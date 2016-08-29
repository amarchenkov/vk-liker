package com.github.vk.liker.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Created at 16.08.2016 19:57
 *
 * @author Andrey
 */
@Document(collection = Account.ACCOUNTS_COLLECTION_NAME)
public class Account {

    static final String ACCOUNTS_COLLECTION_NAME = "accounts";

    @Id
    private ObjectId _id;

    @Field
    private String login;

    @Field
    private String password;

    @Field("user_id")
    private long userId;

    @Field("access_token")
    private String accessToken;

    @Field("expires_in")
    private LocalDateTime expiresIn;


    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }
}