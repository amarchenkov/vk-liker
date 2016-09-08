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
import com.github.vk.liker.service.AlreadyLikedService;
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

    private static final Logger LOG = LogManager.getLogger(LikeTask.class);
    private static final int ITEM_TO_LIKE_COUNT = 3;

    private transient LikeRepository likeRepository;
    private transient AccountRepository accountRepository;
    private transient AlreadyLikedService alreadyLikedService;
    private transient Account account;
    private List<Long> idList;
    private transient VK vk;
    private int delay = 2500;

    /**
     * Task initialization
     *
     * @param accountRepository   Account DAO
     * @param likeRepository      Like DAO
     * @param alreadyLikedService Service for like by another account
     * @param account             VK account data
     * @param idList              id list for processing
     */
    public LikeTask(AccountRepository accountRepository, LikeRepository likeRepository,
                    AlreadyLikedService alreadyLikedService, Account account, List<Long> idList) {
        this.accountRepository = accountRepository;
        this.likeRepository = likeRepository;
        this.alreadyLikedService = alreadyLikedService;
        this.idList = idList;
        this.account = account;
    }

    /**
     * Set delay between like action
     *
     * @param delay delay in milliseconds
     */
    public void setDelay(int delay) {
        this.delay = delay;
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
                LOG.error("Authorization failed! Account[{}]", account, e);
                return;
            }
        }
        this.idList.parallelStream().forEach(this::setLike);
    }

    private void setLike(Long id) {
        if (likeRepository.findByOwnerIdAndAccountId(id, account.getId()) == null) {
            LOG.debug("Like user[{}] items", id);
            likeRepository.save(new Like(id, account.getId()));
            vk.getUserPhotos(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePhoto);
            vk.getWallPosts(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePost);
        } else {
            alreadyLikedService.likeByAnotherAccount(id, account);
        }
    }

    private void likePost(WallGetResponse wall) {
        wall.getItems().forEach(p -> {
            if (!vk.isLiked(ObjectType.POST, p.getOwnerId(), p.getId())) {
                vk.like(ObjectType.POST, p.getOwnerId(), p.getId());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    LOG.error("Like task has been interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void likePhoto(PhotosGetAllResponse photos) {
        photos.getItems().forEach(p -> {
            if (!vk.isLiked(ObjectType.POST, p.getOwnerId(), p.getId())) {
                vk.like(ObjectType.PHOTO, p.getOwnerId(), p.getId());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    LOG.error("Like task has been interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

}