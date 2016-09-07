package com.github.vk.liker.repository;

import com.github.vk.liker.model.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Created at 17.08.2016 10:52
 *
 * @author AMarchenkov
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    /**
     * Get account for already liked id by another account
     *
     * @param id another account
     * @return new account
     */
    Account findOneByIdNot(ObjectId id);

    /**
     * Get first account with not empty non-exired access token
     *
     * @param now Current timestamp
     * @return Account with not expired access token
     */
    Account findOneByAccessTokenNotNullAndExpiresInGreaterThan(LocalDateTime now);
}