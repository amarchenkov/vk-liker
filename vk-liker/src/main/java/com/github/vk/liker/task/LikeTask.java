package com.github.vk.liker.task;

import com.github.vk.liker.model.Account;

/**
 * Created at 29.08.2016 12:38
 *
 * @author AMarchenkov
 */
public class LikeTask implements Runnable {

    private static final int COUNT_ITEMS_TO_LIKE = 3;

    private long ownerId;
    private Account account;

    public LikeTask(long ownerId, Account account) {
        this.ownerId = ownerId;
        this.account = account;
    }

    @Override
    public void run() {
    }

}
