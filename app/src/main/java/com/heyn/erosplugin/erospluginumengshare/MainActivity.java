package com.heyn.erosplugin.erospluginumengshare;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv_text);
//        final JSShareEvent event = new JSShareEvent();
//        event.setContent("这是一个未来");
//        event.setTitle("这是一个将来");
//        event.setShareType("TEXT");
//        initPermission();
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new ShareAction(MainActivity.this)
//                        .setDisplayList(StyleUtil.initPlatform(MainActivity.this, event.getShareType()))
//                        .setShareboardclickCallback(new ShareBoardlistener() {
//                            @Override
//                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//                                ShareActionUtil.shareAction(
//                                        MainActivity.this,
//                                        event,
//                                        share_media);
//                            }
//                        }).open();
//            }
//        });

    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS
            };
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("heyn", "onRequestPermissionsResult");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
