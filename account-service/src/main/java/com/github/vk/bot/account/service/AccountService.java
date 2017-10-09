package com.github.vk.bot.account.service;

import com.github.vk.bot.common.model.account.AccessToken;
import com.github.vk.bot.common.model.account.Account;
import org.bson.types.ObjectId;

import java.util.Set;

/**
 * Created at 26.09.2017 11:38
 *
 * @author AMarchenkov
 */
public interface AccountService {

    Set<Account> getActiveAccounts();

    void addAccessToken(AccessToken token, Account account);

    void addAccessToken(AccessToken token, ObjectId accountId);

    Set<Account> getAccounts();

    Account getAccountById(ObjectId id);

    ObjectId save(Account account);

    void remove(Account account);

    void removeById(ObjectId id);
}
