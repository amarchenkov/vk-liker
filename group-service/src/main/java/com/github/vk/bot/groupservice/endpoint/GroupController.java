package com.github.vk.bot.groupservice.endpoint;

import com.github.vk.bot.common.model.group.Group;
import com.github.vk.bot.groupservice.repository.GroupRepository;
import com.github.vk.bot.groupservice.service.GroupService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created at 01.10.2017 17:31
 *
 * @author Andrey
 */
@RestController
@RequestMapping(value = "/group", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addGroup(@RequestBody Group group) {
        ObjectId id = groupService.save(group);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, id.toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }
}
