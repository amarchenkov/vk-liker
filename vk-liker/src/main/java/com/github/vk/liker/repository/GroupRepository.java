package com.github.vk.liker.repository;

import com.github.vk.liker.model.Group;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created at 22.08.2016 16:06
 *
 * @author AMarchenkov
 */
@Repository
public interface GroupRepository extends MongoRepository<Group, ObjectId> {
}
