package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.model.account.Account;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.common.model.content.Item;
import com.github.vk.bot.contentservice.repository.ContentSourceRepository;
import com.github.vk.bot.contentservice.service.impl.ModelConverter;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import lombok.extern.apachecommons.CommonsLog;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

/**
 * Created at 05.10.2017 14:24
 *
 * @author AMarchenkov
 */
@CommonsLog
public class ParseGroupContentTask extends RecursiveAction {

    private List<ContentSource> allBySourceType;
    private VkApiClient vkApiClient;
    private AccountClient accountClient;
    private ContentSourceRepository contentSourceRepository;
    private ModelConverter modelConverter;

    public ParseGroupContentTask(List<ContentSource> allBySourceType, VkApiClient vkApiClient, AccountClient accountClient, ContentSourceRepository contentSourceRepository) {
        this.allBySourceType = allBySourceType;
        this.vkApiClient = vkApiClient;
        this.accountClient = accountClient;
        this.contentSourceRepository = contentSourceRepository;
    }

    @Override
    protected void compute() {
        if (allBySourceType == null || allBySourceType.isEmpty()) {
            LOG.debug("Nothing to get. No sources detected");
            return;
        }
        if (allBySourceType.size() > 1) {
            LOG.debug("Fork task. Too much sources");
            ParseGroupContentTask subTask = new ParseGroupContentTask(allBySourceType.subList(1, allBySourceType.size()),
                    vkApiClient, accountClient, contentSourceRepository);
            subTask.fork();
        }
        process(allBySourceType.iterator().next());
    }

    private void process(ContentSource contentSource) {
        Optional<Set<Account>> actualAccounts = accountClient.getActualAccounts();
        if (actualAccounts.isPresent() && !actualAccounts.get().isEmpty()) {
            Account account = actualAccounts.get().iterator().next();
            LOG.debug("Start processing content source " + contentSource);
            try {
                UserActor actor = new UserActor(account.getAccessToken().getUserId(), account.getAccessToken().getToken());
                GetResponse execute = vkApiClient.wall().get(actor).ownerId(contentSource.getSourceId()).count(100).execute();
                execute.getItems().stream()
                        .filter(item -> contentSourceRepository.findAllByItems_SourceId(item.getId()).isPresent())
                        .forEach(wallPostFull -> {
                            Item item = modelConverter.fromVkItemToMongoItem(wallPostFull);
                            contentSource.getItems().add(item);
                            contentSourceRepository.save(contentSource);
                        });
            } catch (ApiException | ClientException e) {
                LOG.error("API error", e);
            }
        } else {
            LOG.info("NO Actual Account");
        }
    }

}
