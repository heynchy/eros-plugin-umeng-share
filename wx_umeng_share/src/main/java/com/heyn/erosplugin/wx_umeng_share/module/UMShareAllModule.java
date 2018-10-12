package com.heyn.erosplugin.wx_umeng_share.module;

import android.app.Activity;

import com.alibaba.weex.plugin.annotation.WeexModule;
import com.google.gson.Gson;
import com.heyn.erosplugin.wx_umeng_share.activity.ShareAllActivity;
import com.heyn.erosplugin.wx_umeng_share.event.JSShareEvent;
import com.heyn.erosplugin.wx_umeng_share.util.PermissionUtil;
import com.heyn.erosplugin.wx_umeng_share.util.ShareActionUtil;
import com.heyn.erosplugin.wx_umeng_share.util.StyleUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * Author: 崔海营
 * Date:   2018/10/12
 * <p>
 * Introduce:
 */
@WeexModule(name = "UMShareManager", lazyLoad = true)
public class UMShareAllModule extends WXModule {

    /**
     * 分享面板（默认有 微信， 朋友圈， 微信收藏， 新浪微博， QQ, QQ空间，钉钉）
     *
     * @param params  相关参数
     * @param success 成功的回调
     * @param failure 失败的回调
     */
    @JSMethod(uiThread = true)
    public void shareParams(String params, JSCallback success, JSCallback failure) {
        ShareActionUtil.setSuccess(success);
        ShareActionUtil.setFailure(failure);
        Activity activity = (Activity) mWXSDKInstance.getContext();
        ShareAllActivity.start(activity, params);

    }

}
