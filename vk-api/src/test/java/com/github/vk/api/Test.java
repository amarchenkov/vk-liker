package com.github.vk.api;

import com.github.vk.api.enums.Display;
import com.github.vk.api.enums.ObjectType;
import com.github.vk.api.enums.ResponseType;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.json.LikesAddResponse;
import com.github.vk.api.models.json.PhotosGetAllResponse;
import com.github.vk.api.models.json.WallGetResponse;
import org.junit.Ignore;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

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


    @org.junit.Test
    public void test1() throws InterruptedException {
        String PROXY = "94.177.170.238:" + 8080;
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(PROXY);
        proxy.setSslProxy(PROXY);

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(CapabilityType.PROXY, proxy);
        capabilities.setPlatform(Platform.LINUX);
        capabilities.setBrowserName("chrome");

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("general.useragent.override", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);

        WebDriver driver = new FirefoxDriver(capabilities);
        driver.get("http://2ip.ru");
        Thread.sleep(50000);
    }
}
