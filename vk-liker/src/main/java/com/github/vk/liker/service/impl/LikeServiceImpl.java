package com.github.vk.liker.service.impl;

import com.github.vk.api.enums.Display;
import com.github.vk.api.enums.ResponseType;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.liker.model.Account;
import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.task.LikeTask;
import com.github.vk.liker.task.SourceTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created at 29.08.2016 13:10
 *
 * @author AMarchenkov
 */
@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger LOG = LogManager.getLogger(LikeService.class);

    private BlockingQueue<SourceTask> queue = new LinkedBlockingQueue<>();
    private AccountRepository accountRepository;
    private AuthorizeData authorizeData = new AuthorizeData();

    @Autowired
    public void setRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    protected void init() {
        authorizeData.setClientId("5591327");
        authorizeData.setResponseType(ResponseType.TOKEN);
        authorizeData.setDisplay(Display.MOBILE);
        authorizeData.setScope("wall,photos");
        authorizeData.setV(5.33F);
    }

    @Override
    public void addListToProcess(SourceTask task) {
        LOG.info("Add tasks[{}] to process. Size = {}", task.getUuid(), task.getIdList().size());
        queue.add(task);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                SourceTask task = queue.take();
                ForkJoinPool pool = ForkJoinPool.commonPool();
                pool.invoke(new LikeTask(accountRepository, authorizeData, task.getIdList()));
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOG.error("Like service thread has been interrupted", e);
            }
        }

    }

}