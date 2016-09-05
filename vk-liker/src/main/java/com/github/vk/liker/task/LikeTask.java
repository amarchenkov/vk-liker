package com.github.vk.liker.task;

import com.github.vk.api.VK;
import com.github.vk.api.enums.ObjectType;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AccessToken;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.json.PhotosGetAllResponse;
import com.github.vk.api.models.json.WallGetResponse;
import com.github.vk.liker.Application;
import com.github.vk.liker.model.Account;
import com.github.vk.liker.model.Like;
import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.repository.LikeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    private static final int ITEM_TO_LIKE_COUNT = 3;

    private LikeRepository likeRepository;
    private Account account;
    private List<Long> idList;
    private VK vk;

    public LikeTask(LikeRepository likeRepository, Account account, List<Long> idList) {
        this.likeRepository = likeRepository;
        this.idList = idList;
        this.account = account;
    }

    @Override
    protected void compute() {
        if (account.getExpiresIn() != null && account.getExpiresIn().isAfter(LocalDateTime.now())) {
            AccessToken accessToken = new AccessToken(
                    account.getAccessToken(), account.getExpiresIn(), String.valueOf(account.getUserId()));
            this.vk = new VK(accessToken);
        } else {
            this.vk = new VK(Application.authorizeData());
            try {
                this.vk.updateToken(account.getLogin(), account.getPassword());
            } catch (AuthorizeException e) {
                LOG.error("Authorization failed!", e);
                return;
            }
        }
        this.idList.parallelStream().forEach(this::setLike);
    }

    private void setLike(Long id) {
        vk.getUserPhotos(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePhoto);
        vk.getWallPosts(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePost);
    }

    private void likePost(WallGetResponse wall) {
        wall.getItems().forEach(p -> {
            vk.like(ObjectType.POST, p.getOwnerId(), p.getId());
            likeRepository.save(new Like(p.getOwnerId(), account.getId()));
        });
    }

    private void likePhoto(PhotosGetAllResponse photos) {
        photos.getItems().forEach(p -> {
            vk.like(ObjectType.PHOTO, p.getOwnerId(), p.getId());
            likeRepository.save(new Like(p.getOwnerId(), account.getId()));
        });
    }

}