package com.github.vk.bot.groupservice;

import com.github.vk.bot.common.AbstractMicroServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created at 29.09.2017 10:28
 *
 * @author Andrey
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class GroupServiceApplication extends AbstractMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroupServiceApplication.class, args);
    }
}
