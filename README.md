# eros-plugin-umeng-share
基于Eros框架下的友盟分享集成（Weex与Android的交互）：
     
      目前仅支持友盟带有分享面板的分享（包括微信，微信朋友圈，微信收藏，QQ，QQ空间，新浪微博，钉钉）
## Usage
### 1. Add dependency
```groovy
	dependencies {
	        implementation 'com.github.heynchy:eros-plugin-umeng-share:0.0.3'
	}
```
### 2. Modify AndroidManifest.xml(修改清单文件)
```java
 <!-----------------友盟分享 微信---------------------->
        <activity
            android:name="com.umeng.soexample.wxapi.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
 <!-----------------友盟分享 QQ的相关配置-------------->
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

  <!-----------------友盟分享 钉钉的相关配置-------------->
        <activity
            android:name="com.umeng.soexample.ddshare.DDShareActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
```
######  注意：
       1 . 微信：android:name="com.umeng.soexample.wxapi.WXEntryActivity" 名称一定是以Application ID 为路径， 例如  
                “com.umeng.soexample.wxapi.WXEntryActivity” 的Application ID 就是 com.umeng.soexample；否则可能会
		 收不到回调信息
       2.  QQ ： android:scheme="tencent100424468"， 注意不要丢了------“tecent”
       3.  钉钉：同微信
       4.  新浪的声明已经被封装，不用添加
### 3. 创建相关文件
       1. 微信： 在APPlication ID 的路径下创建 wxapi/WXEntryActivity.java文件;(注意路径)
```java
public class WXEntryActivity extends WXCallbackActivity {
}
```
      2. 钉钉：在APPlication ID 的路径下创建 ddshare/DDShareActivity.java文件;(注意路径)
```java
public class DDShareActivity extends DingCallBack {
}
```
###### 注意： 

         这两个文件一定要存在于APPlicationId的路径下，否则无法接受回调， 例如“com.umeng.soexample.wxapi.WXEntryActivity”， 它的
         Application ID 就是“com.umeng.soexample”
	 
### 4. 添加签名文件（针对微信的相关分享）

    在app 的 build.gradle中配置签名文件, 将签名文件拷贝至app的根目录中：
```java
    signingConfigs {
        debug {
            storeFile file('debug.keystore')
            storePassword "android"
            keyAlias "androiddebugkey"
            keyPassword "android"
        }
    }
    // 将其添加至release, debug等打包版本中
      buildTypes {
        release {
            signingConfig signingConfigs.debug
            ......
        }

        debug {
            signingConfig signingConfigs.debug
             ......
        }
```
## 完成以上配置后就可以使用了
    1. 在APPlication 文件中，初始化友盟和相关的平台参数
```java
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
	/**
         * 初始化common库，初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
	 /**
	  *  配置相关平台的参数
	  */
	// 配置微信平台的相关参数（友盟的基本方法---appKey, appscret）
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
	// 配置QQ平台的相关参数（友盟的基本方法---appKey, appscret）
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
	// 配置钉钉平台的相关参数（友盟的基本方法---appKey）
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
	// 配置微博平台的相关参数（友盟的基本方法---appKey, appscret, 安全域名）
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
```
    2. Module名称： UMShareManager
    
    3. Module方法： shareParams()
```java
 /**
     * 分享面板（默认有 微信， 朋友圈， 微信收藏， 新浪微博， QQ, QQ空间，钉钉）
     *
     * @param params  相关参数
     * @param success 成功的回调
     * @param failure 失败的回调
     */
    @JSMethod(uiThread = true)
    public void shareParams(String params, final JSCallback success, final JSCallback failure)
```
