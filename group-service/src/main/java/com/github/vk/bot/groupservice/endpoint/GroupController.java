package com.github.vk.bot.groupservice.endpoint;

import com.github.vk.bot.common.model.group.Group;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created at 01.10.2017 17:31
 *
 * @author Andrey
 */
@RestController
@RequestMapping(value = "/group", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addGroup(@RequestBody Group group) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
