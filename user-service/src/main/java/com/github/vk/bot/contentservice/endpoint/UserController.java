package com.github.vk.bot.contentservice.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created at 12.10.2017 11:08
 *
 * @author AMarchenkov
 */
@RestController
@RequestMapping(value = "/*", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
}
