package com.github.vk.api.enums;

/**
 * Created at 16.08.2016 23:15
 *
 * @author Andrey
 */
public enum ResponseType {

    TOKEN("token"),
    CODE("code");

    private String value;

    ResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
