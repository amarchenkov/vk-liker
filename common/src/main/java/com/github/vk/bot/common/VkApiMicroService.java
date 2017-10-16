package com.github.vk.bot.common;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.annotation.Bean;

/**
 * Created at 16.10.2017 16:25
 *
 * @author AMarchenkov
 */
public interface VkApiMicroService {

    @Bean
    default VkApiClient vkApiClient() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        return new VkApiClient(transportClient);
    }
}
