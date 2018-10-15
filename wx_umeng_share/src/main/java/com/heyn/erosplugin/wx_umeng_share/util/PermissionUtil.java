package com.heyn.erosplugin.wx_umeng_share.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Author: 崔海营
 * Date:   2018/10/12
 * <p>
 * Introduce:
 */
public class PermissionUtil {

    /**
     * 友盟分享初始化相关权限
     *
     * @param activity
     */
    public static boolean initPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.GET_ACCOUNTS,
            };
            for (String permission : mPermissionList) {
                if (PackageManager.PERMISSION_GRANTED !=
                        ContextCompat.checkSelfPermission(activity, permission)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 友盟分享初始化相关权限
     *
     * @param activity
     */
    public static void getPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.GET_ACCOUNTS,
            };
            ActivityCompat.requestPermissions(activity, mPermissionList, 123);
        }
    }
}
