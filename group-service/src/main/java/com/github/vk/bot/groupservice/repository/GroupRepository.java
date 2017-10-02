package com.github.vk.bot.groupservice.repository;

import com.github.vk.bot.common.model.group.Group;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created at 01.10.2017 21:33
 *
 * @author Andrey
 */
@Repository
public interface GroupRepository extends MongoRepository<Group, ObjectId> {
}
