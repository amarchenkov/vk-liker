package com.github.vk.bot.common.model.content;

import com.github.vk.bot.common.enums.PostType;
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

    @Field("source_id")
    @SerializedName("source_id")
    private long sourceId;

    @Field("from_id")
    @SerializedName("from_id")
    private long fromId;

    @Field("owner_id")
    @SerializedName("owner_id")
    private long ownerId;

    @Field
    private long date;

    @Field("mark_as_ads")
    @SerializedName("mark_as_ads")
    private boolean markedAsAds;

    @Field("post_type")
    @SerializedName("post_type")
    private PostType postType;

    @Field
    private String text;

    @Field("can_pin")
    @SerializedName("can_pin")
    private boolean canPin;

    @Field
    private Set<Attachment> attachments = new HashSet<>();

    @Field("content_source_id")
    @SerializedName("content_source_id")
    private ObjectId contentSourceId;
}
