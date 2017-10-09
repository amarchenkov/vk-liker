package com.github.vk.bot.contentservice.repository;

import com.github.vk.bot.common.model.content.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created at 09.10.2017 13:52
 *
 * @author AMarchenkov
 */
@Repository
public interface ItemRepository extends MongoRepository<Item, ObjectId> {
    Optional<List<Item>> findAllBySourceId(long sourceId);
    Optional<List<Item>> findAllByContentSourceId(ObjectId contentSourceId);
}
