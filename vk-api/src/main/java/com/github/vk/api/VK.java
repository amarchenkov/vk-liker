package com.github.vk.api;

import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.LoginFormModel;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;

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
    private AuthorizeData authorizeData;

    public VK(AuthorizeData authorizeData) {
        this.httpClient = HttpClients.createDefault();
        LOG.debug("HTTP client initialized");
        this.authorizeData = authorizeData;
    }

    /**
     * Check valid access token. Return false if token is empty or token has expires
     *
     * @return is access token valid
     */
    public boolean checkAccessToken() {
        return !(accessToken == null || accessToken.isEmpty()) && expireAccessToken >= System.currentTimeMillis();
    }

    private String extractTokenFromUrl(String url) {
        return null;
    }

    public void authorize(String login, String password) {
        LOG.debug("Open auth dialog. URL = " + AUTHORIZE_URL + "?" + authorizeData.toString());
        HttpGet get = new HttpGet(AUTHORIZE_URL + "?" + authorizeData.toString());
        try {
            HttpResponse response = httpClient.execute(get);
            String responseBody = EntityUtils.toString(response.getEntity());
            Document responseDocument = Jsoup.parse(responseBody);
            Elements forms = responseDocument.select("form");
            if (forms.size() != 1) {
                throw new AuthorizeException("Cannot find form for submitting login and password");
            }
            LoginFormModel loginForm = prepareFormData(forms.get(0), login, password);

            HttpPost post = new HttpPost(String.valueOf(loginForm.getAction()));
            post.setHeaders(HttpUtils.prepareAuthorizeHeaders());
            post.addHeader("Referer", AUTHORIZE_URL + "?" + authorizeData.toString());
            HttpEntity entity = new ByteArrayEntity(loginForm.toString().getBytes());
            post.setEntity(entity);
            LOG.debug(post.toString());
            response = httpClient.execute(post);

            String location = "";
            while (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
                Header locationHeader = getHeaderByName(response.getAllHeaders(), "Location");
                if (locationHeader == null) {
                    break;
                }
                LOG.debug("Redirect to " + getHeaderByName(response.getAllHeaders(), "Location"));
                response = httpClient.execute(new HttpGet(locationHeader.getValue()));
                location = locationHeader.getValue();
            }
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LoginFormModel prepareFormData(Element form, String login, String password) throws MalformedURLException {
        String actionUrl = form.attr("action");
        LoginFormModel loginForm = new LoginFormModel();
        loginForm.setAction(new URL(actionUrl));
        loginForm.setIgH(form.select("input[name=\"lg_h\"]").get(0).val());
        loginForm.setIpH(form.select("input[name=\"ip_h\"]").get(0).val());
        loginForm.setOrigin(form.select("input[name=\"_origin\"]").get(0).val());
        loginForm.setTo(form.select("input[name=\"to\"]").get(0).val());
        loginForm.setEmail(login);
        loginForm.setPassword(password);
        return loginForm;
    }

    private Header getHeaderByName(Header[] headers, String name) {
        for (Header header : headers) {
            if (header.getName().equals(name)) {
                return header;
            }
        }
        return null;
    }

}