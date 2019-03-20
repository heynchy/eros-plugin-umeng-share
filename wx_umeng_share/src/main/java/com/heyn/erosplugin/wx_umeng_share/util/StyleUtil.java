package com.heyn.erosplugin.wx_umeng_share.util;

import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_MEDIA_DINGTALK;
import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_MEDIA_QQ;
import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_MEDIA_QZONE;
import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_MEDIA_SINA;
import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_MEDIA_WEIXIN;
import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_MEDIA_WEIXIN_CIRCLE;
import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_MEDIA_WEIXIN_FAVORITE;

/**
 *
 */

public class StyleUtil {

    public static boolean isWeixin = true;
    public static boolean isWeixinCircle = true;
    public static boolean isWeixinFavorite = true;
    public static boolean isSina = true;
    public static boolean isQQ = true;
    public static boolean isQZONE = true;
    public static boolean isDingTalk = true;


    public static final String TEXT = "text";             // 纯文本
    public static final String IMAGE = "image";           // 图片
    public static final String TEXTIMAGE = "textimage";   // 图文
    public static final String MULIMAGE = "muliimage";    // 多图分享
    public static final String MUSIC = "music";           // 音乐链接
    public static final String VIDEO = "video";           // 视频链接
    public static final String WEBPAGE = "webpage";       // 网页链接
    public static final String EMOJI = "emoji";           // 表情
    public static final String MINAPP = "minapp";         // 小程序

    public static SHARE_MEDIA[] initPlatform(Context context, String shareStyle) {
        List<SHARE_MEDIA> platforms = new ArrayList<>();
        switch (shareStyle.toLowerCase()) {
            case TEXT:          // 分享的类型为纯文本
            case MUSIC:         // 分享的类型为音乐链接
            case VIDEO:         // 分享的类型为视频链接
            case WEBPAGE:       // 分享的类型为网页链接
                if (isWeixin && AppIsAvailableUtil.isWeixinAvailable(context)) {
                    platforms.add(SHARE_MEDIA.WEIXIN);
                    if (isWeixinCircle){
                        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE);
                    }
                   if (isWeixinFavorite){
                       platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE);
                   }
                }
                if (isSina && AppIsAvailableUtil.isSinaAvailable(context)) {
                    platforms.add(SHARE_MEDIA.SINA);
                }

                if (isQQ && AppIsAvailableUtil.isQQClientAvailable(context)) {
                    platforms.add(SHARE_MEDIA.QQ);
                    if (isQZONE) {
                        platforms.add(SHARE_MEDIA.QZONE);
                    }
                }

                if (isDingTalk && AppIsAvailableUtil.isDingTalkAvailable(context)) {
                    platforms.add(SHARE_MEDIA.DINGTALK);
                }

                break;
            case IMAGE:         // 分享的类型为图片
            case EMOJI:         // 分享的类型为表情
                if (isWeixin && AppIsAvailableUtil.isWeixinAvailable(context)) {
                    platforms.add(SHARE_MEDIA.WEIXIN);
                    if(isWeixinCircle){
                        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE);
                    }
                    if (isWeixinFavorite){
                        platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE);
                    }
                }
                if (isSina && AppIsAvailableUtil.isSinaAvailable(context)) {
                    platforms.add(SHARE_MEDIA.SINA);
                }
                if (isQQ && AppIsAvailableUtil.isQQClientAvailable(context)) {
                    platforms.add(SHARE_MEDIA.QQ);
                    if (isQZONE){
                        platforms.add(SHARE_MEDIA.QZONE);
                    }
                }
                if (isDingTalk && AppIsAvailableUtil.isDingTalkAvailable(context)) {
                    platforms.add(SHARE_MEDIA.DINGTALK);
                }
                break;
            case TEXTIMAGE:     // 分享的类型为图文
                if (isSina && AppIsAvailableUtil.isSinaAvailable(context)) {
                    platforms.add(SHARE_MEDIA.SINA);
                }
                break;
            case MULIMAGE:      // 分享的类型为多图分享
                if (isSina && AppIsAvailableUtil.isSinaAvailable(context)) {
                    platforms.add(SHARE_MEDIA.SINA);
                }
                if (isQQ && AppIsAvailableUtil.isQQClientAvailable(context)) {
                    if (isQZONE){
                        platforms.add(SHARE_MEDIA.QZONE);
                    }
                }
                break;
            case MINAPP:        // 分享的类型为小程序
                if (isWeixin && AppIsAvailableUtil.isWeixinAvailable(context)) {
                    platforms.add(SHARE_MEDIA.WEIXIN);
                }
                break;

        }
        SHARE_MEDIA[] shareMedia = new SHARE_MEDIA[platforms.size()];
        for (int i = 0; i < platforms.size(); i++) {
            shareMedia[i] = platforms.get(i);
        }
        return shareMedia;
    }

    /**
     * 单一分享时选择对应的分享平台
     *
     * @param media
     * @return
     */
    public static SHARE_MEDIA getShareMedia(String media) {
        switch (media.toLowerCase()) {
            case SHARE_MEDIA_WEIXIN:  // 微信分享
                return SHARE_MEDIA.WEIXIN;
            case SHARE_MEDIA_WEIXIN_CIRCLE:  // 微信朋友圈分享
                return SHARE_MEDIA.WEIXIN_CIRCLE;
            case SHARE_MEDIA_WEIXIN_FAVORITE:  // 微信收藏分享
                return SHARE_MEDIA.WEIXIN_FAVORITE;
            case SHARE_MEDIA_QQ:  // QQ 分享
                return SHARE_MEDIA.QQ;
            case SHARE_MEDIA_QZONE:  // QQ空间分享
                return SHARE_MEDIA.QZONE;
            case SHARE_MEDIA_SINA:  // 新浪微博分享
                return SHARE_MEDIA.SINA;
            case SHARE_MEDIA_DINGTALK:  // 钉钉分享
                return SHARE_MEDIA.DINGTALK;
        }
        return null;
    }
}