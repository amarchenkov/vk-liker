package com.github.vk.bot.account.service;

import com.github.vk.bot.common.model.AccessTokenResponse;
import com.github.vk.bot.common.model.account.Account;
import com.github.vk.bot.common.test.AbstractMongoTest;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created at 26.09.2017 11:42
 *
 * @author AMarchenkov
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class AccountServiceTest extends AbstractMongoTest {

    private static final String TEST_LOGIN = "login";
    private static final String ACTIVE_TOKEN = "bla-bla-token";
    private static final String INACTIVE_TOKEN = "bad-bad-token";
    private static final String TEST_TOKEN = "token";
    private static final LocalDateTime TEST_EXPIRATION_TIME = LocalDateTime.now().plusSeconds(3600L);
    private static final long TEST_EXPIRES_IN = 123456780;
    private static final int TEST_USER_ID = 12345678;

    @Autowired
    private AccountService accountService;

    @Test
    public void shouldSaveAccountModelInMongoDb() {
        Account account = new Account();
        account.setLogin(TEST_LOGIN);
        ObjectId id = accountService.save(account);
        assertNotNull(id);
        List<Account> accounts = getAccountsByLogin(TEST_LOGIN);
        assertThat(accounts, hasSize((1)));
        assertThat(accounts.get(0).getLogin(), is(equalTo(TEST_LOGIN)));
    }

    private List<Account> getAccountsByLogin(String login) {
        Query query = new Query();
        query.addCriteria(Criteria.where("login").is(login));
        return mongoTemplate.find(query, Account.class);
    }

    private List<Account> getAccounts() {
        return mongoTemplate.find(new Query(), Account.class);
    }

    @Test
    public void shouldRemoveOnlyOneAccountById() {
        ObjectId objectId1 = new ObjectId();
        Account account1 = new Account();
        account1.setLogin(TEST_LOGIN);
        account1.setId(objectId1);

        ObjectId objectId2 = new ObjectId();
        Account account2 = new Account();
        account2.setLogin(TEST_LOGIN);
        account2.setId(objectId2);
        mongoTemplate.insert(account1, Account.COLLECTION_NAME);
        mongoTemplate.insert(account2, Account.COLLECTION_NAME);

        accountService.removeById(objectId1);

        List<Account> accounts = getAccounts();
        assertThat(accounts, hasSize(1));
        assertThat(accounts.get(0).getId(), is(equalTo(objectId2)));
    }

    @Test
    public void shouldRemoveAccountFromDb() {
        Account account = new Account();
        account.setLogin(TEST_LOGIN);

        mongoTemplate.insert(account, Account.COLLECTION_NAME);

        accountService.remove(account);

        List<Account> accounts = getAccounts();
        assertThat(accounts, hasSize(0));
    }

    @Test
    public void shouldAddAccessTokenToAccountByObjectId() {
        ObjectId objectId = new ObjectId();
        Account account = new Account();
        account.setId(objectId);
        mongoTemplate.insert(account, Account.COLLECTION_NAME);

        AccessTokenResponse token = new AccessTokenResponse(TEST_TOKEN, TEST_EXPIRES_IN, TEST_USER_ID);
        accountService.addAccessToken(token, objectId);

        List<Account> accounts = getAccounts();
        assertThat(accounts, hasSize(1));
        assertThat(accounts.get(0).getAccessToken(), is(not(equalTo(null))));
        assertThat(accounts.get(0).getExpirationTime(), is(not(equalTo(null))));
        assertThat(accounts.get(0).getUserId(), is(not(equalTo(null))));
    }

    @Test
    public void shouldReturnAccountsWithNonExpiredToken() {
        ObjectId objectId1 = new ObjectId();
        Account account1 = new Account();
        account1.setLogin(TEST_LOGIN);
        account1.setId(objectId1);
        account1.setAccessToken(ACTIVE_TOKEN);
        account1.setUserId(TEST_USER_ID);
        account1.setExpirationTime(LocalDateTime.now().plusSeconds(3600));

        ObjectId objectId2 = new ObjectId();
        Account account2 = new Account();
        account2.setLogin(TEST_LOGIN);
        account2.setId(objectId2);
        account2.setAccessToken(INACTIVE_TOKEN);
        account2.setUserId(TEST_USER_ID);
        account2.setExpirationTime(LocalDateTime.now().minusSeconds(3600));

        mongoTemplate.insert(account1, Account.COLLECTION_NAME);
        mongoTemplate.insert(account2, Account.COLLECTION_NAME);

        Set<Account> activeAccounts = accountService.getActiveAccounts();
        assertThat(activeAccounts, hasSize(1));
        assertThat(activeAccounts.iterator().next().getAccessToken(), is(equalTo(ACTIVE_TOKEN)));
    }

    @Test
    public void shouldReturnCollectionOfAccounts() {
        ObjectId objectId1 = new ObjectId();
        Account account1 = new Account();
        account1.setLogin(TEST_LOGIN);
        account1.setId(objectId1);
        account1.setAccessToken(ACTIVE_TOKEN);
        account1.setExpirationTime(TEST_EXPIRATION_TIME);
        account1.setUserId(TEST_USER_ID);

        ObjectId objectId2 = new ObjectId();
        Account account2 = new Account();
        account2.setLogin(TEST_LOGIN);
        account2.setId(objectId2);
        account2.setAccessToken(INACTIVE_TOKEN);
        account2.setExpirationTime(TEST_EXPIRATION_TIME);
        account2.setUserId(TEST_USER_ID);
        mongoTemplate.insert(account1, Account.COLLECTION_NAME);
        mongoTemplate.insert(account2, Account.COLLECTION_NAME);

        Set<Account> activeAccounts = accountService.getAccounts();
        assertThat(activeAccounts, hasSize(2));
    }

}




