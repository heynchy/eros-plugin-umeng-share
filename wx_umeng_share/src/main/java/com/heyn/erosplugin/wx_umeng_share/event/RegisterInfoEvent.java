package com.heyn.erosplugin.wx_umeng_share.event;

/**
 * Author: heynchy
 * Date:   2018/10/10
 * <p>
 * Introduce:
 */
public class RegisterInfoEvent {
    private String appKey;
    private String appSecret;
    private String redirectUrl; // 新浪专有

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
