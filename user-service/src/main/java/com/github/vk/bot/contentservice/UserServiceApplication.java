package com.github.vk.bot.contentservice;

import com.github.vk.bot.common.AbstractMicroServiceApplication;
import com.github.vk.bot.common.ForkJoinMicroService;
import com.github.vk.bot.common.VkApiMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.github.vk.bot.contentservice.*", "com.github.vk.bot.common.*"})
@EnableScheduling
@EnableFeignClients(basePackages = {"com.github.vk.bot.common.client",})
public class UserServiceApplication extends AbstractMicroServiceApplication implements VkApiMicroService, ForkJoinMicroService {

	@Override
	public String getWorkerNamePrefix() {
		return "USER-CRAWLER";
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
