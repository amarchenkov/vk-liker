package com.github.vk.api;

import com.github.vk.api.enums.Display;
import com.github.vk.api.enums.ResponseType;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AuthorizeData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created at 16.08.2016 23:47
 *
 * @author Andrey
 */
public class Test {

    @org.junit.Test
    public void test() throws Exception {
        AuthorizeData authorizeData = new AuthorizeData();
        authorizeData.setClientId("5591327");
        authorizeData.setDisplay(Display.MOBILE);
        authorizeData.setRedirectUrl(new URL(VK.BLANK_URL));
        authorizeData.setResponseType(ResponseType.TOKEN);
        authorizeData.setScope("0");
        VK vk = new VK(authorizeData);
        vk.authorize("andrej.marchenkov@gmail.com", "!zsifhgv^FGysehgf7v6");
        System.out.println("");
    }
}
