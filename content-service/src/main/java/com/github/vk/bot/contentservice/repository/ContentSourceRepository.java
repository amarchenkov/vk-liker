package com.github.vk.bot.contentservice.repository;

import com.github.vk.bot.common.enums.SourceType;
import com.github.vk.bot.common.model.content.ContentSource;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created at 05.10.2017 14:26
 *
 * @author AMarchenkov
 */
@Repository
public interface ContentSourceRepository extends MongoRepository<ContentSource, ObjectId> {
    Optional<List<ContentSource>> findAllBySourceType(SourceType sourceType);
    Optional<List<ContentSource>> findAllByItems_SourceId(long sourceId);
}
