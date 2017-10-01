package com.github.vk.bot.groupservice.service;

import com.github.vk.bot.common.model.Group;

import java.util.Set;

/**
 * Created at 01.10.2017 21:26
 *
 * @author Andrey
 */
public interface GroupService {
    Set<Group> getAllGroups();
}
