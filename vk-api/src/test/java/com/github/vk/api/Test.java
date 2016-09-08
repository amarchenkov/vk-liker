package com.github.vk.api;

import com.github.vk.api.enums.Display;
import com.github.vk.api.enums.ObjectType;
import com.github.vk.api.enums.ResponseType;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.json.LikesAddResponse;
import com.github.vk.api.models.json.PhotosGetAllResponse;
import com.github.vk.api.models.json.WallGetResponse;
import org.junit.Ignore;

import java.net.URL;

/**
 * Created at 16.08.2016 23:47
 *
 * @author Andrey
 */
public class Test {

    @Ignore
    @org.junit.Test
    public void test() throws Exception {
        System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
        AuthorizeData authorizeData = new AuthorizeData();
        authorizeData.setClientId("5591327");
        authorizeData.setDisplay(Display.MOBILE);
        authorizeData.setResponseType(ResponseType.TOKEN);
        authorizeData.setScope("wall,photos");
        VK vk = new VK(authorizeData);
        vk.updateToken("andrej.marchenkov@gmail.com", "!zsifhgv^FGysehgf7v6");
        System.out.println(vk.isLiked(ObjectType.POST, 36128013, 556));
        LikesAddResponse a = vk.like(ObjectType.POST, 36128013, 556).get();
        WallGetResponse b = vk.getWallPosts(36128013, 0, 5).get();
        PhotosGetAllResponse c = vk.getUserPhotos(36128013, 0, 5).get();
        System.out.println(vk.isLiked(ObjectType.POST, 36128013, 556));
    }

}
