package com.github.vk.bot.common.model.user;

import com.github.vk.bot.common.enums.Gender;
import com.github.vk.bot.common.enums.SourceType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created at 12.10.2017 10:50
 *
 * @author AMarchenkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = GroupUser.COLLECTION_NAME)
public class GroupUser {

    public static final String COLLECTION_NAME = "users";

    @Id
    private ObjectId id;

    @Field("source_user_id")
    @SerializedName("source_user_id")
    private int sourceUserId;

    @Field("source_type")
    @SerializedName("source_type")
    private SourceType sourceType;

    @Field("first_name")
    @SerializedName("first_name")
    private String firstName;

    @Field("last_name")
    @SerializedName("last_name")
    private String lastName;

    @Field
    private String state;

    @Field
    private String country;

    @Field
    private Gender gender;

    @Field("invite_allowed")
    @SerializedName("invite_allowed")
    private boolean inviteAllowed;

    @Field("content_source_from")
    @SerializedName("content_source_from")
    private ObjectId contentSourceFrom;

}
