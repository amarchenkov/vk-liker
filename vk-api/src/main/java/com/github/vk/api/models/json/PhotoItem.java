package com.github.vk.api.models.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created at 24.08.2016 11:23
 *
 * @author AMarchenkov
 */
public class PhotoItem {

    private long id;

    @SerializedName("album_id")
    private long albumId;

    @SerializedName("owner_id")
    private long ownerId;

    private String text;

    private long date;

    @SerializedName("post_id")
    private long postId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
