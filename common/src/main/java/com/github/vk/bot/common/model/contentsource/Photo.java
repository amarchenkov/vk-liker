package com.github.vk.bot.common.model.contentsource;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created at 28.09.2017 15:59
 *
 * @author AMarchenkov
 */
@Data
@Document(collection = Photo.COLLECTION_NAME)
public class Photo implements Serializable {

    public static final String COLLECTION_NAME = "photos";

    @Id
    private ObjectId id;
    @Field("source_id")
    private long sourceId;
    @Field("album_id")
    private long albumId;
    @Field("owner_id")
    private long ownerId;
    @Field("user_id")
    private long userId;
    @Field("photo_75")
    private String photo75;
    @Field("photo_130")
    private String photo130;
    @Field("photo_640")
    private String photo640;
    @Field("photo_807")
    private String photo807;
    @Field("photo_1280")
    private String photo1280;
    @Field
    private int width;
    @Field
    private int height;
    @Field
    private String text;
    @Field
    private long date;
    @Field("access_key")
    private String accessKey;
}
