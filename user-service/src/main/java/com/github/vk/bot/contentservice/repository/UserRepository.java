package com.github.vk.bot.contentservice.repository;

import com.github.vk.bot.common.model.user.GroupUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created at 12.10.2017 11:10
 *
 * @author AMarchenkov
 */
@Repository
public interface UserRepository extends MongoRepository<GroupUser, ObjectId> {
    Optional<List<GroupUser>> findAllBySourceUserId(int sourceUserId);
}
