package com.github.vk.liker.source.impl;

import com.github.vk.api.VK;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AccessToken;
import com.github.vk.liker.exception.NoAccountException;
import com.github.vk.liker.jmx.GroupSourceMBean;
import com.github.vk.liker.jmx.SearchSourceMBean;
import com.github.vk.liker.model.Account;
import com.github.vk.liker.repository.AccountRepository;
import com.github.vk.liker.service.LikeService;
import com.github.vk.liker.task.SourceTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created at 29.08.2016 13:11
 *
 * @author AMarchenkov
 */
@Service
@ManagedResource(objectName = "Liker:name=SearchSource")
public class SearchSource implements SearchSourceMBean {

    private static final Logger LOG = LogManager.getLogger(GroupSourceMBean.class);

    private LikeService likeService;
    private AccountRepository accountRepository;
    private int fromAge = 0;
    private int toAge = 999;
    private String city;
    private String country;

    @ManagedAttribute
    public void setFromAge(int fromAge) {
        this.fromAge = fromAge;
    }

    @ManagedAttribute
    public void setToAge(int toAge) {
        this.toAge = toAge;
    }

    @ManagedAttribute
    public void setCity(String city) {
        this.city = city;
    }

    @ManagedAttribute
    public void setCountry(String country) {
        this.country = country;
    }

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @ManagedOperation(description = "Start liking search result")
    public void start() {
        Map<String, String> params = new HashMap<>();
        params.put("age_from", String.valueOf(fromAge));
        params.put("age_to", String.valueOf(toAge));
        if (city != null) {
            params.put("city", city);
        }
        if (country != null) {
            params.put("country", country);
        }
        Account account = accountRepository.findOneByAccessTokenNotNullAndExpiresInGreaterThan(LocalDateTime.now());
        if (account == null) {
            LOG.error("There no account with token");
            throw new NoAccountException("There no account with token");
        }
        VK vk = new VK(new AccessToken(account.getAccessToken(), account.getExpiresIn(), String.valueOf(account.getUserId())));
        LOG.info("Start vk search with params = [{}]", params.toString());
        final List<Long> idList = new ArrayList<>();
        vk.getUser(params).ifPresent(user -> {
            user.getItems().forEach(u -> idList.add(u.getId()));
            likeService.addListToProcess(new SourceTask(UUID.randomUUID(), idList));
        });
    }
}
