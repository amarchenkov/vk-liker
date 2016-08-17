package com.github.vk.api.models;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created at 17.08.2016 13:15
 *
 * @author AMarchenkov
 */
public class LoginFormModel {
    public URL action;
    private String email;
    private String password;
    private String _origin;
    private String ip_h;
    private String lg_h;
    private String to;

    public void setAction(URL action) {
        this.action = action;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOrigin(String _origin) {
        this._origin = _origin;
    }

    public void setIpH(String ip_h) {
        this.ip_h = ip_h;
    }

    public void setIgH(String lg_h) {
        this.lg_h = lg_h;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public URL getAction() {
        return action;
    }

    @Override
    public String toString() {
        try {
            StringBuilder sb = new StringBuilder("");
            sb.append("email=").append(URLEncoder.encode(email, "UTF-8")).append("&");
            sb.append("pass=").append(URLEncoder.encode(password, "UTF-8")).append("&");
            sb.append("_origin=").append(URLEncoder.encode(_origin, "UTF-8")).append("&");
            sb.append("ip_h=").append(URLEncoder.encode(ip_h, "UTF-8")).append("&");
            sb.append("lg_h=").append(URLEncoder.encode(lg_h, "UTF-8")).append("&");
            sb.append("to=").append(to);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
