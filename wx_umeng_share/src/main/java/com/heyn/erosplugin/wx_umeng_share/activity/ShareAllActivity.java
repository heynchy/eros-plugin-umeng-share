package com.heyn.erosplugin.wx_umeng_share.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.heyn.erosplugin.wx_umeng_share.event.JSShareEvent;
import com.heyn.erosplugin.wx_umeng_share.util.PermissionUtil;
import com.heyn.erosplugin.wx_umeng_share.util.ShareActionUtil;
import com.heyn.erosplugin.wx_umeng_share.util.StyleUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_PARAMS;

/**
 * Author: 崔海营
 * Date:   2018/9/19
 * <p>
 * Introduce: 相关权限行为处理的Activity
 */
public class ShareAllActivity extends Activity implements IWXRenderListener {
    private String mParamas;      // 传递的参数


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (PermissionUtil.initPermission(this)) {
            final JSShareEvent event = new Gson().fromJson(mParamas, JSShareEvent.class);
            new ShareAction(this)
                    .setDisplayList(StyleUtil.initPlatform(this, event.getShareType()))
                    .setShareboardclickCallback(new ShareBoardlistener() {
                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            ShareActionUtil.shareAction(ShareAllActivity.this,
                                    event, share_media);
                        }
                    }).open();
        }

    }


    /**
     * 初始化基本配置
     */
    private void init() {
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        params.alpha = 0.0f;
        window.setAttributes(params);
        mParamas = getIntent().getStringExtra(SHARE_PARAMS);
    }


    /**
     * 主动获取权限后的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final JSShareEvent event = new Gson().fromJson(mParamas, JSShareEvent.class);
        new ShareAction(this)
                .setDisplayList(StyleUtil.initPlatform(this, event.getShareType()))
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        ShareActionUtil.shareAction(ShareAllActivity.this,
                                event, share_media);
                    }
                }).open();
        finish();
    }

    /**
     * 跳转至该页面的静态方法
     *
     * @param context
     */
    public static void start(Context context, String params) {
        Intent intent = new Intent(context, ShareAllActivity.class);
        intent.putExtra(SHARE_PARAMS, params);
        context.startActivity(intent);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {

    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }
}
