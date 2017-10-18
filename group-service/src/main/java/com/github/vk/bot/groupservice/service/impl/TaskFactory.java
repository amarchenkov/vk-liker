package com.github.vk.bot.groupservice.service.impl;

import com.github.vk.bot.common.client.AccountClient;
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

    @Autowired
    public TaskFactory(VkApiClient vkApiClient, AccountClient accountClient) {
        this.vkApiClient = vkApiClient;
        this.accountClient = accountClient;
    }

    VkPostTask getPostContentTask(Group group) {
        return new VkPostTask(vkApiClient, group, accountClient);
    }
}
