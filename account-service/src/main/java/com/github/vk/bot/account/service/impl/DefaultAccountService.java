package com.github.vk.bot.account.service.impl;

import com.github.vk.bot.account.repository.AccountRepository;
import com.github.vk.bot.account.service.AccountService;
import com.github.vk.bot.common.model.account.AccessToken;
import com.github.vk.bot.common.model.account.Account;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
    public Set<Account> getActiveAccounts() {
        return new HashSet<>(accountRepository.findAll());
//        return new HashSet<>(accountRepository.findActiveAccount(System.currentTimeMillis() / 1000L));
//        return accountRepository.findAll();
    }

    @Override
    public void addAccessToken(AccessToken token, Account account) {
        if (token.getId() == null) {
            token.setId(new ObjectId());
        }
        account.setAccessToken(token);
        save(account);
    }

    @Override
    public void addAccessToken(AccessToken token, ObjectId accountId) {
        Account account = getAccountById(accountId);
        addAccessToken(token, account);
    }

    @Override
    public Set<Account> getAccounts() {
        return new HashSet<>(accountRepository.findAll());
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
