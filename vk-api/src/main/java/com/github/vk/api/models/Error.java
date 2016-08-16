package com.github.vk.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created at 16.08.2016 23:53
 *
 * @author Andrey
 */
public class Error {

    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}