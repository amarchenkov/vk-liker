package com.github.vk.bot.account.service;

import com.github.vk.bot.common.model.account.AccessToken;
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

    private static final String TEST_PASSWORD = "password";
    private static final String TEST_LOGIN = "login";
    private static final String ACTIVE_TOKEN = "bla-bla-token";
    private static final String INACTIVE_TOKEN = "bad-bad-token";

    @Autowired
    private AccountService accountService;

    @Test
    public void shouldSaveAccountModelInMongoDb() {
        Account account = new Account();
        account.setLogin(TEST_LOGIN);
        account.setPassword(TEST_PASSWORD);
        ObjectId id = accountService.save(account);
        assertNotNull(id);
        List<Account> accounts = getAccountsByLogin(TEST_LOGIN);
        assertThat(accounts, hasSize((1)));
        assertThat(accounts.get(0).getPassword(), is(equalTo(TEST_PASSWORD)));
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
        account1.setPassword(TEST_PASSWORD);
        account1.setLogin(TEST_LOGIN);
        account1.setId(objectId1);

        ObjectId objectId2 = new ObjectId();
        Account account2 = new Account();
        account2.setPassword(TEST_PASSWORD);
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
        account.setPassword(TEST_PASSWORD);
        account.setLogin(TEST_LOGIN);

        mongoTemplate.insert(account, Account.COLLECTION_NAME);

        accountService.remove(account);

        List<Account> accounts = getAccounts();
        assertThat(accounts, hasSize(0));
    }

    @Test
    public void shouldAddAccessTokenToAccount() {
        Account account = new Account();
        AccessToken token = new AccessToken();
        accountService.addAccessToken(token, account);
        List<Account> accounts = getAccounts();
        assertThat(accounts, hasSize(1));
        assertThat(accounts.get(0).getAccessToken().getId(), is(not(equalTo(null))));
    }

    @Test
    public void shouldAddAccessTokenToAccountByObjectId() {
        ObjectId objectId = new ObjectId();
        Account account = new Account();
        account.setId(objectId);
        mongoTemplate.insert(account, Account.COLLECTION_NAME);

        AccessToken token = new AccessToken();
        accountService.addAccessToken(token, objectId);

        List<Account> accounts = getAccounts();
        assertThat(accounts, hasSize(1));
        assertThat(accounts.get(0).getAccessToken().getId(), is(not(equalTo(null))));
    }

    @Test
    public void shouldReturnAccountsWithNonExpiredToken() {
        AccessToken activeToken = new AccessToken(ACTIVE_TOKEN, (System.currentTimeMillis() / 1000L) + 200000);
        AccessToken inactiveToken = new AccessToken(INACTIVE_TOKEN, (System.currentTimeMillis() / 1000L) - 20000);
        ObjectId objectId1 = new ObjectId();
        Account account1 = new Account();
        account1.setPassword(TEST_PASSWORD);
        account1.setLogin(TEST_LOGIN);
        account1.setId(objectId1);
        account1.setAccessToken(activeToken);

        ObjectId objectId2 = new ObjectId();
        Account account2 = new Account();
        account2.setPassword(TEST_PASSWORD);
        account2.setLogin(TEST_LOGIN);
        account2.setId(objectId2);
        account2.setAccessToken(inactiveToken);
        mongoTemplate.insert(account1, Account.COLLECTION_NAME);
        mongoTemplate.insert(account2, Account.COLLECTION_NAME);

        List<Account> activeAccounts = accountService.getActiveAccounts();
        assertThat(activeAccounts, hasSize(1));
        assertThat(activeAccounts.iterator().next().getAccessToken().getToken(), is(equalTo(ACTIVE_TOKEN)));
    }

    @Test
    public void shouldReturnCollectionOfAccounts() {
        AccessToken activeToken = new AccessToken(ACTIVE_TOKEN, (System.currentTimeMillis() / 1000L) + 200000);
        AccessToken inactiveToken = new AccessToken(INACTIVE_TOKEN, (System.currentTimeMillis() / 1000L) - 20000);
        ObjectId objectId1 = new ObjectId();
        Account account1 = new Account();
        account1.setPassword(TEST_PASSWORD);
        account1.setLogin(TEST_LOGIN);
        account1.setId(objectId1);
        account1.setAccessToken(activeToken);

        ObjectId objectId2 = new ObjectId();
        Account account2 = new Account();
        account2.setPassword(TEST_PASSWORD);
        account2.setLogin(TEST_LOGIN);
        account2.setId(objectId2);
        account2.setAccessToken(inactiveToken);
        mongoTemplate.insert(account1, Account.COLLECTION_NAME);
        mongoTemplate.insert(account2, Account.COLLECTION_NAME);

        Set<Account> activeAccounts = accountService.getAccounts();
        assertThat(activeAccounts, hasSize(2));
    }

    @Test
    public void shouldAddObjectIdWhenAddAccessToken() {
        ObjectId id = new ObjectId();
        Account account = new Account();
        AccessToken token = new AccessToken();
        token.setId(id);
        accountService.addAccessToken(token, account);
        List<Account> accounts = getAccounts();
        assertThat(accounts, hasSize(1));
        assertThat(accounts.get(0).getAccessToken().getId(), is(not(equalTo(null))));
        assertThat(accounts.get(0).getAccessToken().getId(), is(equalTo(id)));
    }

}




