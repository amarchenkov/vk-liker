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

    private AccountRepository accountRepository;

    @Autowired
    public void setRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void addListToProcess(SourceTask task) {
        LOG.info("Add tasks[{}] to process. Size = {}", task.getUuid(), task.getIdList().size());
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long accountSize = accountRepository.count();
        if (accountSize == 0) {
            LOG.error("Account list is empty");
            throw new RuntimeException("Account list is empty");
        }
        int partSize = (int) (task.getIdList().size() / accountRepository.count());
        if (partSize < 1) {
            partSize = 1;
        }
        LOG.info("Size of part to process = [{}]", partSize);
        int initialItemIndex = 0;
        for (Account account : accountRepository.findAll()) {
            pool.execute(new LikeTask(accountRepository, account, null));
            initialItemIndex += partSize;
        }
    }

}