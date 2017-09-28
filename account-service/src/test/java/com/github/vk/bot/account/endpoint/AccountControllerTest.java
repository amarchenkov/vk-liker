package com.github.vk.bot.account.endpoint;

import com.github.vk.bot.account.service.AccountService;
import com.github.vk.bot.common.model.account.AccessToken;
import com.github.vk.bot.common.model.account.Account;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created at 26.09.2017 16:32
 *
 * @author AMarchenkov
 */
@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class AccountControllerTest {

    private static final String TEST_LOGIN = "login";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_TOKEN = "token";
    private static final long TEST_EXPIRES = 123456789;
    private static final String ACCOUNT_ID = "59c239b1a11c1223082555d0";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AccountService accountService;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void shouldReturnJsonAccountById() throws Exception {
        Account account = new Account();
        AccessToken accessToken = new AccessToken(TEST_TOKEN, TEST_EXPIRES);
        account.setId(new ObjectId(ACCOUNT_ID));
        account.setLogin(TEST_LOGIN);
        account.setPassword(TEST_PASSWORD);
        account.setAccessToken(accessToken);
        when(accountService.getAccountById(Mockito.any(ObjectId.class))).thenReturn(account);
        mockMvc.perform(
                get("/account/{id}", ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(equalTo(TEST_LOGIN))))
                .andExpect(jsonPath("$.password", is(equalTo(TEST_PASSWORD))))
                .andExpect(jsonPath("$.accessToken.token", is(equalTo(TEST_TOKEN))))
                .andExpect(jsonPath("$.accessToken.expiresIn", is(equalTo(new Long(TEST_EXPIRES).intValue()))))
                .andExpect(jsonPath("$.id", is(equalTo("59c239b1a11c1223082555d0"))))
        ;
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        when(accountService.getAccountById(Mockito.any(ObjectId.class))).thenReturn(null);
        mockMvc.perform(
                get("/account/{id}", ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCollectionOfAccounts() throws Exception {
        Set<Account> accounts = new HashSet<>(3);
        accounts.add(new Account(TEST_LOGIN, TEST_PASSWORD));
        accounts.add(new Account(TEST_LOGIN + "1", TEST_PASSWORD));
        accounts.add(new Account(TEST_LOGIN + "2", TEST_PASSWORD));
        when(accountService.getAccounts()).thenReturn(accounts);
        mockMvc.perform(get("/account").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].login", containsInAnyOrder(TEST_LOGIN, TEST_LOGIN + "1", TEST_LOGIN + "2")))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void shouldReturnNoContentAfterRemoveAccount() throws Exception {
        mockMvc.perform(delete("/account/{id}", ACCOUNT_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}
