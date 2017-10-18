package com.github.vk.bot.groupservice.service.impl;

import com.github.vk.bot.common.model.group.Group;
import com.github.vk.bot.groupservice.service.GroupService;
import com.github.vk.bot.groupservice.service.PostContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created at 16.10.2017 15:51
 *
 * @author AMarchenkov
 */
@Service
public class VkPostContentService implements PostContentService {

    private final GroupService groupService;
    private final ForkJoinPool forkJoinPool;
    private final TaskFactory taskFactory;

    @Autowired
    public VkPostContentService(GroupService groupService, ForkJoinPool forkJoinPool, TaskFactory taskFactory) {
        this.groupService = groupService;
        this.forkJoinPool = forkJoinPool;
        this.taskFactory = taskFactory;
    }

    @Override
    @Scheduled(cron = "0 0 0/2 * * *")
    public void post() {
        List<Group> groups = groupService.getAllGroups();
        groups.forEach(group -> forkJoinPool.execute(taskFactory.getPostContentTask(group)));
    }

}
