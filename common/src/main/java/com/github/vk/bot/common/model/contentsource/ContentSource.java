package com.github.vk.bot.common.model.contentsource;

import com.github.vk.bot.common.enums.SourceType;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created at 28.09.2017 15:34
 *
 * @author AMarchenkov
 */
@Data
@Document(collection = ContentSource.COLLECTION_NAME)
public class ContentSource implements Serializable {

    public static final String COLLECTION_NAME = "content_sources";

    @Id
    private ObjectId id;
    @Field
    private String url;
    @Field("source_type")
    private SourceType sourceType;
    @Field("last_check")
    private LocalDateTime lastCheck;
    @Field("items")
    private Set<Item> items = new HashSet<>();
}
