package com.github.vk.api;

import com.github.vk.api.exceptions.AuthorizeException;
import com.github.vk.api.models.AuthorizeData;
import com.github.vk.api.models.LoginFormModel;
import org.apache.http.*;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        this.httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setRedirectStrategy(new DefaultRedirectStrategy()).build();
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
            HttpResponse response1 = httpClient.execute(post);
            Header locationHeader = HttpUtils.getHeaderByName(response1.getAllHeaders(), "Location");
            System.out.println(locationHeader);
            HttpGet get1 = new HttpGet(locationHeader.getValue());
            get1.addHeader("Referer", "https://oauth.vk.com/authorize?client_id=5591327&redirect_url=https%3A%2F%2Foauth.vk.com%2Fblank.html&display=mobile&scope=0&response_type=token&state=null");
            get1.addHeader("Connection", "keep-alive");
            get1.addHeader("Accept-Encoding", "gzip, deflate, br");
            get1.addHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
            get1.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            get1.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
//            get1.addHeader("Host", "oauth.vk.com");
            get1.addHeader("Cookie", "remixlang=0; remixlhk=ab00b429c5a56d328d; remixstid=1971100988_e0c58fad8d0bf3f625; remixmdevice=1280/1024/1/!!-!!!!; remixq_a967dcdda523bfab2824a75d3ca4b65c=cde4c7ee0093a7f0e6");
            HttpResponse response2  = httpClient.execute(get1);
            Header locationHeader1 = HttpUtils.getHeaderByName(response2.getAllHeaders(), "Location");
            System.out.println(locationHeader1);



//            String location = "";
//            while (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
//                Header locationHeader = HttpUtils.getHeaderByName(response.getAllHeaders(), "Location");
//                if (locationHeader == null) {
//                    break;
//                }
//                LOG.debug("Redirect to " + HttpUtils.getHeaderByName(response.getAllHeaders(), "Location"));
//                response = httpClient.execute(new HttpGet(locationHeader.getValue()));
//
//                location = locationHeader.getValue();
//                System.out.println(response.getStatusLine().getStatusCode());
//            }
//            System.out.println(EntityUtils.toString(response.getEntity()));
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


}