package com.github.vk.api.enums;

/**
 * Created at 16.08.2016 23:19
 *
 * @author Andrey
 */
public enum Display {

    PAGE("page"),
    POPUP("popup"),
    MOBILE("mobile");

    private String value;

    Display(String value) {
        this.value = value;
    }

    public String  getValue() {
        return value;
    }
}