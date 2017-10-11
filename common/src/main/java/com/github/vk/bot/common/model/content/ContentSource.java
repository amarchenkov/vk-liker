package com.github.vk.bot.common.model.content;

import com.github.vk.bot.common.enums.SourceType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private String name;

    @Field("source_id")
    @SerializedName("source_id")
    private int sourceId;

    @Field("source_type")
    @SerializedName("source_type")
    private SourceType sourceType;

    @Field("last_check")
    @SerializedName("last_check")
    private LocalDateTime lastCheck; //NOSONAR

}
