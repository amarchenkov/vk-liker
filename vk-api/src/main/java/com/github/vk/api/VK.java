package com.github.vk.api;

import com.github.vk.api.models.Authorize;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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

    private String accessToken;
    private long expireAccessToken;
    private CloseableHttpClient httpClient;

    public VK() {
        this.httpClient = HttpClients.createDefault();
        LOG.debug("HTTP client initialized");
    }

    /**
     * Check valid access token. Return false if token is empty or token has expires
     *
     * @return is access token valid
     */
    public boolean checkAccessToken() {
        return !(accessToken == null || accessToken.isEmpty()) && expireAccessToken >= System.currentTimeMillis();
    }

    /**
     *
     * @param url
     * @return
     */
    private String extractTokenFromUrl(String url) {
        return null;
    }

    public void authorize(Authorize authorize) {
        HttpGet get = new HttpGet(AUTHORIZE_URL + "?" + authorize.toString());
        try {
            HttpResponse response = httpClient.execute(get);
            System.out.printf(IOUtils.toString(response.getEntity().getContent()));
        } catch (IOException e) {
        }
    }

}