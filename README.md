# eros-plugin-umeng-share
基于Eros框架下的友盟分享集成（Weex与Android的交互）：
    1.目前仅支持友盟带有分享面板的分享（包括微信，微信朋友圈，微信收藏，QQ， QQ空间，新浪微博，钉钉）
    
## Usage
###  Add dependency
```groovy
	dependencies {
	        implementation 'com.github.heynchy:eros-plugin-umeng-share:0.0.3'
	}
```
### Modify AndroidManifest.xml(修改清单文件)
```java
 <!--友盟分享 微信----->
        <activity
            android:name="com.umeng.soexample.wxapi.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <!--友盟分享 QQ-android:scheme="tencent100424468"---注意后面的ID值---->
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="tencent100424468" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />

        // 钉钉集成,android:name格式同微信，一定是APPlication ID为名的路径
        <activity
            android:name="com.umeng.soexample.ddshare.DDShareActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
```
######  注意：
       1 . 微信：  android:name="com.umeng.soexample.wxapi.WXEntryActivity" 名称一定是以Application ID 为路径，例如  
                    “com.umeng.soexample.wxapi.WXEntryActivity” 的Application ID 就是 com.umeng.soexample；
       2.  QQ  ： android:scheme="tencent100424468"， 注意不要丢了------“tecent”
       3.  钉钉：同微信
       4. 新浪的声明已经被封装，不用添加
