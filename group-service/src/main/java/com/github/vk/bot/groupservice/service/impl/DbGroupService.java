package com.github.vk.bot.groupservice.service.impl;

import com.github.vk.bot.common.model.group.Group;
import com.github.vk.bot.groupservice.repository.GroupRepository;
import com.github.vk.bot.groupservice.service.GroupService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created at 01.10.2017 21:27
 *
 * @author Andrey
 */
@Service
public class DbGroupService implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public DbGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Set<Group> getAllGroups() {
        return new HashSet<>(groupRepository.findAll());
    }

    @Override
    public ObjectId save(Group group) {
        Group saved = groupRepository.save(group);
        return saved.getId();
    }

    @Override
    public void removeById(ObjectId id) {
        groupRepository.delete(id);
    }

}
