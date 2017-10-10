package com.github.vk.bot.account.endpoint;

import com.github.vk.bot.account.service.AccountService;
import com.github.vk.bot.common.model.AccessTokenResponse;
import com.github.vk.bot.common.test.TestUtils;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private static final String TEST_TOKEN = "token";
    private static final LocalDateTime TEST_EXPIRES = LocalDateTime.now().plusSeconds(3600L);
    private static final String TEST_EXPIRES_STRING = TEST_EXPIRES.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private static final String TEST_ACCOUNT_ID = "59c239b1a11c1223082555d0";

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
    public void shouldReturnJsonAccountByIdAndThenNotFound() throws Exception {
        Account account = new Account();
        account.setId(new ObjectId(TEST_ACCOUNT_ID));
        account.setLogin(TEST_LOGIN);
        account.setAccessToken(TEST_TOKEN);
        account.setExpirationTime(TEST_EXPIRES);
        when(accountService.getAccountById(Mockito.any(ObjectId.class))).thenReturn(account).thenReturn(null);

        mockMvc.perform(
                get("/{id}", TEST_ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(equalTo(TEST_LOGIN))))
                .andExpect(jsonPath("$.access_token", is(equalTo(TEST_TOKEN))))
                .andExpect(jsonPath("$.expiration_time", is(equalTo(TEST_EXPIRES_STRING))))
                .andExpect(jsonPath("$.id", is(equalTo("59c239b1a11c1223082555d0"))));

        mockMvc.perform(
                get("/{id}", TEST_ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        when(accountService.getAccountById(Mockito.any(ObjectId.class))).thenReturn(null);
        mockMvc.perform(
                get("/account/{id}", TEST_ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCollectionOfAccounts() throws Exception {
        Set<Account> accounts = new HashSet<>(3);
        accounts.add(new Account(TEST_LOGIN));
        accounts.add(new Account(TEST_LOGIN + "1"));
        accounts.add(new Account(TEST_LOGIN + "2"));
        when(accountService.getAccounts()).thenReturn(accounts);
        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].login", containsInAnyOrder(TEST_LOGIN, TEST_LOGIN + "1", TEST_LOGIN + "2")))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void shouldReturnNoContentAfterRemoveAccount() throws Exception {
        mockMvc.perform(delete("/{id}", TEST_ACCOUNT_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    public void shouldAddAccountInDb() throws Exception {
        when(accountService.save(Mockito.any(Account.class))).thenReturn(new ObjectId(TEST_ACCOUNT_ID));
        mockMvc.perform(
                post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.getResourceAsString("api/request/AccAccount.json")))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(not(equalTo(null)))));
    }

    @Test
    public void shouldReturn200AndActiveAccountsAndThenNotFoundCode() throws Exception {
        Account account = new Account(TEST_LOGIN);
        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        when(accountService.getActiveAccounts()).thenReturn(accounts).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/actual"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].login", is(equalTo(TEST_LOGIN))));

        mockMvc.perform(get("/actual"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldReturn200AndLocationAfterAttachingAccessToken() throws Exception {
        mockMvc.perform(
                put("/{account_id}/access_token", TEST_ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.getResourceAsString("api/request/AccessTokenResponse.json")))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", is(equalTo("/account/" + TEST_ACCOUNT_ID))));
        verify(accountService, times(1))
                .addAccessToken(Mockito.any(AccessTokenResponse.class), Mockito.any(ObjectId.class));
    }

}
