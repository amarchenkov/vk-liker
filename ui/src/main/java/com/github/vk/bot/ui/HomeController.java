package com.github.vk.bot.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created at 22.09.2017 17:42
 *
 * @author AMarchenkov
 */
@Controller
public class HomeController {
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String home() {
        return "index.html";
    }
}
