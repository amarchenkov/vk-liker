package com.github.vk.bot.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created at 25.09.2017 16:31
 *
 * @author AMarchenkov
 */
@Data
public class Account implements Serializable {
    private UUID id;
    private String login;
    private String password;
    private AccessToken accessToken;
}
