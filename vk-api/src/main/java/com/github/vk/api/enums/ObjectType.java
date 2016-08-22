package com.github.vk.api.enums;

/**
 * Created at 22.08.2016 10:14
 *
 * @author AMarchenkov
 */
public enum ObjectType {

    POST("post"),
    COMMENT("comment"),
    PHOTO("photo"),
    AUDIO("audio"),
    VIDEO("video"),
    NOTE("note"),
    MARKET("market"),
    PHOTO_COMMENT("photo_comment"),
    VIDEO_COMMENT("video_comment"),
    TOPIC_COMMENT("topic_comment"),
    MARKET_COMMENT("market_comment");

    private String value;

    ObjectType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
