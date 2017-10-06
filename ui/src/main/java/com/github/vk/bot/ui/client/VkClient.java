package com.github.vk.bot.ui.client;

import com.github.vk.bot.common.model.account.AccessToken;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created at 06.10.2017 12:26
 *
 * @author AMarchenkov
 */
@FeignClient(value = "vk", url = "${vk.access_token.url}", configuration = FeignConfiguration.class)
public interface VkClient {
    @RequestMapping(value = "/access_token", method = RequestMethod.GET)
    AccessToken getAccessTokenByCode(@RequestParam("client_id") long clientId,
                                     @RequestParam("client_secret") String clientSecret,
                                     @RequestParam("redirect_uri") String redirectUri,
                                     @RequestParam("code") String code);
}
