package com.github.vk.bot.ui;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.model.AccessTokenResponse;
import com.github.vk.bot.ui.client.VkClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created at 11.10.2017 11:28
 *
 * @author AMarchenkov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AccessControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private AccountClient accountClient;

    @MockBean
    private VkClient vkClient;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldSendRequestToSaveAccessTokenAndRedirect() throws Exception {
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
        accessTokenResponse.setAccessToken("qwe");
        accessTokenResponse.setExpiresIn(3600);
        accessTokenResponse.setUserId(123);
        when(vkClient.getAccessTokenByCode(anyLong(), anyString(), anyString(), anyString())).thenReturn(accessTokenResponse);

        mockMvc.perform(get("/response/{account_id}?code=123", "asdqwedaa8wdawd8a"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", is(equalTo("/index.html#/Accounts"))));
        verify(accountClient, times(1)).attachAccessToken("asdqwedaa8wdawd8a", accessTokenResponse);
        verify(vkClient, times(1)).getAccessTokenByCode(anyLong(), anyString(), anyString(), anyString());
    }

}
