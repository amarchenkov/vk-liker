package com.github.vk.bot.groupservice;

import com.github.vk.bot.common.AbstractMicroServiceApplication;
import com.github.vk.bot.common.ForkJoinMicroService;
import com.github.vk.bot.common.VkApiMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created at 29.09.2017 10:28
 *
 * @author Andrey
 */
@EnableFeignClients(basePackages = {"com.github.vk.bot.common.client"})
@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
public class GroupServiceApplication extends AbstractMicroServiceApplication implements ForkJoinMicroService, VkApiMicroService {

    public static void main(String[] args) {
        SpringApplication.run(GroupServiceApplication.class, args);
    }

    @Override
    public String getWorkerNamePrefix() {
        return "POST-CONTENT";
    }
}
