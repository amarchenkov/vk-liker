package com.github.vk.api.models.json;

import com.github.vk.api.enums.ObjectType;
import com.google.gson.annotations.SerializedName;

/**
 * Created at 24.08.2016 10:44
 *
 * @author AMarchenkov
 */
public class WallItem {

    private long id;

    @SerializedName("from_id")
    private long fromId;

    @SerializedName("owner_id")
    private long ownerId;

    private long date;

    @SerializedName("post_type")
    private ObjectType postType;

    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ObjectType getPostType() {
        return postType;
    }

    public void setPostType(ObjectType postType) {
        this.postType = postType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
