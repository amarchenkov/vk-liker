package com.github.vk.bot.contentservice.service.impl;

import com.github.vk.bot.common.client.ContentSourceClient;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.contentservice.service.UserCrawler;
import com.github.vk.bot.contentservice.task.TaskFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * Created at 12.10.2017 11:02
 *
 * @author AMarchenkov
 */
@Service
public class VkUserCrawler implements UserCrawler {

    private final ContentSourceClient contentSourceClient;
    private final TaskFactory taskFactory;
    private final ForkJoinPool forkJoinPool;

    @Autowired
    public VkUserCrawler(ContentSourceClient contentSourceClient, TaskFactory taskFactory, ForkJoinPool forkJoinPool) {
        this.contentSourceClient = contentSourceClient;
        this.taskFactory = taskFactory;
        this.forkJoinPool = forkJoinPool;
    }

    @Override
    @Scheduled(cron = "0 23 11 * * *")
    public void crawl() {
        Set<ContentSource> allContentSource = contentSourceClient.getAllContentSource();
        allContentSource.forEach(contentSource -> forkJoinPool.execute(taskFactory.createStoreUserTask(contentSource)));
    }

}
