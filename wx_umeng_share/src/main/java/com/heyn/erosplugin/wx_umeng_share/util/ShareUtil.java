package com.heyn.erosplugin.wx_umeng_share.util;

import android.content.Context;

import com.google.gson.Gson;
import com.heyn.erosplugin.wx_umeng_share.BuildConfig;
import com.heyn.erosplugin.wx_umeng_share.event.RegisterInfoEvent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Author: heynchy
 * Date:   2018/10/10
 * <p>
 * Introduce: 分享的工具类
 */
public class ShareUtil {

    /**
     * 初始化友盟的账号
     *
     * @param context 上下文对象
     * @param params  包含友盟的AppKey和AppSecret的Json字符串
     */
    public static void initUmeng(Context context, String params) {
        if (BuildConfig.DEBUG) {
            //设置LOG开关，默认为false
            UMConfigure.setLogEnabled(true);
        }
        RegisterInfoEvent event = new Gson().fromJson(params, RegisterInfoEvent.class);
        /*
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(context, event.getAppKey(), "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                event.getAppSecret());
    }

    /**
     * 友盟分享---注册微信
     *
     * @param params 包含微信的AppKey和AppSecret的Json字符串
     */
    public static void initWeixin(String params) {
        RegisterInfoEvent event = new Gson().fromJson(params, RegisterInfoEvent.class);
        PlatformConfig.setWeixin(event.getAppKey(), event.getAppSecret());
    }

    /**
     * 友盟分享---注册QQ
     *
     * @param params 包含QQ的AppKey和AppSecret的Json字符串
     */
    public static void initQQ(String params) {
        RegisterInfoEvent event = new Gson().fromJson(params, RegisterInfoEvent.class);
        PlatformConfig.setQQZone(event.getAppKey(), event.getAppSecret());
    }

    /**
     * 友盟分享---注册新浪
     *
     * @param params 包含新浪的AppKey,AppSecret和redirectUrl的Json字符串
     */
    public static void initSina(String params) {
        RegisterInfoEvent event = new Gson().fromJson(params, RegisterInfoEvent.class);
        PlatformConfig.setSinaWeibo(event.getAppKey(), event.getAppSecret(), event.getRedirectUrl());
    }

    /**
     * 友盟分享---注册钉钉
     *
     * @param params 包含钉钉的AppKey的Json字符串
     */
    public static void initDing(String params) {
        RegisterInfoEvent event = new Gson().fromJson(params, RegisterInfoEvent.class);
        PlatformConfig.setDing(event.getAppKey());
    }
}
