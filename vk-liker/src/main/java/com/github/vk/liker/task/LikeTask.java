package com.github.vk.liker.task;

import com.github.vk.api.VK;
import com.github.vk.api.enums.ObjectType;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AccessToken;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.json.PhotosGetAllResponse;
import com.github.vk.api.models.json.WallGetResponse;
import com.github.vk.liker.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created at 29.08.2016 12:38
 *
 * @author AMarchenkov
 */
public class LikeTask extends RecursiveAction {

    private static final Logger LOG = LogManager.getLogger(RecursiveAction.class);

    private AuthorizeData authorizeData;
    private Account account;
    private List<Long> idList;
    private VK vk;

    public LikeTask(AuthorizeData authorizeData, Account account, List<Long> idList) throws AuthorizeException {
        this.authorizeData = authorizeData;
        this.account = account;
        this.idList = idList;
        if (account.getExpiresIn().isAfter(LocalDateTime.now())) {
            AccessToken accessToken = new AccessToken(
                    account.getAccessToken(), account.getExpiresIn(), String.valueOf(account.getUserId()));
            this.vk = new VK(accessToken);
        } else {
            this.vk = new VK(authorizeData);
            this.vk.updateToken(account.getLogin(), account.getPassword());
        }
    }

    @Override
    protected void compute() {
        this.idList.parallelStream().forEach(this::setLike);
    }

    private void setLike(Long id) {
        vk.getUserPhotos(id, 0, 3).ifPresent(this::likePhoto);
        vk.getWallPosts(id, 0, 3).ifPresent(this::likePost);
    }

    private void likePost(WallGetResponse wall) {
        wall.getItems().forEach(p -> vk.like(ObjectType.POST, p.getOwnerId(), p.getId()));
    }

    private void likePhoto(PhotosGetAllResponse photos) {
        photos.getItems().forEach(p -> vk.like(ObjectType.PHOTO, p.getOwnerId(), p.getId()));
    }

}
