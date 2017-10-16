package com.github.vk.bot.account.service;

import com.github.vk.bot.common.model.AccessTokenResponse;
import com.github.vk.bot.common.model.account.Account;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created at 26.09.2017 11:38
 *
 * @author AMarchenkov
 */
public interface AccountService {

    List<Account> getActiveAccounts();

    void addAccessToken(AccessTokenResponse token, ObjectId accountId);

    List<Account> getAccounts();

    Account getAccountById(ObjectId id);

    ObjectId save(Account account);

    void remove(Account account);

    void removeById(ObjectId id);
}
