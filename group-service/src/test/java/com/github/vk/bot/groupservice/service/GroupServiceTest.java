package com.github.vk.bot.groupservice.service;

import com.github.vk.bot.common.model.group.Group;
import com.github.vk.bot.common.test.AbstractMongoTest;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created at 01.10.2017 21:24
 *
 * @author Andrey
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class GroupServiceTest extends AbstractMongoTest {

    private static final String TEST_GROUP_NAME = "test_group";

    @Autowired
    private GroupService groupService;

    @Test
    public void shouldSaveGroupAndReturnId() {
        Group group = new Group();
        group.setGroupName(TEST_GROUP_NAME);
        group.setGroupId(-11234567);
        ObjectId save = groupService.save(group);
        assertThat(save, is(not(equalTo(null))));
    }

    @Test
    public void shouldReturnAllGroups() {
        ObjectId objectId1 = new ObjectId();
        ObjectId objectId2 = new ObjectId();
        Group group1 = new Group(objectId1, 123486874,"fuck_humor");
        Group group2 = new Group(objectId2, 11111111, "fuck_humor1");
        List<Group> groupsBefore = new ArrayList<>();
        groupsBefore.add(group1);
        groupsBefore.add(group2);
        mongoTemplate.insert(groupsBefore, Group.COLLECTION_NAME);

        List<Group> groups = groupService.getAllGroups();
        assertThat(groups.size(), is(equalTo(2)));
        assertThat(groups.stream().map(Group::getId).collect(Collectors.toList()), containsInAnyOrder(objectId1, objectId2));
    }

    @Test
    public void shouldRemoveGroupById() {
        ObjectId objectId1 = new ObjectId();
        ObjectId objectId2 = new ObjectId();
        Group group1 = new Group(objectId1, 123486874,"fuck_humor");
        Group group2 = new Group(objectId2, 11111111, "fuck_humor1");
        List<Group> groupsBefore = new ArrayList<>();
        groupsBefore.add(group1);
        groupsBefore.add(group2);
        mongoTemplate.insert(groupsBefore, Group.COLLECTION_NAME);

        groupService.removeById(objectId2);
        List<Group> groups = groupService.getAllGroups();
        assertThat(groups.size(), is(equalTo(1)));
        assertThat(groups.iterator().next().getId(), is(equalTo(objectId1)));
    }

}
