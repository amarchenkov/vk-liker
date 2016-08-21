package com.github.vk.api;

import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AccessToken;
import com.github.vk.api.models.AuthorizeData;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created at 15.08.2016 8:15
 *
 * @author Andrey
 */
public class VK {

    private static final Logger LOG = LogManager.getLogger(VK.class);
    private static final String AUTHORIZE_URL = "https://oauth.vk.com/authorize";
    public static final String BLANK_URL = "https://oauth.vk.com/blank.html";
    public static final String API_URL = "https://api.vk.com/method/";

    private AccessToken accessToken;
    private AuthorizeData authorizeData;

    public VK(AuthorizeData authorizeData) {
        this.authorizeData = authorizeData;
        System.setProperty("webdriver.gecko.driver", "E:\\geckodriver.exe");
    }

    /**
     * Check valid access token. Return false if token is empty or token has expires
     *
     * @return is access token valid
     */
    public boolean checkAccessToken() {
        return !(accessToken == null || accessToken.getAccessToken().isEmpty()) && accessToken.getExpiresIn() >= System.currentTimeMillis();
    }

    private void extractTokenFromUrl(String url) throws AuthorizeException {
        if (!url.contains("access_token=")) {
            throw new AuthorizeException("Cannot get access token");
        }
        this.accessToken = new AccessToken(url.split("#")[1]);
    }

    public void authorize(String login, String password) throws AuthorizeException {
        WebDriver driver = new FirefoxDriver();
        driver.get(AUTHORIZE_URL + "?" + authorizeData.toString());
        WebElement email = driver.findElement(By.name("email"));
        WebElement pass = driver.findElement(By.name("pass"));
        email.sendKeys(login);
        pass.sendKeys(password);
        driver.findElement(By.cssSelector("[value=\"Войти\"]")).click();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("oauth blank");
            }
        });
        extractTokenFromUrl(driver.getCurrentUrl());
        driver.quit();
    }

}