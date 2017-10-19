package com.github.vk.bot.groupservice.service.impl;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.client.ContentSourceClient;
import com.github.vk.bot.common.model.group.Group;
import com.github.vk.bot.groupservice.task.VkPostTask;
import com.vk.api.sdk.client.VkApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created at 17.10.2017 13:09
 *
 * @author AMarchenkov
 */
@Component
public class TaskFactory {

    private final VkApiClient vkApiClient;
    private final AccountClient accountClient;
    private final ContentSourceClient contentSourceClient;

    @Autowired
    public TaskFactory(VkApiClient vkApiClient, AccountClient accountClient, ContentSourceClient contentSourceClient) {
        this.vkApiClient = vkApiClient;
        this.accountClient = accountClient;
        this.contentSourceClient = contentSourceClient;
    }

    VkPostTask getPostContentTask(Group group) {
        return new VkPostTask(vkApiClient, group, accountClient, contentSourceClient);
    }
}
