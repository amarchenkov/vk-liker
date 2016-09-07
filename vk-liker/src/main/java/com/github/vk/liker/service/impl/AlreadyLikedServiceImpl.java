package com.github.vk.liker.service.impl;

import com.github.vk.api.VK;
import com.github.vk.liker.model.Account;
import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.repository.LikeRepository;
import com.github.vk.liker.service.AlreadyLikedService;
import com.github.vk.liker.task.LikeTask;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created at 07.09.2016 17:46
 *
 * @author AMarchenkov
 */
@Service
public class AlreadyLikedServiceImpl implements AlreadyLikedService {

    private static final Logger LOG = LogManager.getLogger(AlreadyLikedService.class);

    private AccountRepository accountRepository;
    private LikeRepository likeRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void likeByAnotherAccount(long id, Account account) {
        Account newAccount = accountRepository.findOneByIdNot(account.getId());
        if (newAccount == null) {
            LOG.error("There are no accounts");
            return;
        }
        List<Long> idList = Lists.newArrayList();
        idList.add(id);
        LikeTask likeTask = new LikeTask(accountRepository, likeRepository, this, newAccount, idList);
        likeTask.invoke();
    }
}
