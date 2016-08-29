package com.github.vk.liker.jmx;

import com.github.vk.liker.model.Account;

import java.util.concurrent.RecursiveAction;

/**
 * Created at 28.08.2016 15:08
 *
 * @author Andrey
 */
public class LikeTask extends RecursiveAction {

    private Account account;
    private long itemId;
    private long ownerId;

    public LikeTask(Account account, long itemId, long ownerId) {
        this.account = account;
        this.itemId = itemId;
        this.ownerId = ownerId;
    }

    @Override
    protected void compute() {

    }

}
