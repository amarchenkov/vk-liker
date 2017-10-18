package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.converter.ModelConverter;
import com.github.vk.bot.common.enums.SourceType;
import com.github.vk.bot.common.model.account.Account;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.common.model.user.GroupUser;
import com.github.vk.bot.contentservice.repository.UserRepository;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

/**
 * Created at 12.10.2017 11:50
 *
 * @author AMarchenkov
 */
@Slf4j
public class StoreUserTask extends RecursiveAction {

    private static final int MAX_MEMBERS = 1000;

    private final transient ContentSource contentSource;
    private final transient UserRepository userRepository;
    private final transient AccountClient accountClient;
    private final transient VkApiClient vkApiClient;
    private final transient ModelConverter modelConverter;

    private int offset = 0;
    private boolean retry = false;

    StoreUserTask(ContentSource contentSource, UserRepository userRepository, AccountClient accountClient,
                  VkApiClient vkApiClient, ModelConverter modelConverter) {
        this.contentSource = contentSource;
        this.userRepository = userRepository;
        this.accountClient = accountClient;
        this.vkApiClient = vkApiClient;
        this.modelConverter = modelConverter;
    }

    private StoreUserTask(int offset, ContentSource contentSource, UserRepository userRepository, AccountClient accountClient,
                          VkApiClient vkApiClient, ModelConverter modelConverter) {
        this(contentSource, userRepository, accountClient, vkApiClient, modelConverter);
        this.offset = offset;
    }

    private void setRetry(boolean retry) {
        this.retry = retry;
    }

    @Override
    protected void compute() {
        try {
            List<Account> actualAccounts = accountClient.getActualAccounts();
            if (actualAccounts.isEmpty()) {
                LOG.info("NO Actual accounts");
                return;
            }
            Account account = actualAccounts.iterator().next();
            UserActor userActor = new UserActor(account.getUserId(), account.getAccessToken());
            GetMembersResponse execute = vkApiClient
                    .groups()
                    .getMembers(userActor)
                    .offset(this.offset)
                    .count(MAX_MEMBERS)
                    .groupId(String.valueOf(contentSource.getSourceId() * -1))
                    .execute();

            int count = execute.getCount() != null ? execute.getCount() : 0;
            if (count > (offset + MAX_MEMBERS) && !this.retry) {
                LOG.info("[" + count + "] users in [" + contentSource + "]");
                this.runSubTask(offset + MAX_MEMBERS);
            }
            process(execute.getItems(), userActor);
        } catch (ApiException | ClientException e) {
            LOG.error("API exception", e);
            this.runSubTask(this.offset, true);
        } catch (Exception e) {
            LOG.error("General exception", e);
            this.runSubTask(this.offset, true);
        }
        LOG.info("Finish processing user from [" + offset + "] to [" + (offset + MAX_MEMBERS) + "] for " + contentSource.getName());
    }

    private void runSubTask(int offset) {
        runSubTask(offset, false);
    }

    private void runSubTask(int offset, boolean retry) {
        StoreUserTask subTask = new StoreUserTask(offset, contentSource, userRepository, accountClient, vkApiClient,
                modelConverter);
        setRetry(retry);
        subTask.fork();
    }

    private void process(List<Integer> items, UserActor userActor) throws ClientException, ApiException, InterruptedException {
        LOG.info("Start processing user from [" + offset + "] to [" + (offset + MAX_MEMBERS) + "] for " + contentSource.getName());
        if (items == null || items.isEmpty()) {
            LOG.info("Source has no members");
            return;
        }
        try {
            LOG.info("Sleep before exec");
            Thread.sleep(1562);
        } catch (InterruptedException e) {
            LOG.error("Task interrupted", e);
            throw e;
        }
        List<UserXtrCounters> users = vkApiClient
                .users()
                .get(userActor)
                .fields(UserField.ABOUT, UserField.CITY)
                .userIds(items.stream().map(String::valueOf).collect(Collectors.toList()))
                .execute();
        LOG.info("Getting users");
        List<GroupUser> collect = users.parallelStream()
                .filter(user -> {
                    List<GroupUser> allBySourceUserId = userRepository.findAllBySourceUserId(user.getId()).orElse(Collections.emptyList());
                    return allBySourceUserId.isEmpty();
                })
                .map(user -> {
                    GroupUser groupUser = modelConverter.convertToMongoUser(user);
                    groupUser.setContentSourceFrom(contentSource.getId());
                    groupUser.setSourceType(SourceType.VK);
                    return groupUser;
                })
                .collect(Collectors.toList());
        userRepository.save(collect);
        LOG.info("[" + collect.size() + "] user saved in storage from [" + contentSource.getName() + "]");
    }

}
