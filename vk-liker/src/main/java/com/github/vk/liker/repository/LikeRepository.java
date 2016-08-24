package com.github.vk.liker.repository;

import com.github.vk.liker.model.Like;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created at 24.08.2016 17:02
 *
 * @author AMarchenkov
 */
@Repository
public interface LikeRepository extends MongoRepository<Like, ObjectId> {
    /**
     * Find liked object
     *
     * @param ownerId Owner item
     * @param itemId  item
     * @return Pair of owner => item
     */
    Like findByOwnerIdAndItemId(long ownerId, long itemId);
}
