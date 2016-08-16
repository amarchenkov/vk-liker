package com.github.vk.api.models;

import com.github.vk.api.enums.Display;
import com.github.vk.api.enums.ResponseType;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created at 16.08.2016 23:13
 *
 * @author Andrey
 */
public class Authorize {

    private String clientId;
    private URL redirectUrl;
    private Display display;
    private String scope;
    private ResponseType responseType;
    private float v;
    private String state;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setRedirectUrl(URL redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public void setV(float v) {
        this.v = v;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("client_id=").append(clientId).append("&");
        if (redirectUrl != null) {
            sb.append("redirect_url=").append(redirectUrl.toString()).append("&");
        }
        if (display != null) {
            sb.append("display=").append(display.getValue()).append("&");
        }
        sb.append("scope=").append(scope).append("&");
        if (responseType != null) {
            sb.append("response_type=").append(responseType.getValue()).append("&");
        }
        sb.append("v=").append(v).append("&");
        sb.append("state=").append(state);
        return sb.toString();
    }
}