package com.github.vk.bot.common.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created at 01.10.2017 17:45
 *
 * @author Andrey
 */
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    private ObjectId id;

    @SerializedName("group_id")
    @Field("group_id")
    private String groupId;

    @SerializedName("group_name")
    @Field("group_name")
    private String groupName;

}
