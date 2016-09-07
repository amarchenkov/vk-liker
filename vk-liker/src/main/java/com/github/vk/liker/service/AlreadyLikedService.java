package com.github.vk.liker.service;

import com.github.vk.liker.model.Account;

/**
 * Created at 07.09.2016 17:45
 *
 * @author AMarchenkov
 */
@FunctionalInterface
public interface AlreadyLikedService {
    /**
     * Like objects for owner id by account different from source
     *
     * @param id      Owner id for like by another account
     * @param account liked account
     */
    void likeByAnotherAccount(long id, Account account);
}
