package com.github.vk.bot.account.repository;

import com.github.vk.bot.common.model.account.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created at 26.09.2017 11:35
 *
 * @author AMarchenkov
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    @Query("{'access_token.expires_in': {'$gt': ?0}}")
    List<Account> findActiveAccount(long timestamp);
}
