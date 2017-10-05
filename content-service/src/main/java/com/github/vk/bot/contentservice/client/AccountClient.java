package com.github.vk.bot.contentservice.client;

import com.github.vk.bot.common.model.account.Account;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created at 05.10.2017 22:06
 *
 * @author Andrey
 */
@RefreshScope
@FeignClient(value = "account", url = "${vk.bot.content-service.account_url}")
public interface AccountClient {
    @RequestMapping(value = "/account", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Account> getAllAccounts();
}
