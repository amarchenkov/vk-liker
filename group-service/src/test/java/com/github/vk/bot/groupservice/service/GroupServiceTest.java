package com.github.vk.bot.groupservice.service;

import com.github.vk.bot.common.model.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

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
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    public void shouldReturnAllGroups() {
        Set<Group> groups = groupService.getAllGroups();
        assertThat(groups.size(), is(equalTo(3)));
//        assertThat(groups, containsInAnyOrder());
    }

}
