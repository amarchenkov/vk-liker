package com.github.vk.bot.groupservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.model.account.Account;
import com.github.vk.bot.common.model.group.Group;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created at 16.10.2017 15:57
 *
 * @author AMarchenkov
 */
@Slf4j
public class VkPostTask extends RecursiveAction {

    private final transient VkApiClient vkApiClient;
    private final transient Group group;
    private final transient AccountClient accountClient;

    public VkPostTask(VkApiClient vkApiClient, Group group, AccountClient accountClient) {
        this.vkApiClient = vkApiClient;
        this.group = group;
        this.accountClient = accountClient;
    }

    @Override
    protected void compute() {
        List<Account> actualAccounts = accountClient.getActualAccounts();
        if (actualAccounts != null && !actualAccounts.isEmpty()) {
            Account account = actualAccounts.get(0);
            UserActor userActor = new UserActor(account.getUserId(), account.getAccessToken());
//            vkApiClient.photos().saveWallPhoto(userActor, "").groupId(group.getGroupId()).execute();
//            vkApiClient
//                    .wall()
//                    .post(userActor)
//                    .ownerId(group.getGroupId())
//                    .message()
//                    .attachments()
//                    .execute();
        } else {
            LOG.info("NO actual accounts");
        }
    }

}
