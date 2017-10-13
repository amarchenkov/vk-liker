package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.converter.ModelConverter;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.contentservice.repository.UserRepository;
import com.vk.api.sdk.client.VkApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created at 12.10.2017 11:49
 *
 * @author AMarchenkov
 */
@Component
public class TaskFactory {

    private final UserRepository userRepository;
    private final AccountClient accountClient;
    private final ModelConverter modelConverter;
    private final VkApiClient vkApiClient;

    @Autowired
    public TaskFactory(UserRepository userRepository, AccountClient accountClient, ModelConverter modelConverter, VkApiClient vkApiClient) {
        this.userRepository = userRepository;
        this.accountClient = accountClient;
        this.modelConverter = modelConverter;
        this.vkApiClient = vkApiClient;
    }

    public StoreUserTask createStoreUserTask(ContentSource contentSource) {
        return new StoreUserTask(contentSource, userRepository, accountClient, vkApiClient, modelConverter);
    }
}
