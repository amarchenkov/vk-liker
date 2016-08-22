package com.github.vk.api.models;

/**
 * Created at 21.08.2016 23:11
 *
 * @author Andrey
 */
public class AccessToken {

    private String accessToken;
    private long expiresIn;
    private String userId;

    public AccessToken(String queryString) {
        String[] parts = queryString.split("&");
        for (String part : parts) {
            String key = part.split("=")[0];
            String value = part.split("=")[1];
            switch (key) {
                case "access_token":
                    this.accessToken = value;
                    break;
                case "user_id":
                    this.userId = value;
                    break;
                case "expires_in":
                    this.expiresIn = Long.valueOf(value);
                    break;
            }
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return accessToken;
    }
}