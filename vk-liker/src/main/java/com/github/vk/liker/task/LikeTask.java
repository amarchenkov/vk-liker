package com.github.vk.liker.task;

import com.github.vk.api.VK;
import com.github.vk.api.enums.ObjectType;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AccessToken;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.json.PhotosGetAllResponse;
import com.github.vk.api.models.json.WallGetResponse;
import com.github.vk.liker.model.Account;
import com.github.vk.liker.repository.AccountRepository;
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
    public static final int ITEM_TO_LIKE_COUNT = 3;

    private AuthorizeData authorizeData;
    private AccountRepository accountRepository;
    private List<Long> idList;
    private int accountIndex;
    private VK vk;

    public LikeTask(AccountRepository accountRepository, AuthorizeData authorizeData, List<Long> idList) {
        this.authorizeData = authorizeData;
        this.accountRepository = accountRepository;
        this.idList = idList;
        this.accountIndex = 1;
    }

    public LikeTask(AccountRepository accountRepository, AuthorizeData authorizeData, List<Long> idList, int accountIndex) {
        this(accountRepository, authorizeData, idList);
        this.accountIndex = accountIndex;
    }

    @Override
    protected void compute() {
        long count = accountRepository.count();
        if (idList.size() <= count) {
            Page<Account> accounts = accountRepository.findAll(new PageRequest(accountIndex, 1, new Sort(Sort.Direction.ASC, "_id")));
            if (accounts.getNumberOfElements() == 0) {
                LOG.error("Account list is empty!");
                return;
            }
            Account account = accounts.getContent().get(0);
            if (account.getExpiresIn().isAfter(LocalDateTime.now())) {
                AccessToken accessToken = new AccessToken(
                        account.getAccessToken(), account.getExpiresIn(), String.valueOf(account.getUserId()));
                this.vk = new VK(accessToken);
            } else {
                this.vk = new VK(authorizeData);
                try {
                    this.vk.updateToken(account.getLogin(), account.getPassword());
                } catch (AuthorizeException e) {
                    LOG.error("Authorization failed!", e);
                    return;
                }
            }
            this.idList.parallelStream().forEach(this::setLike);
        } else {
        }
    }

    private void setLike(Long id) {
        vk.getUserPhotos(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePhoto);
        vk.getWallPosts(id, 0, ITEM_TO_LIKE_COUNT).ifPresent(this::likePost);
    }

    private void likePost(WallGetResponse wall) {
        wall.getItems().forEach(p -> vk.like(ObjectType.POST, p.getOwnerId(), p.getId()));
    }

    private void likePhoto(PhotosGetAllResponse photos) {
        photos.getItems().forEach(p -> vk.like(ObjectType.PHOTO, p.getOwnerId(), p.getId()));
    }

}
