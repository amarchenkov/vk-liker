package com.github.vk.bot.common.model.account;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created at 25.09.2017 16:31
 *
 * @author AMarchenkov
 */
@Data
@NoArgsConstructor
@Document(collection = Account.COLLECTION_NAME)
public class Account implements Serializable {

    public static final String COLLECTION_NAME = "accounts";

    @Id
    private ObjectId id;

    @Field
    private String login;

    @Field("access_token")
    @SerializedName("access_token")
    private String accessToken;

    @Field("user_id")
    @SerializedName("user_id")
    private int userId;

    @Field("expiration_time")
    @SerializedName("expiration_time")
    private LocalDateTime expirationTime; //NOSONAR

    public Account(String login) {
        this.login = login;
    }
}
