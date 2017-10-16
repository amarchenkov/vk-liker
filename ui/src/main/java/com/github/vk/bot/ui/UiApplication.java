package com.github.vk.bot.ui;

import com.github.vk.bot.common.AbstractMicroServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.github.vk.bot.*"})
public class UiApplication extends AbstractMicroServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}
}
