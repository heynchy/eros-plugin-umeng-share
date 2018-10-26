package com.heyn.erosplugin.wx_umeng_share.module;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.weex.plugin.annotation.WeexModule;
import com.heyn.erosplugin.wx_umeng_share.activity.ShareAllActivity;
import com.heyn.erosplugin.wx_umeng_share.util.ShareActionUtil;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

/**
 * Author: heynchy
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
    public void shareParams(String params, final JSCallback success, final JSCallback failure) {
        if (TextUtils.isEmpty(params)){
            if (failure!= null){
                failure.invoke("分享的数据为空，不支持分享！");
            }
            return;
        }
        ShareActionUtil.setSuccess(success);
        ShareActionUtil.setFailure(failure);
        final Activity activity = (Activity) mWXSDKInstance.getContext();
        ShareAllActivity.start(activity, params);
    }
}
