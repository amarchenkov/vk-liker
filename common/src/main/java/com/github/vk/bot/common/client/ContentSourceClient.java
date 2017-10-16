package com.github.vk.bot.common.client;

import com.github.vk.bot.common.model.content.ContentSource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created at 12.10.2017 11:39
 *
 * @author AMarchenkov
 */
@FeignClient(configuration = FeignConfiguration.class, name = "content-source-client", url = "${vk.bot.content-url:http://localhost:8095}")
public interface ContentSourceClient {
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    List<ContentSource> getAllContentSource();
}
