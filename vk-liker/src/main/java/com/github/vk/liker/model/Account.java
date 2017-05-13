package com.github.vk.liker.model;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@Document(collection = Account.ACCOUNTS_COLLECTION_NAME)
public class Account {

    static final String ACCOUNTS_COLLECTION_NAME = "accounts";

    @Id
    private ObjectId id;

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

}