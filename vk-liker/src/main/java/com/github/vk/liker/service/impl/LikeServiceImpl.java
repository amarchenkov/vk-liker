package com.github.vk.liker.service.impl;

import com.github.vk.liker.exception.TaskException;
import com.github.vk.liker.model.Account;
import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.repository.LikeRepository;
import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.task.LikeTask;
import com.github.vk.liker.task.SourceTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;

/**
 * Created at 29.08.2016 13:10
 *
 * @author AMarchenkov
 */
@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger LOG = LogManager.getLogger(LikeService.class);

    private AccountRepository accountRepository;
    private LikeRepository likeRepository;

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Autowired
    public void setRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void addListToProcess(SourceTask task) {
        LOG.info("Add tasks[{}] to process. Size = {}", task.getUuid(), task.getIdList().size());
        if (task.getIdList().isEmpty()) {
            LOG.error("Task is empty");
            throw new TaskException("Task is empty");
        }
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long accountSize = accountRepository.count();
        if (accountSize == 0) {
            LOG.error("Account list is empty");
            throw new TaskException("Account list is empty");
        }
        int partSize = (int) (task.getIdList().size() / accountRepository.count());
        if (partSize < 1) {
            partSize = 1;
        }
        LOG.info("Size of part to process = [{}]", partSize);
        int initialItemIndex = 0;
        for (Account account : accountRepository.findAll()) {
            int begin;
            int end;
            begin = initialItemIndex;
            if (accountSize - (initialItemIndex + partSize) < partSize) {
                end = (int) accountSize;
            } else {
                end = initialItemIndex + partSize;
            }
            pool.execute(new LikeTask(accountRepository, likeRepository, account, task.getIdList().subList(begin, end)));
            initialItemIndex += (end - begin);
        }
    }

}