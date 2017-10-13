package com.github.vk.bot.contentservice;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.github.vk.bot.contentservice.*", "com.github.vk.bot.common.*"})
@EnableScheduling
@EnableFeignClients(basePackages = {"com.github.vk.bot.common.client",})
public class UserServiceApplication {

	@Bean
	public VkApiClient vkApiClient() {
		TransportClient transportClient = HttpTransportClient.getInstance();
		return new VkApiClient(transportClient);
	}

	@Bean
	public ForkJoinPool forkJoinPool() {
		final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
			final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
			worker.setName("USER-CRAWLER-" + worker.getPoolIndex());
			return worker;
		};
		return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), factory, null, true);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
