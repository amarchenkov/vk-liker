package com.github.vk.liker.repository;

import com.github.vk.liker.model.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created at 17.08.2016 10:52
 *
 * @author AMarchenkov
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {
}