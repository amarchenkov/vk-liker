package com.github.vk.bot.contentservice;

import com.github.vk.bot.common.AbstractMicroServiceApplication;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

@EnableDiscoveryClient
@SpringBootApplication
public class ContentServiceApplication extends AbstractMicroServiceApplication {

    @Bean
    public VkApiClient vkApiClient() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        return new VkApiClient(transportClient);
    }

    @Bean
    public ForkJoinPool forkJoinPool() {
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("CONTENT-CRAWLER-" + worker.getPoolIndex());
            return worker;
        };
        return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), factory, null, true);
    }

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }
}
