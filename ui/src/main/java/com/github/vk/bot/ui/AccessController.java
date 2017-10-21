package com.github.vk.bot.ui;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.model.AccessTokenResponse;
import com.github.vk.bot.ui.client.VkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created at 23.09.2017 12:21
 *
 * @author Andrey
 */
@RefreshScope
@Controller
public class AccessController {

    private final VkClient client;
    private final AccountClient accountClient;

    @Value("${vk.bot.client_id}")
    private int clientId;

    @Value("${vk.bot.client_secret}")
    private String clientSecret;

    private final Environment environment;

    @Autowired
    public AccessController(VkClient client, AccountClient accountClient, Environment environment) {
        this.client = client;
        this.accountClient = accountClient;
        this.environment = environment;
    }

    @RequestMapping(value = "/response/{account_id}", method = RequestMethod.GET)
    public String getCode(@RequestParam("code") String code, @PathVariable("account_id") String accountId) {
        String redirectUri = "http://localhost:8080/response/" + accountId;
        AccessTokenResponse accessTokenByCode = client.getAccessTokenByCode(clientId, clientSecret, redirectUri, code);
        accountClient.attachAccessToken(accountId, accessTokenByCode);
        return "redirect:/index.html#/Accounts";
    }

    @RequestMapping("/param/{name:.+}")
    @ResponseBody
    public String getParamValue(@PathVariable("name") String name) {
        String value = environment.getProperty(name);
        return value != null ? value : "";
    }

}
