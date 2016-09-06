package com.github.vk.api.models.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created at 22.08.2016 11:28
 *
 * @param <T> Type of value for <i>response</i> key
 * @author AMarchenkov
 */
public class Response<T> {

    @SerializedName("response")
    private T responseKey;

    public T getResponseKey() {
        return responseKey;
    }

    public void setResponseKey(T responseKey) {
        this.responseKey = responseKey;
    }
}
