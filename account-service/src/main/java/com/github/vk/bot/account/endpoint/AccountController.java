package com.github.vk.bot.account.endpoint;

import com.github.vk.bot.account.service.AccountService;
import com.github.vk.bot.common.model.account.Account;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created at 25.09.2017 16:21
 *
 * @author AMarchenkov
 */
@RestController
@RequestMapping(value = "/*", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<Account> getAllAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") ObjectId id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addAccount(@RequestBody Account account) {
        ObjectId id = accountService.save(account);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, id.toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeAccountById(@PathVariable("id") ObjectId id) {
        accountService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
