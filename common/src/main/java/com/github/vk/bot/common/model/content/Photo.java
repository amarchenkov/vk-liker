package com.github.vk.bot.common.model.content;

import com.google.gson.annotations.SerializedName;
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

    /**
     * идентификатор фотографии
     */
    @Field("source_id")
    @SerializedName("source_id")
    private long sourceId;

    /**
     * идентификатор владельца фотографии.
     */
    @Field("owner_id")
    @SerializedName("owner_id")
    private long ownerId;

    /**
     * идентификатор пользователя, загрузившего фото (если фотография размещена в сообществе). Для фотографий, размещенных от имени сообщества, user_id = 100.
     */
    @Field("user_id")
    @SerializedName("user_id")
    private long userId;

    /**
     * URL копии фотографии с максимальным размером 75x75px.
     */
    @Field("photo_75")
    @SerializedName("photo_75")
    private String photo75;

    /**
     * URL копии фотографии с максимальным размером 130x130px.
     */
    @Field("photo_130")
    @SerializedName("photo_130")
    private String photo130;

    /**
     * URL копии фотографии с максимальным размером 604x604px.
     */
    @Field("photo_604")
    @SerializedName("photo_604")
    private String photo604;

    /**
     * URL копии фотографии с максимальным размером 807x807px.
     */
    @Field("photo_807")
    @SerializedName("photo_807")
    private String photo807;

    /**
     * URL копии фотографии с максимальным размером 1280x1024px.
     */
    @Field("photo_1280")
    @SerializedName("photo_1280")
    private String photo1280;

    /**
     * URL копии фотографии с максимальным размером 2560x2048px.
     */
    @Field("photo_2560")
    @SerializedName("photo_2560")
    private String photo2560;

    /**
     * ширина оригинала фотографии в пикселах.
     */
    @Field
    private int width;

    /**
     * высота оригинала фотографии в пикселах.
     */
    @Field
    private int height;

    /**
     * текст описания фотографии.
     */
    @Field
    private String text;

    /**
     * дата добавления в формате Unixtime.
     */
    @Field
    private long date;

}
