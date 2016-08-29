package com.github.vk.liker.service.impl;

import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.task.LikeTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created at 29.08.2016 13:10
 *
 * @author AMarchenkov
 */
@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger LOG = LogManager.getLogger(LikeService.class);

    private BlockingQueue<Long> queue = new LinkedBlockingQueue<>();
    private BlockingQueue<Runnable> threadPoolQueue = new LinkedBlockingQueue<>();
    private AccountRepository accountRepository;
    private Semaphore semaphore;
    private ExecutorService threadPool;

    @Autowired
    public void setRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    protected void init() {
        int maxThread = (int) accountRepository.count();
        final AtomicInteger counter = new AtomicInteger(0);
        threadPool = new ThreadPoolExecutor(1, maxThread, 100, TimeUnit.MICROSECONDS, threadPoolQueue,
                r -> new Thread(r, "Liker-" + counter.incrementAndGet()));
        semaphore = new Semaphore(maxThread);
    }

    @PreDestroy
    private void finish() {
        threadPool.shutdown();
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
                threadPool.execute(new LikeTask(ownerId, ));
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOG.error("Like service thread has been interrupted", e);
            }
        }

    }

}