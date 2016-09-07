package com.github.vk.liker.task;

import com.github.vk.api.VK;
import com.github.vk.api.enums.ObjectType;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AccessToken;
import com.github.vk.api.models.json.PhotosGetAllResponse;
import com.github.vk.api.models.json.WallGetResponse;
import com.github.vk.liker.Application;
import com.github.vk.liker.model.Account;
import com.github.vk.liker.model.Like;
import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.repository.LikeRepository;
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
    private static final int ITEM_TO_LIKE_COUNT = 3;

    private transient LikeRepository likeRepository;
    private transient AccountRepository accountRepository;
    private transient Account account;
    private List<Long> idList;
    private transient VK vk;

    /**
     * Task initialization
     *
     * @param accountRepository Account DAO
     * @param likeRepository    Like DAO
     * @param account           VK account data
     * @param idList            id list for processing
     */
    public LikeTask(AccountRepository accountRepository, LikeRepository likeRepository, Account account, List<Long> idList) {
        this.accountRepository = accountRepository;
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
                account.setAccessToken(vk.getAccessToken().getAccessTokenProperty());
                account.setExpiresIn(vk.getAccessToken().getExpiresIn());
                account.setUserId(Long.valueOf(vk.getAccessToken().getUserId()));
                accountRepository.save(account);
            } catch (AuthorizeException e) {
                LOG.error("Authorization failed!", e);
                return;
            }
        }
        this.idList.parallelStream().forEach(this::setLike);
    }

    private void setLike(Long id) {
        if (likeRepository.findByOwnerIdAndAccountId(id, account.getId()) != null) {
            likeRepository.save(new Like(id, account.getId()));
            vk.getUserPhotos(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePhoto);
            vk.getWallPosts(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePost);
        } else {
            //Already liked
        }
    }

    private void likePost(WallGetResponse wall) {
        wall.getItems().forEach(p -> {
            if (!vk.isLiked(ObjectType.POST, p.getOwnerId(), p.getId())) {
                vk.like(ObjectType.POST, p.getOwnerId(), p.getId());
            }
        });
    }

    private void likePhoto(PhotosGetAllResponse photos) {
        photos.getItems().forEach(p -> {
            if (!vk.isLiked(ObjectType.POST, p.getOwnerId(), p.getId())) {
                vk.like(ObjectType.PHOTO, p.getOwnerId(), p.getId());
            }
        });
    }

}