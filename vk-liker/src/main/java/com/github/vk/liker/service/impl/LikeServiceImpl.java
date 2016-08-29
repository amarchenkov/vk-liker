package com.github.vk.liker.service.impl;

import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.service.LikeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * Created at 29.08.2016 13:10
 *
 * @author AMarchenkov
 */
@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger LOG = LogManager.getLogger(LikeService.class);

    private BlockingQueue<Long> queue = new LinkedBlockingQueue<>();
    private AccountRepository accountRepository;
    private Semaphore semaphore;

    @Autowired
    public void setRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    protected void init() {
        semaphore = new Semaphore((int) accountRepository.count());
    }

    @PreDestroy
    private void finish() {
    }

    @Override
    public void addListToProcess(List<Long> list) {
        LOG.info("Add [{}] tasks to process", list.size());
        list.parallelStream().forEach(queue::add);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                long ownerId = queue.take();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOG.error("Like service thread has been interrupted", e);
            }
        }

    }

}