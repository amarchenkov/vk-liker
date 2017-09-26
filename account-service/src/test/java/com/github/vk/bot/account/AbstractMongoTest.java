package com.github.vk.bot.account;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created at 26.09.2017 12:13
 *
 * @author AMarchenkov
 */
public abstract class AbstractMongoTest {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Before
    public void init() {
        mongoTemplate.getDb().dropDatabase();
    }

    @After
    public void finish() {
        mongoTemplate.getDb().dropDatabase();
    }
}
