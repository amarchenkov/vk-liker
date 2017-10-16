package com.github.vk.bot.common.model.content;

import com.github.vk.bot.common.enums.PostType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created at 28.09.2017 15:50
 *
 * @author AMarchenkov
 */
@Data
@Document(collection = Item.COLLECTION_NAME)
public class Item implements Serializable {

    public static final String COLLECTION_NAME = "items";

    @Id
    private ObjectId id;

    /**
     * Идентификатор записи по ВК
     */
    @Field("source_id")
    @SerializedName("source_id")
    private long sourceId;

    /**
     * время публикации записи в формате unix-time.
     */
    @Field
    private long date;

    /**
     * тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
     */
    @Field("post_type")
    @SerializedName("post_type")
    private PostType postType;

    /**
     * текст записи.
     */
    @Field
    private String text;

    @Field
    private List<Attachment> attachments = new ArrayList<>();

    @Field("content_source_id")
    private ObjectId contentSourceId;
}
