package com.github.vk.bot.contentservice;

import com.github.vk.bot.common.AbstractMicroServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ContentServiceApplication extends AbstractMicroServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContentServiceApplication.class, args);
	}
}
