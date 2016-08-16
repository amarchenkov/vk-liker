package com.github.vk.api;

import com.github.vk.api.models.Authorize;

/**
 * Created at 16.08.2016 23:47
 *
 * @author Andrey
 */
public class Test {

    @org.junit.Test
    public void test() {
        VK vk = new VK();
        Authorize authorize = new Authorize();
        authorize.setClientId("123456");
        vk.authorize(authorize);
    }
}
