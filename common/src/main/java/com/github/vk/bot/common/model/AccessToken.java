package com.github.vk.bot.common.model;

import lombok.Data;

import java.util.UUID;

/**
 * Created at 25.09.2017 16:37
 *
 * @author AMarchenkov
 */
@Data
public class AccessToken {
    private UUID id;
    private String accessToken;
    private long expiresIn;
    private String userId;
}
