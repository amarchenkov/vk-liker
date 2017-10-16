package com.github.vk.bot.groupservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.model.group.Group;
import com.vk.api.sdk.client.VkApiClient;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created at 16.10.2017 15:57
 *
 * @author AMarchenkov
 */
public class VkPostTask extends RecursiveAction {

    private final transient VkApiClient vkApiClient;
    private final transient List<Group> groups;
    private final transient AccountClient accountClient;

    public VkPostTask(VkApiClient vkApiClient, List<Group> groups, AccountClient accountClient) {
        this.vkApiClient = vkApiClient;
        this.groups = groups;
        this.accountClient = accountClient;
    }

    @Override
    protected void compute() {
        vkApiClient.wall().post(null);
    }

}
