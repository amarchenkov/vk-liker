package com.github.vk.api.models;

import java.time.LocalDateTime;

/**
 * Created at 21.08.2016 23:11
 *
 * @author Andrey
 */
public class AccessToken {

    private String accessTokenProperty;
    private LocalDateTime expiresIn;
    private String userId;

    /**
     * Initialize properties with saved value
     *
     * @param accessTokenProperty token
     * @param expiresIn   expiration time
     * @param userId      ID
     */
    public AccessToken(String accessTokenProperty, LocalDateTime expiresIn, String userId) {
        this.accessTokenProperty = accessTokenProperty;
        this.expiresIn = expiresIn;
        this.userId = userId;
    }

    /**
     * Parse query string to initialize properties
     *
     * @param queryString query string
     */
    public AccessToken(String queryString) {
        String[] parts = queryString.split("&");
        for (String part : parts) {
            String key = part.split("=")[0];
            String value = part.split("=")[1];
            switch (key) {
                case "access_token":
                    this.accessTokenProperty = value;
                    break;
                case "user_id":
                    this.userId = value;
                    break;
                case "expires_in":
                    this.expiresIn = LocalDateTime.now().plusSeconds(Long.valueOf(value));
                    break;
                default:
                    break;
            }
        }
    }

    public String getAccessTokenProperty() {
        return accessTokenProperty;
    }

    public void setAccessTokenProperty(String accessTokenProperty) {
        this.accessTokenProperty = accessTokenProperty;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(LocalDateTime expiresIn) {
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
        return accessTokenProperty;
    }
}