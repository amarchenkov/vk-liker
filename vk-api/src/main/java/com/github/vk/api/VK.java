package com.github.vk.api;

import com.github.vk.api.enums.ObjectType;
import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AccessToken;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.json.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created at 15.08.2016 8:15
 *
 * @author Andrey
 */
public class VK {

    private static final Logger LOG = LogManager.getLogger(VK.class);
    private static final String AUTHORIZE_URL = "https://oauth.vk.com/authorize";
    private static final String API_URL = "https://api.vk.com/method/";

    private AccessToken accessToken;
    private AuthorizeData authorizeData;
    private CloseableHttpClient httpClient;
    private Gson gson = new Gson();

    public VK() {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .build();
        LOG.debug("HTTP client initialized");
    }

    public VK(AccessToken accessToken) {
        this();
        this.accessToken = accessToken;
    }

    public VK(AuthorizeData authorizeData) {
        this();
        this.authorizeData = authorizeData;
    }

    private boolean checkAccessToken() {
        return !(accessToken == null || accessToken.getAccessToken().isEmpty()) && accessToken.getExpiresIn().isBefore(LocalDateTime.now());
    }

    private void extractTokenFromUrl(String url) throws AuthorizeException {
        LOG.debug("Token URL = [{}]", url);
        if (!url.contains("access_token=")) {
            throw new AuthorizeException("Cannot get access token");
        }
        this.accessToken = new AccessToken(url.split("#")[1]);
    }

    /**
     * Get access token for VK api
     *
     * @param login    Phone number or email
     * @param password password
     * @throws AuthorizeException If wrong login or password
     */
    public void updateToken(String login, String password) throws AuthorizeException {
        WebDriver driver = new FirefoxDriver();
        LOG.info("Start authorization. URL = [{}]", AUTHORIZE_URL + "?" + authorizeData.toString());
        LOG.info("Login = [{}] Password = [{}]", login, password);
        driver.get(AUTHORIZE_URL + "?" + authorizeData.toString());
        WebElement email = driver.findElement(By.name("email"));
        WebElement pass = driver.findElement(By.name("pass"));
        email.sendKeys(login);
        pass.sendKeys(password);
        driver.findElement(By.cssSelector("[value=\"Войти\"]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("получение доступа")
                        || d.getTitle().toLowerCase().startsWith("oauth blank");
            }
        });
        if (driver.findElements(By.cssSelector("[value=\"Разрешить\"]")).size() > 0) {
            driver.findElement(By.cssSelector("[value=\"Разрешить\"]")).click();
        }
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("oauth blank");
            }
        });
        extractTokenFromUrl(driver.getCurrentUrl());
        driver.quit();
    }

    /**
     * Add like on object
     *
     * @param type    Object type
     * @param ownerId Object owner
     * @param itemId  Item ID
     * @return Likes count
     */
    public Optional<LikesAddResponse> like(ObjectType type, long ownerId, long itemId) {
        StringBuilder sb = new StringBuilder(API_URL).append("likes.add?")
                .append("access_token=").append(accessToken).append("&")
                .append("type=").append(type.getValue()).append("&")
                .append("owner_id=").append(ownerId).append("&")
                .append("v=").append(authorizeData.getV()).append("&")
                .append("item_id=").append(itemId);
        HttpGet get = new HttpGet(sb.toString());
        try {
            HttpResponse response = httpClient.execute(get);
            Type responseType = new TypeToken<Response<LikesAddResponse>>() {
            }.getType();
            Response<LikesAddResponse> responseJson = gson.fromJson(EntityUtils.toString(response.getEntity()), responseType);
            return Optional.of(responseJson.getResponse());
        } catch (IOException e) {
            LOG.error("Cannot send request [likes.add]", e);
        }
        return Optional.empty();
    }

    /**
     * Check if object is liked by current user
     *
     * @param type    Object type
     * @param ownerId Object owner
     * @param itemId  Item ID
     * @return resource liked flag
     */
    public boolean isLiked(ObjectType type, long ownerId, long itemId) {
        StringBuilder sb = new StringBuilder(API_URL).append("likes.isLiked?")
                .append("access_token=").append(accessToken).append("&")
                .append("type=").append(type.getValue()).append("&")
                .append("owner_id=").append(ownerId).append("&")
                .append("v=").append(authorizeData.getV()).append("&")
                .append("item_id=").append(itemId);
        HttpGet get = new HttpGet(sb.toString());
        try {
            HttpResponse response = httpClient.execute(get);
            JsonElement json = gson.fromJson(EntityUtils.toString(response.getEntity()), JsonElement.class);
            return json.getAsJsonObject().get("response").getAsJsonObject().get("liked").getAsInt() > 0;
        } catch (IOException e) {
            LOG.error("Cannot send request [likes.isLiked]", e);
        }
        return true;
    }

    /**
     * Get count of items from wall of owner start with offset
     *
     * @param ownerId Owner of wall
     * @param offset  start index of items
     * @param count   Quantity of items on the wall
     * @return Items from the wall
     */
    public Optional<WallGetResponse> getWallPosts(long ownerId, int offset, int count) {
        StringBuilder sb = new StringBuilder(API_URL).append("wall.get?")
                .append("access_token=").append(accessToken).append("&")
                .append("owner_id=").append(ownerId).append("&")
                .append("offset=").append(offset).append("&")
                .append("v=").append(authorizeData.getV()).append("&")
                .append("count=").append(count);
        HttpGet get = new HttpGet(sb.toString());
        try {
            HttpResponse response = httpClient.execute(get);
            Type responseType = new TypeToken<Response<WallGetResponse>>() {
            }.getType();
            Response<WallGetResponse> responseJson = gson.fromJson(EntityUtils.toString(response.getEntity()), responseType);
            return Optional.of(responseJson.getResponse());
        } catch (IOException e) {
            LOG.error("Cannot send request [wall.get]", e);
        }
        return Optional.empty();
    }

    /**
     * Get count of photo of owner start with offset
     *
     * @param ownerId Photo owner
     * @param offset  start index of photo
     * @param count   photo count
     * @return Photos of owner
     */
    public Optional<PhotosGetAllResponse> getUserPhotos(long ownerId, int offset, int count) {
        StringBuilder sb = new StringBuilder(API_URL).append("photos.getAll?")
                .append("access_token=").append(accessToken).append("&")
                .append("owner_id=").append(ownerId).append("&")
                .append("offset=").append(offset).append("&")
                .append("v=").append(authorizeData.getV()).append("&")
                .append("count=").append(count);
        HttpGet get = new HttpGet(sb.toString());
        try {
            HttpResponse response = httpClient.execute(get);
            Type responseType = new TypeToken<Response<PhotosGetAllResponse>>() {
            }.getType();
            Response<PhotosGetAllResponse> responseJson = gson.fromJson(EntityUtils.toString(response.getEntity()), responseType);
            return Optional.of(responseJson.getResponse());
        } catch (IOException e) {
            LOG.error("Cannot send request [photos.getAll]", e);
        }
        return Optional.empty();
    }

    /**
     * Get list of group's members
     *
     * @param groupId ID of group
     * @return list of members
     */
    public Optional<GroupMembersResponse> getGroupMembers(long groupId) {
        StringBuilder sb = new StringBuilder(API_URL).append("groups.getMembers?").append("group_id=").append(groupId);
        if (accessToken != null) {
            sb.append("access_token=").append(accessToken).append("&");
        }
        HttpGet get = new HttpGet(sb.toString());
        try {
            HttpResponse response = httpClient.execute(get);
            Type responseType = new TypeToken<Response<GroupMembersResponse>>() {
            }.getType();
            Response<GroupMembersResponse> responseJson = gson.fromJson(EntityUtils.toString(response.getEntity()), responseType);
            return Optional.of(responseJson.getResponse());
        } catch (IOException e) {
            LOG.error("Cannot send request [groups.getMembers]", e);
        }
        return Optional.empty();
    }

}