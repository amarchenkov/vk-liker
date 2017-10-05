package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.contentservice.client.AccountClient;
import com.vk.api.sdk.client.VkApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${vk.bot.client_id}")
    private int clientId;
    @Value("${vk.bot.client_secret}")
    private String clientSecret;
    @Value("${vk.bot.redirect_url}")
    private String redirectUrl;

    @Autowired
    public TaskFactory(VkApiClient vkApiClient, AccountClient accountClient) {
        this.vkApiClient = vkApiClient;
        this.accountClient = accountClient;
    }

    public ParseGroupContentTask createParseGroupContentTask(List<ContentSource> sources) {
        return new ParseGroupContentTask(sources, vkApiClient, clientId, clientSecret, redirectUrl, accountClient);
    }

}
