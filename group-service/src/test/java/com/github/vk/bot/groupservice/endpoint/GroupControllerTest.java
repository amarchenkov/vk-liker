package com.github.vk.bot.groupservice.endpoint;

import com.github.vk.bot.common.model.group.Group;
import com.github.vk.bot.common.test.TestUtils;
import com.github.vk.bot.groupservice.service.impl.DbGroupService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created at 01.10.2017 17:34
 *
 * @author Andrey
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@WebAppConfiguration
public class GroupControllerTest {

    private static final String TEST_GROUP_ID = "59dcbf9b4b0f8d17acf124f8";
    private static final String TEST_GROUP_NAME = "test_group";
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private DbGroupService dbGroupService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldAddGroupToDb() throws Exception {
        when(dbGroupService.save(Mockito.any(Group.class))).thenReturn(new ObjectId());
        mockMvc.perform(
                post("/")
                        .content(TestUtils.getResourceAsString("api/request/AddGroup.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(not(equalTo(null)))));
    }

    @Test
    public void shouldReturn200AndArrayOfGroups() throws Exception {
        Set<Group> groups = new HashSet<>();
        Group group1 = new Group();
        group1.setGroupName(TEST_GROUP_NAME);
        groups.add(group1);

        Group group2 = new Group();
        group2.setGroupName(TEST_GROUP_NAME + "1");
        groups.add(group2);
        when(dbGroupService.getAllGroups()).thenReturn(groups);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].group_name", containsInAnyOrder(TEST_GROUP_NAME, TEST_GROUP_NAME + "1")));
    }

    @Test
    public void shouldRemoveGroupBuIdAndReturnNoContent() throws Exception {
        mockMvc.perform(
                delete("/{group_id}", TEST_GROUP_ID))
                .andExpect(status().isNoContent());
        verify(dbGroupService, times(1)).removeById(new ObjectId(TEST_GROUP_ID));
    }

}
