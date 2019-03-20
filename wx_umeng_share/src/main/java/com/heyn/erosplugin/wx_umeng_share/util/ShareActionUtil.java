package com.heyn.erosplugin.wx_umeng_share.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.heyn.erosplugin.wx_umeng_share.event.JSShareEvent;
import com.taobao.weex.bridge.JSCallback;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

import static com.heyn.erosplugin.wx_umeng_share.activity.ShareAllActivity.finishSelf;
import static com.heyn.erosplugin.wx_umeng_share.activity.SharePlatformActivity.finishPlatform;
import static com.heyn.erosplugin.wx_umeng_share.util.AppIsAvailableUtil.isQQClientAvailable;

/**
 * Author: heynchy
 * Date:   2018/10/11
 * <p>
 * Introduce: 分享行为的工具类
 */
public class ShareActionUtil {
    private static JSCallback success;
    private static JSCallback failure;

    private static UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            if (success != null) {
                success.invoke("分享成功");
            }
            finishSelf();
            finishPlatform();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable e) {
            if (failure != null) {
                failure.invoke("分享失败： " + e.getMessage());
            }
            finishSelf();
            finishPlatform();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            if (failure != null) {
                failure.invoke("分享取消了");
            }
            finishSelf();
            finishPlatform();
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
        if (TextUtils.isEmpty(event.getContent())) {
            if (failure != null) {
                failure.invoke("文本内容为空，不支持分享！");
            }
            return;
        }
        new ShareAction(activity).withText(event.getContent()).setPlatform(shareMedia)
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
        if (TextUtils.isEmpty(event.getImageUrl())) {
            if (failure != null) {
                failure.invoke("图片链接为空，不支持分享！");
            }
            return;
        }
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
        if (TextUtils.isEmpty(event.getUrl())) {
            if (failure != null) {
                failure.invoke("网页链接为空，不支持分享！");
            }
            return;
        }
        if (event.getImageUrl() != null) {
            web.setThumb(new UMImage(activity, event.getImageUrl()));
        }
        web.setTitle(event.getTitle());
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
        if (TextUtils.isEmpty(event.getImageUrl()) || TextUtils.isEmpty(event.getContent())) {
            if (failure != null) {
                failure.invoke("图文数据不完整，不支持分享！");
            }
            return;
        }
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
        if (TextUtils.isEmpty(event.getUrl())) {
            if (failure != null) {
                failure.invoke("音频链接不存在，不支持分享！");
            }
            return;
        }
        UMusic uMusic = new UMusic(event.getUrl());
        uMusic.setTitle(event.getTitle());
        uMusic.setDescription(event.getContent());
        uMusic.setmTargetUrl(event.getUrl());
        if (!TextUtils.isEmpty(event.getImageUrl())) {
            UMImage thumb = new UMImage(activity, event.getImageUrl());
            uMusic.setThumb(thumb);
        }

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
        if (TextUtils.isEmpty(event.getUrl())) {
            if (failure != null) {
                failure.invoke("视频链接不存在，不支持分享！");
            }
            return;
        }
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
        if (TextUtils.isEmpty(event.getUrl()) || TextUtils.isEmpty(event.getPath())) {
            if (failure != null) {
                failure.invoke("小程序数据不完整，不支持分享！");
            }
            return;
        }
        UMMin umMin = new UMMin(event.getUrl());
        umMin.setTitle(event.getTitle());
        umMin.setDescription(event.getContent());
        umMin.setPath(event.getPath());
        umMin.setUserName(event.getUserName());
        if (!TextUtils.isEmpty(event.getImageUrl())) {
            UMImage umImage = new UMImage(activity, event.getImageUrl());
            umMin.setThumb(umImage);
        }
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
        if (TextUtils.isEmpty(event.getImageUrl())) {
            if (failure != null) {
                failure.invoke("表情链接不存在，不支持分享！");
            }
            return;
        }
        UMEmoji emoji = new UMEmoji(activity, event.getImageUrl());
        emoji.setThumb(new UMImage(activity, event.getImageUrl()));
        new ShareAction(activity)
                .withMedia(emoji)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    /**
     * 因为友盟SDK中不支持QQ的纯文本分享，所以采用主动调用的方式进行纯文本的分享
     *
     * @param context 上下文 *
     * @param content 要分享的文本 *
     */
    public static void shareTextToQQ(Context context, String content) {
        if (isQQClientAvailable(context) && !TextUtils.isEmpty(content)) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
            context.startActivity(intent);
            if (success != null) {
                success.invoke("分享成功");
            }
        } else {
            if (failure != null) {
                failure.invoke("分享失败");
            }
        }
        finishSelf();
        finishPlatform();
    }

    /**
     * 根据分享的类型设置对应的分享方法
     *
     * @param activity
     * @param event
     * @param shareMedia
     */
    public static void shareAction(Activity activity, JSShareEvent event, SHARE_MEDIA shareMedia) {
        if (shareMedia == null){

        }
        switch (event.getShareType().toLowerCase()) {
            case StyleUtil.TEXT:
                // 分享文本
                if (SHARE_MEDIA.QQ == shareMedia) {
                    shareTextToQQ(activity, event.getContent());
                } else {
                    shareText(activity, event, shareMedia);
                }
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

    public static void setFailure(JSCallback fail) {
        failure = fail;
    }

    public static void setSuccess(JSCallback succ) {
        success = succ;
    }

    public static JSCallback getFailure() {
        return failure;
    }

    public static JSCallback getSuccess() {
        return success;
    }
}
