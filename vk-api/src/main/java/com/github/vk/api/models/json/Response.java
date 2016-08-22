package com.github.vk.api.models.json;

/**
 * Created at 22.08.2016 11:28
 *
 * @author AMarchenkov
 */
public class Response<T> {

    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
