package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.model.content.ContentSource;
import com.vk.api.sdk.client.VkApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created at 05.10.2017 14:27
 *
 * @author AMarchenkov
 */
@Service
public class TaskFactory {

    private final VkApiClient vkApiClient;

    @Autowired
    public TaskFactory(VkApiClient vkApiClient) {
        this.vkApiClient = vkApiClient;
    }

    public ParseGroupContentTask createParseGroupContentTask(List<ContentSource> sources) {
        return new ParseGroupContentTask(sources, vkApiClient);
    }

}
