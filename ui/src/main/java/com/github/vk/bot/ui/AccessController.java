package com.github.vk.bot.ui;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.model.account.AccessToken;
import com.github.vk.bot.ui.client.VkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * Created at 23.09.2017 12:21
 *
 * @author Andrey
 */
@RefreshScope
@RestController
public class AccessController {

    private final VkClient client;
    private final AccountClient accountClient;

    @Value("${vk.bot.client_id}")
    private int clientId;
    @Value("${vk.bot.client_secret}")
    private String clientSecret;

    @Autowired
    public AccessController(VkClient client, AccountClient accountClient) {
        this.client = client;
        this.accountClient = accountClient;
    }

    @RequestMapping(value = "/response/{account_id}", method = RequestMethod.GET)
    public String getCode(@RequestParam("code") String code, @PathVariable("account_id") String accountId) {
        String redirectUri = "http://localhost:8080/response/" + accountId;
        AccessToken accessTokenByCode = client.getAccessTokenByCode(clientId, clientSecret, redirectUri, code);
        accountClient.attachAccessToken(accountId, accessTokenByCode);
        return "redirect:/index.html#/Accounts";
    }

}
