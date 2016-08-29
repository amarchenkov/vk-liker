package com.github.vk.liker.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created at 24.08.2016 16:59
 *
 * @author AMarchenkov
 */
@Document(collection = Like.LIKES_COLLECTION_NAME)
public class Like {

    static final String LIKES_COLLECTION_NAME = "likes";

    @Id
    private ObjectId _id;

    @Field("owner_id")
    private long ownerId;

    @Field("item_id")
    private long itemId;

    @Field("account_id")
    private ObjectId accountId;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
