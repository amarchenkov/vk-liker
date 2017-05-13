package com.github.vk.liker.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created at 24.08.2016 16:59
 *
 * @author AMarchenkov
 */
@Getter
@Setter
@Document(collection = Like.LIKES_COLLECTION_NAME)
public class Like {

    static final String LIKES_COLLECTION_NAME = "likes";

    @Id
    private ObjectId id;

    @Field("owner_id")
    private long ownerId;

    @Field("account_id")
    private ObjectId accountId;

    /**
     * Quick initialization of entity
     *
     * @param ownerId Object owner id
     * @param id      Liked account ID
     */
    public Like(long ownerId, ObjectId id) {
        this.ownerId = ownerId;
        this.accountId = id;
    }

}
