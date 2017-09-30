package com.github.vk.bot.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created at 22.09.2017 17:42
 *
 * @author AMarchenkov
 */
@Controller
@RefreshScope
public class HomeController {

    @Value("${vk.bot.client_id:-1}")
    private long clientId;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String home() {
        return "index.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/configuration")
    public Map<String, String> getConfiguration() {
        return new HashMap<>();
    }
}
