package com.heyn.erosplugin.wx_umeng_share.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.heyn.erosplugin.wx_umeng_share.R;
import com.heyn.erosplugin.wx_umeng_share.event.JSShareEvent;
import com.heyn.erosplugin.wx_umeng_share.util.PermissionUtil;
import com.heyn.erosplugin.wx_umeng_share.util.ShareActionUtil;
import com.heyn.erosplugin.wx_umeng_share.util.StyleUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import static com.heyn.erosplugin.wx_umeng_share.util.Constant.SHARE_PARAMS;

/**
 * Author: heynchy
 * Date:   2018/9/19
 * <p>
 * Introduce: 相关权限行为处理的Activity
 */
public class ShareAllActivity extends Activity implements IWXRenderListener {
    public static ShareAllActivity activity;
    private String mParamas;         // 传递的参数
    private ShareAction mShareAction;
    private RelativeLayout mRootView;
    private static boolean isWxSharing;  //是否调起了分享。如果调起分享，这个值为true。
    private static boolean isWxResume;   //Activity是否处于前台。


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umeng_self);
        init();
        if (PermissionUtil.initPermission(this)) {
            showShareWindow();
        } else {
            PermissionUtil.getPermission(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 处理分享成功后留在微信导致无回调的问题
        isWxSharing = false;
        isWxResume = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 处理分享成功后留在微信导致无回调的问题
        if (isWxSharing) {
            isWxSharing = false;
            //这里要延时0.2秒在判断是否回调了onResume，因为onRestart在onResume之前执行。
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 如果0.2秒后没有调用onResume，则认为是分享成功并且留着微信。
                    if (!isWxResume) {
                        if (ShareActionUtil.getSuccess() != null) {
                            ShareActionUtil.getSuccess().invoke("分享成功！");
                        }
                        finishSelf();
                    }
                }
            }, 200);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 处理分享成功后留在微信导致无回调的问题
        isWxResume = false;
    }


    /**
     * 初始化基本配置
     */
    private void init() {
        activity = this;
        mRootView = findViewById(R.id.rl_root);
        mParamas = getIntent().getStringExtra(SHARE_PARAMS);
        final JSShareEvent event = new Gson().fromJson(mParamas, JSShareEvent.class);
        mShareAction = new ShareAction(this);
        if (TextUtils.isEmpty(event.getShareType())){
            if (ShareActionUtil.getFailure()!= null){
                ShareActionUtil.getFailure().invoke("分享类型未设置");
            }
            finishSelf();
            return;
        }
        mShareAction.setDisplayList(StyleUtil.initPlatform(this, event.getShareType()))
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == SHARE_MEDIA.WEIXIN ||
                                share_media == SHARE_MEDIA.WEIXIN_FAVORITE ||
                                share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                            // 如果是分享至微信
                            isWxSharing = true;
                        } else {
                            isWxSharing = false;
                        }
                        ShareActionUtil.shareAction(ShareAllActivity.this,
                                event, share_media);
                    }
                });
    }

    /**
     * 显示分享的弹窗
     */
    private void showShareWindow() {
        mRootView.post(new Runnable() {
            @Override
            public void run() {
                mShareAction.open(new ShareBoardConfig().setOnDismissListener(
                        new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                if (ShareActionUtil.getFailure() != null) {
                                    ShareActionUtil.getFailure().invoke("取消分享！");
                                }
                               finishSelf();
                            }
                        }));
            }
        });
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
        if (requestCode == 123 && PermissionUtil.initPermission(this)) {
            // 获得相关权限
            showShareWindow();
        } else {
            if (ShareActionUtil.getFailure() != null) {
                ShareActionUtil.getFailure().invoke("权限获取失败，分享失败！");
            }
            finishSelf();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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

    /**
     * 销毁界面
     */
    public static void finishSelf() {
        isWxResume = true;
        if (activity != null) {
            activity.finish();
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
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
