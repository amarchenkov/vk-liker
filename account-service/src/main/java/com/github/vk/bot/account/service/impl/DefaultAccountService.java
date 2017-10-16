package com.github.vk.bot.account.service.impl;

import com.github.vk.bot.account.repository.AccountRepository;
import com.github.vk.bot.account.service.AccountService;
import com.github.vk.bot.common.model.AccessTokenResponse;
import com.github.vk.bot.common.model.account.Account;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created at 26.09.2017 11:49
 *
 * @author AMarchenkov
 */
@Service
public class DefaultAccountService implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public DefaultAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getActiveAccounts() {
        return accountRepository.findActiveAccount(LocalDateTime.now());
    }

    @Override
    public void addAccessToken(AccessTokenResponse token, ObjectId accountId) {
        Account account = getAccountById(accountId);
        account.setAccessToken(token.getAccessToken());
        account.setUserId(token.getUserId());
        account.setExpirationTime(LocalDateTime.now().plusSeconds(token.getExpiresIn()));
        save(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(ObjectId id) {
        return accountRepository.findOne(id);
    }

    @Override
    public ObjectId save(Account account) {
        Account saved = accountRepository.save(account);
        return saved.getId();
    }

    @Override
    public void remove(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public void removeById(ObjectId id) {
        accountRepository.delete(id);
    }
}
