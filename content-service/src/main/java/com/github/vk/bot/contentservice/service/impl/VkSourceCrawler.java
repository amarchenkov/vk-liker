package com.github.vk.bot.contentservice.service.impl;

import com.github.vk.bot.common.enums.SourceType;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.contentservice.repository.ContentSourceRepository;
import com.github.vk.bot.contentservice.service.SourceCrawler;
import com.github.vk.bot.contentservice.task.ParseGroupContentTask;
import com.github.vk.bot.contentservice.task.TaskFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created at 05.10.2017 14:45
 *
 * @author AMarchenkov
 */
@Service
@Slf4j
public class VkSourceCrawler implements SourceCrawler {

    private final ForkJoinPool pool;
    private final TaskFactory taskFactory;
    private final ContentSourceRepository sourceRepository;

    @Autowired
    public VkSourceCrawler(ForkJoinPool pool, TaskFactory taskFactory, ContentSourceRepository sourceRepository) {
        this.pool = pool;
        this.taskFactory = taskFactory;
        this.sourceRepository = sourceRepository;
    }

    @Override
//    @Scheduled(cron = "0 0 0/3 * * *")
    @Scheduled(cron = "0 * * * * *")
    public void crawl() {
        LOG.info("VK Crawler started");
        List<ContentSource> contentSources = sourceRepository.findAllBySourceType(SourceType.VK_GROUP).orElse(new ArrayList<>());
        ParseGroupContentTask task = taskFactory.createParseGroupContentTask(contentSources);
        pool.execute(task);
    }

}
