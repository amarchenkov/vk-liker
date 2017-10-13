package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.contentservice.repository.ContentSourceRepository;
import com.github.vk.bot.contentservice.repository.ItemRepository;
import com.github.vk.bot.common.converter.ModelConverter;
import com.vk.api.sdk.client.VkApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created at 05.10.2017 14:27
 *
 * @author AMarchenkov
 */
@Service
@RefreshScope
public class TaskFactory {

    private final VkApiClient vkApiClient;
    private final AccountClient accountClient;
    private final ContentSourceRepository contentSourceRepository;
    private final ModelConverter modelConverter;
    private final ItemRepository itemRepository;


    @Autowired
    public TaskFactory(VkApiClient vkApiClient, AccountClient accountClient,
                       ContentSourceRepository contentSourceRepository, ModelConverter modelConverter,
                       ItemRepository itemRepository) {
        this.vkApiClient = vkApiClient;
        this.accountClient = accountClient;
        this.contentSourceRepository = contentSourceRepository;
        this.modelConverter = modelConverter;
        this.itemRepository = itemRepository;
    }

    public ParseGroupContentTask createParseGroupContentTask(List<ContentSource> sources) {
        ParseGroupContentTask task = new ParseGroupContentTask(vkApiClient, accountClient, contentSourceRepository,
                itemRepository, modelConverter);
        task.setContentSources(sources);
        return task;
    }

}
