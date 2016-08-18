package com.github.vk.api;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created at 17.08.2016 20:46
 *
 * @author Andrey
 */
public class HttpUtils {

//    remixlang=777;
//    remixflash=22.0.0;
//    remixscreen_depth=24;
//    remixdt=0;
//    remixseenads=2;
//    remixlhk=ed26d62b9c387e8f72;
//    remixstid=1620128121_aa25b83009aa4e990c;
//    remixmdevice=1920/1080/1/!!-!!!!

    public static Header[] prepareAuthorizeHeaders() {
        return new BasicHeader[] {
                new BasicHeader("Accept", "text/html, application/xhtml+xml, image/jxr, */*)"),
                new BasicHeader("Accept-Encoding", "gzip, deflate"),
                new BasicHeader("Accept-Language", "ru-RU"),
                new BasicHeader("Cache-Control", "no-cache"),
                new BasicHeader("Content-Type", "application/x-www-form-urlencoded"),
                new BasicHeader("Host", "login.vk.com"),
                new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586"),
        };
    }

    public static Header getHeaderByName(Header[] headers, String name) {
        for (Header header : headers) {
            if (header.getName().equals(name)) {
                return header;
            }
        }
        return null;
    }
}
