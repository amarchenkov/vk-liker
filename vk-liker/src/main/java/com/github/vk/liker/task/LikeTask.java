package com.github.vk.liker.task;

import com.github.vk.liker.model.Account;

/**
 * Created at 29.08.2016 12:38
 *
 * @author AMarchenkov
 */
public class LikeTask implements Runnable {

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
