package com.github.vk.bot.common.model.account;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created at 25.09.2017 16:31
 *
 * @author AMarchenkov
 */
@Data
@Document(collection = Account.COLLECTION_NAME)
public class Account implements Serializable {

    public static final String COLLECTION_NAME = "accounts";

    @Id
    private ObjectId id;
    @Field
    private String login;
    @Field
    private String password;
    @Field("access_token")
    private AccessToken accessToken;

    public Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
