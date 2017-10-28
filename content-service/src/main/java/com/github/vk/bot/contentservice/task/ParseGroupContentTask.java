package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.converter.ModelConverter;
import com.github.vk.bot.common.model.account.Account;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.common.model.content.Item;
import com.github.vk.bot.contentservice.repository.ContentSourceRepository;
import com.github.vk.bot.contentservice.repository.ItemRepository;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.PostType;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

/**
 * Created at 05.10.2017 14:24
 *
 * @author AMarchenkov
 */
@Slf4j
public class ParseGroupContentTask extends RecursiveAction {

    private List<ContentSource> contentSources;
    private transient VkApiClient vkApiClient;
    private transient ModelConverter modelConverter;
    private transient AccountClient accountClient;
    private transient ContentSourceRepository contentSourceRepository;
    private transient ItemRepository itemRepository;

    public ParseGroupContentTask(VkApiClient vkApiClient,
                                 AccountClient accountClient, ContentSourceRepository contentSourceRepository,
                                 ItemRepository itemRepository, ModelConverter modelConverter) {
        this.vkApiClient = vkApiClient;
        this.accountClient = accountClient;
        this.contentSourceRepository = contentSourceRepository;
        this.itemRepository = itemRepository;
        this.modelConverter = modelConverter;
    }

    void setContentSources(List<ContentSource> contentSources) {
        this.contentSources = contentSources;
    }

    @Override
    protected void compute() {
        if (contentSources == null || contentSources.isEmpty()) {
            LOG.debug("Nothing to get. No sources detected");
            return;
        }
        if (contentSources.size() > 1) {
            LOG.debug("Fork task. Too much sources");
            ParseGroupContentTask subTask = new ParseGroupContentTask(vkApiClient, accountClient,
                    contentSourceRepository, itemRepository, modelConverter);
            subTask.setContentSources(contentSources.subList(1, contentSources.size()));
            subTask.fork();
        }
        process(contentSources.iterator().next());
    }

    private void process(ContentSource contentSource) {
        List<Account> actualAccounts = accountClient.getActualAccounts();
        if (actualAccounts != null && !actualAccounts.isEmpty()) {
            Account account = actualAccounts.iterator().next();
            LOG.debug("Start processing content source " + contentSource);
            try {
                UserActor actor = new UserActor(account.getUserId(), account.getAccessToken());
                GetResponse execute = vkApiClient.wall().get(actor).ownerId(contentSource.getSourceId()).count(100).execute();
                LocalDateTime checkDateTime = LocalDateTime.now();
                List<Item> items = execute.getItems().stream()
                        .filter(wallPostFull -> wallPostFull.getPostType().equals(PostType.POST))
                        .filter(wallPostFull -> wallPostFull.getAttachments() != null && !wallPostFull.getAttachments().isEmpty())
                        .filter(item -> {
                            Optional<List<Item>> itemsInDb = itemRepository.findAllBySourceId(item.getId());
                            return !itemsInDb.isPresent() || itemsInDb.get().isEmpty();
                        })
                        .map(wallPostFull -> {
                            Item item = modelConverter.fromVkItemToMongoItem(wallPostFull);
                            item.setContentSourceId(contentSource.getId());
                            return item;
                        })
                        .collect(Collectors.toList());
                itemRepository.save(items);
                LOG.info(MessageFormat.format("[{0}] items have been saved in database", items.size()));
                contentSource.setLastCheck(checkDateTime);
                contentSourceRepository.save(contentSource);
            } catch (ApiException | ClientException e) {
                LOG.error("API error", e);
            } catch (Exception e) {
                LOG.error("General exception", e);
            }
        } else {
            LOG.info("NO Actual Account");
        }
    }

}
