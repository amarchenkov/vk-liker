package com.github.vk.bot.contentservice.endpoint;

import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.common.test.TestUtils;
import com.github.vk.bot.contentservice.service.ContentSourceService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created at 11.10.2017 10:35
 *
 * @author AMarchenkov
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class SourceControllerTest {

    private static final String TEST_SOURCE_NAME = "TEST-SOURCE";
    private static final String TEST_CONTENT_SOURCE_ID = "59c239b1a11c1223082555d0";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ContentSourceService contentSourceService;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnListOfSource() throws Exception {
        ContentSource contentSource1 = new ContentSource();
        contentSource1.setName(TEST_SOURCE_NAME);
        ContentSource contentSource2 = new ContentSource();
        contentSource2.setName(TEST_SOURCE_NAME + "1");
        List<ContentSource> sources = new ArrayList<>();
        sources.add(contentSource1);
        sources.add(contentSource2);
        when(contentSourceService.getAllSources()).thenReturn(sources);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].name", containsInAnyOrder(TEST_SOURCE_NAME, TEST_SOURCE_NAME + "1")));
    }

    @Test
    public void shouldAddContentSourceInDb() throws Exception {
        ObjectId objectId = new ObjectId();
        when(contentSourceService.saveSource(Mockito.any(ContentSource.class))).thenReturn(objectId);
        mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.getResourceAsString("api/request/AddContentSource.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, is(equalTo("/" + objectId.toString()))));
    }

    @Test
    public void shouldRemoveContentSourceReturnNoContent() throws Exception {
        mockMvc.perform(
                delete("/{content_source_id}", TEST_CONTENT_SOURCE_ID))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

}
