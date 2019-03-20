package com.heyn.erosplugin.wx_umeng_share.event;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;

/**
 * Author: heynchy
 * Date:   2018/10/9
 * <p>
 * Introduce:
 */
public class JSShareEvent implements Serializable {

    private String title;           // 分享的标题
    private String content;         // 分享的文字内容
    private String url;             // 分享对应的URL地址，如h5、音乐链接、视频链接、小程序的链接
    private String imageUrl;        // 分享的图片的Url
    private String path;            // 分享小程序用到的页面路径
    private String userName;        // 分享小程序的名称
    private String shareType;       // 分享的资源类型
    private String [] platforms;    // 分享的平台类型(分享面板)
    private String shareMedia;      // 分享至某一平台


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public String getShareMedia() {
        return shareMedia;
    }

    public void setShareMedia(String shareMedia) {
        this.shareMedia = shareMedia;
    }
}
