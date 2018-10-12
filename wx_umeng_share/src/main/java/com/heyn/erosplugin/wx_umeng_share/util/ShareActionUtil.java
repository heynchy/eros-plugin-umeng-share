package com.heyn.erosplugin.wx_umeng_share.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.heyn.erosplugin.wx_umeng_share.event.JSShareEvent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

/**
 * Author: 崔海营
 * Date:   2018/10/11
 * <p>
 * Introduce: 分享行为的工具类
 */
public class ShareActionUtil {

    private static UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i("heyn", "onStart");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.i("heyn", "onResult");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.i("heyn", "onError");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.i("heyn", "onCancel");
        }
    };

    /**
     * 分享纯文本
     *
     * @param activity   当前的activity对象
     * @param event      分享内容的实体
     * @param shareMedia 分享的平台
     */
    public static void shareText(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        new ShareAction(activity)
                .withText(event.getContent())
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 分享网络图片
     *
     * @param activity   当前的activity对象
     * @param event      分享内容的实体
     * @param shareMedia 分享的平台
     */
    public static void shareImageUrl(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        UMImage imageurl = new UMImage(activity, event.getImageUrl());
        imageurl.setThumb(new UMImage(activity, event.getImageUrl()));
        new ShareAction(activity)
                .withMedia(imageurl)
                .setPlatform(shareMedia)
                .setCallback(shareListener)
                .share();
    }

    /**
     * 分享网页
     *
     * @param activity   当前activity的对象
     * @param event      分享内容的实体
     * @param shareMedia 分享的平台
     */
    public static void shareWebPage(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        UMWeb web = new UMWeb(event.getUrl());
        web.setTitle(event.getTitle());
        web.setThumb(new UMImage(activity, event.getImageUrl()));
        web.setDescription(event.getContent());
        new ShareAction(activity).withMedia(web)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 分享图文
     *
     * @param activity   当前activity的对象
     * @param event      分享的内容实体
     * @param shareMedia 分享的平台
     */
    public static void shareTextImage(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        UMImage imageUrl = new UMImage(activity, event.getImageUrl());
        imageUrl.setThumb(new UMImage(activity, event.getImageUrl()));
        new ShareAction(activity)
                .withText(event.getContent())
                .withMedia(imageUrl)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 分享音乐链接
     *
     * @param activity   当前的activity对象
     * @param event      分享的内容实体
     * @param shareMedia 分享的平台
     */
    public static void shareMusic(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        UMusic uMusic = new UMusic(event.getUrl());
        uMusic.setTitle(event.getTitle());
        uMusic.setDescription(event.getContent());
        uMusic.setmTargetUrl(event.getUrl());
        UMImage thumb = new UMImage(activity, event.getImageUrl());
        uMusic.setThumb(thumb);
        new ShareAction(activity)
                .withMedia(uMusic)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 分享视频
     *
     * @param activity   当前activity的对象
     * @param event      分享的内容实体
     * @param shareMedia 分享的平台
     */
    public static void shareVideo(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        UMVideo video = new UMVideo(event.getUrl());
        video.setThumb(new UMImage(activity, event.getImageUrl()));
        video.setTitle(event.getTitle());
        video.setDescription(event.getContent());
        new ShareAction(activity).withMedia(video)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 分享小程序
     *
     * @param activity   当前的activity对象
     * @param event      分享的内容实体
     * @param shareMedia 分享的平台
     */
    public static void shareMiniProgram(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        UMMin umMin = new UMMin(event.getUrl());
        umMin.setTitle(event.getTitle());
        umMin.setDescription(event.getContent());
        umMin.setPath(event.getPath());
        umMin.setUserName(event.getUserName());
        UMImage umImage;
        if (TextUtils.isEmpty(event.getImageUrl())) {
            umImage = new UMImage(activity, event.getImageUrl());
        } else {
            umImage = new UMImage(activity, event.getImageUrl());
        }
        umMin.setThumb(umImage);
        new ShareAction(activity)
                .withMedia(umMin)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 分享表情
     *
     * @param activity   当前的activity对象
     * @param event      分享的内容实体
     * @param shareMedia 分享的平台
     */
    public static void shareEmoji(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        UMEmoji emoji = new UMEmoji(activity, event.getImageUrl());
        emoji.setThumb(new UMImage(activity, event.getImageUrl()));
        new ShareAction(activity)
                .withMedia(emoji)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 根据分享的类型设置对应的分享方法
     * @param activity
     * @param event
     * @param shareMedia
     */
    public static void shareAction(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        switch (event.getShareType().toLowerCase()) {
            case StyleUtil.TEXT:
                // 分享文本
                shareText(activity, event, shareMedia);
                break;
            case StyleUtil.IMAGE:
                // 分享图片
                shareImageUrl(activity, event, shareMedia);
                break;
            case StyleUtil.TEXTIMAGE:
                // 分享图文
                shareTextImage(activity, event, shareMedia);
                break;
            case StyleUtil.WEBPAGE:
                // 分享网页
                shareWebPage(activity, event, shareMedia);
                break;
            case StyleUtil.MUSIC:
                // 分享音乐
                shareMusic(activity, event, shareMedia);
                break;
            case StyleUtil.VIDEO:
                // 分享视频
                shareVideo(activity, event, shareMedia);
                break;
            case StyleUtil.MINAPP:
                // 分享小程序
                shareMiniProgram(activity, event, shareMedia);
                break;
            case StyleUtil.EMOJI:
                // 分享表情
                shareEmoji(activity, event, shareMedia);
                break;
        }
    }
}
