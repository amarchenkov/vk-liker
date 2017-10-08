package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.contentservice.repository.ContentSourceRepository;
import com.github.vk.bot.contentservice.service.impl.ModelConverter;
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


    @Autowired
    public TaskFactory(VkApiClient vkApiClient, AccountClient accountClient, ContentSourceRepository contentSourceRepository, ModelConverter modelConverter) {
        this.vkApiClient = vkApiClient;
        this.accountClient = accountClient;
        this.contentSourceRepository = contentSourceRepository;
        this.modelConverter = modelConverter;
    }

    public ParseGroupContentTask createParseGroupContentTask(List<ContentSource> sources) {
        return new ParseGroupContentTask(sources, vkApiClient, accountClient, contentSourceRepository, modelConverter);
    }

}
