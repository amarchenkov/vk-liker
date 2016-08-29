package com.github.vk.liker.task;

import com.github.vk.api.VK;
import com.github.vk.api.models.AccessToken;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.liker.model.Account;

import java.time.LocalDateTime;

/**
 * Created at 29.08.2016 12:38
 *
 * @author AMarchenkov
 */
public class LikeTask implements Runnable {

    private static final int COUNT_ITEMS_TO_LIKE = 3;

    private long ownerId;
    private Account account;
    private AuthorizeData authorizeData;

    public LikeTask(long ownerId, Account account, AuthorizeData authorizeData) {
        this.ownerId = ownerId;
        this.account = account;
        this.authorizeData = authorizeData;
    }

    @Override
    public void run() {
        if (account.getExpiresIn().isAfter(LocalDateTime.now())) {
            AccessToken token = new AccessToken()
            VK vk = new VK()
        }
        VK vk = new VK(authorizeData);
        vk.getUserPhotos(ownerId, 0, COUNT_ITEMS_TO_LIKE).ifPresent();
        vk.getWallPosts(ownerId, 0, COUNT_ITEMS_TO_LIKE).ifPresent();
    }

}
