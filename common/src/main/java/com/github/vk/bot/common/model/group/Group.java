package com.github.vk.bot.common.model.group;

import com.github.vk.bot.common.model.content.Item;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created at 01.10.2017 17:45
 *
 * @author Andrey
 */
@Data
@Document(collection = Group.COLLECTION_NAME)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Group implements Serializable {

    public static final String COLLECTION_NAME = "groups";

    @Id
    @NonNull
    private ObjectId id;

    @NonNull
    @SerializedName("group_id")
    @Field("group_id")
    private String groupId;

    @NonNull
    @SerializedName("group_name")
    @Field("group_name")
    private String groupName;

    @Field("posted_items")
    @SerializedName("posted_items")
    private Set<Item> postedItems = new HashSet<>();
}
