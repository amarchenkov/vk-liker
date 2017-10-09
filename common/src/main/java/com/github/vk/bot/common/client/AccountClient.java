package com.github.vk.bot.common.client;

import com.github.vk.bot.common.model.account.AccessToken;
import com.github.vk.bot.common.model.account.Account;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

/**
 * Created at 05.10.2017 22:06
 *
 * @author Andrey
 */
@RefreshScope
@FeignClient(value = "account", url = "${vk.bot.account-url}", configuration = FeignConfiguration.class)
public interface AccountClient {
    @RequestMapping(value = "/account", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Account> getAllAccounts();

    @RequestMapping(method = RequestMethod.PUT, value = "/account/{account_id}/access_token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void attachAccessToken(@PathVariable("account_id") String accountId, @RequestBody AccessToken accessToken);

    @RequestMapping(value = "/account/actual", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Set<Account> getActualAccounts();
}
