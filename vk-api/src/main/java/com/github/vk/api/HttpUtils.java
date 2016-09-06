package com.github.vk.api;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created at 17.08.2016 20:46
 *
 * @author Andrey
 */
public class HttpUtils {

    public static final String HOST = "oauth.vk.com";
    public static final String HOST_LOGIN = "login.vk.com";
    public static final String CONNECTION = "Keep-Alive";
    public static final String ACCEPT_ENCODING = "gzip, deflate";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586";
    public static final String ACCEPT_LANGUAGE = "ru-RU";
    public static final String ACCEPT = "text/html, application/xhtml+xml, image/jxr, */*";
    public static final String CACHE_CONTROL = "no-cache";
    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private HttpUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Заголовки для запроса на открытие диалога авторизации
     *
     * @return массив заголовков
     */
    public static Header[] openAuthorizeHeaders() {
        return new BasicHeader[]{
                new BasicHeader("Accept", ACCEPT),
                new BasicHeader("Accept-Encoding", ACCEPT_ENCODING),
                new BasicHeader("Accept-Language", ACCEPT_LANGUAGE),
                new BasicHeader("Host", HOST),
                new BasicHeader("User-Agent", USER_AGENT),
                new BasicHeader("Connection", CONNECTION),
        };
    }

    /**
     * Headers for auth
     *
     * @return headers
     */
    public static Header[] prepareAuthorizeHeaders() {
        return new BasicHeader[]{
                new BasicHeader("Accept", ACCEPT),
                new BasicHeader("Accept-Encoding", ACCEPT_ENCODING),
                new BasicHeader("Accept-Language", ACCEPT_LANGUAGE),
                new BasicHeader("Cache-Control", CACHE_CONTROL),
                new BasicHeader("Content-Type", FORM_CONTENT_TYPE),
                new BasicHeader("Host", HOST_LOGIN),
                new BasicHeader("Connection", CONNECTION),
                new BasicHeader("User-Agent", USER_AGENT),
        };
    }

    /**
     * Return value of specified header
     *
     * @param headers all headers
     * @param name    target header name
     * @return target header value
     */
    public static Header getHeaderByName(Header[] headers, String name) {
        for (Header header : headers) {
            if (header.getName().equals(name)) {
                return header;
            }
        }
        return null;
    }
}
