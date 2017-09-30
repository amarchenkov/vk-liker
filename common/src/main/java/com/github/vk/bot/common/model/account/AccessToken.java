package com.github.vk.bot.common.model.account;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created at 25.09.2017 16:37
 *
 * @author AMarchenkov
 */
@Data
@Document(collection = AccessToken.COLLECTION_NAME)
public class AccessToken implements Serializable {

    public static final String COLLECTION_NAME = "access_tokens";

    @Id
    private ObjectId id;
    @Field("token")
    private String token;
    @Field("expires_in")
    @SerializedName("expires_in")
    private long expiresIn;
    @Field("user_id")
    @SerializedName("user_id")
    private String userId;

    public AccessToken() {
    }

    public AccessToken(String activeToken, long expiresIn) {
        this.token = activeToken;
        this.expiresIn = expiresIn;
    }
}
