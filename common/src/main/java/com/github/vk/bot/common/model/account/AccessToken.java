package com.github.vk.bot.common.model.account;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken implements Serializable {

    public static final String COLLECTION_NAME = "access_tokens";

    @Id
    private ObjectId id;

    @Field("access_token")
    @SerializedName("access_token")
    private String token;

    @Field("expires_in")
    @SerializedName("expires_in")
    private long expiresIn;

    @Field("user_id")
    @SerializedName("user_id")
    private int userId;

    public AccessToken(String activeToken, long expiresIn) {
        this.token = activeToken;
        this.expiresIn = expiresIn;
    }
}
