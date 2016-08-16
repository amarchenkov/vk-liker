package com.github.vk.liker.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created at 16.08.2016 20:02
 *
 * @author Andrey
 */
@Document(collection = Group.GROUPS_COLLECTION_NAME)
public class Group {

    static final String GROUPS_COLLECTION_NAME = "groups";

    @Id
    private ObjectId _id;

    @Field(value = "group_id")
    private String groupId;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
