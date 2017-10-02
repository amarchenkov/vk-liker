package com.github.vk.bot.common.model.content;

import com.github.vk.bot.common.enums.SourceType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created at 02.10.2017 17:48
 *
 * @author AMarchenkov
 */
@Data
@Document(collection = Source.COLLECTION_NAME)
public class Source implements Serializable {
    public static final String COLLECTION_NAME = "sources";

    @Id
    private ObjectId id;

    @Field("source_name")
    @SerializedName("source_name")
    private String sourceName;

    @Field("source_type")
    @SerializedName("source_type")
    private SourceType sourceType;

    @Field("source_items")
    @SerializedName("source_items")
    private Set<Item> sourceItems = new HashSet<>();
}
