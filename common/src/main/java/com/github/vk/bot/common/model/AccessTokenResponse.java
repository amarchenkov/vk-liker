package com.github.vk.bot.common.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created at 10.10.2017 14:02
 *
 * @author AMarchenkov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_id")
    private long expiresIn;

    @SerializedName("user_id")
    private int userId;
}
