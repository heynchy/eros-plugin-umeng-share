# eros-plugin-umeng-share
基于Eros框架下的友盟分享集成（Weex与Android的交互）：
     
      目前仅支持友盟带有分享面板的分享（包括微信，微信朋友圈，微信收藏，QQ，QQ空间，新浪微博，钉钉）
## Usage
### 1. Add dependency
```groovy
	dependencies {
	        implementation 'com.github.heynchy:eros-plugin-umeng-share:0.0.5'
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

#### Android的参数初始化以及相关的方法介绍
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
### JS 端的使用方法
    1. 分享文本-----shareType: Text
```java
    /**
     *  分享纯文本
     *  content：  文本内容（不能为空）
     *  shareType：分享类型（不能为空）
     */
    weex.requireModule('UMShareManager').shareParams({
          content: '这是一个未来！！',
          shareType: 'Text'
    }, success => {
          console.log("heyn----success: " + success);
    }, failure => {
          console.log("heyn----failure: " + failure);
    })
```
    2. 分享网络图片-----shareType: Image
```java
    /**
     *  分享图片
     *  imageUrl： 图片链接（不能为空）
     *  shareType：分享类型（不能为空）
     */
     weex.requireModule('UMShareManager').shareParams({
          imageUrl: 'https://mobile.umeng.com/images/pic/home/social/img-1.png',
          shareType: 'Image'
     }, success => {
          console.log("heyn----success: " + success);
     }, failure => {
          console.log("heyn----failure: " + failure);
     })
```
    3. 分享网页------ shareType:webPage
```java
    /**
     *  分享网页
     *  url：      网页链接（不能为空）
     *  title：    网页标题（可为空）
     *  content：  网页简介（可为空）
     *  imageUrl： 网页缩略图链接（可为空）
     *  shareType：分享类型（不能为空）
     */
     weex.requireModule('UMShareManager').shareParams({
          url: 'https://www.baidu.com/',  
          title:'我的网页分享ddd',
          content:'这是一个网页分享',
          imageUrl:'https://mobile.umeng.com/images/pic/home/social/img-1.png',
          shareType: 'webPage'
     }, success => {
          console.log("heyn----success: " + success);
     }, failure => {
          console.log("heyn----failure: " + failure);
     })
```
    4. 分享图文-----shareType:TextImage
```java
    /**
     *  分享图文
     *  content :  文本内容 (不能为空)
     *  imageUrl： 图片链接（不能为空）
     *  shareType：分享类型（不能为空）
     */
     weex.requireModule('UMShareManager').shareParams({
          content: '分享图文',
          imageUrl:'https://mobile.umeng.com/images/pic/home/social/img-1.png',
          shareType: 'TextImage'
     }, success => {
          console.log("heyn----success: " + success);
     }, failure => {
          console.log("heyn----failure: " + failure);
     })
```
    5. 分享音频----shareType: music
```java
/**
 *  分享音乐
 *  url:      音乐链接（不能为空）
 *  title:    音乐标题（可为空）
 *  content:  音乐简介（可为空）
 *  imageUrl: 缩略图  （可为空）
 *  shareType:分享类型 (不能为空)
 */
 weex.requireModule('UMShareManager').shareParams({
      url:'https://y.qq.com/n/yqq/song/108782194_num.html?ADTAG=h5_playsong&no_redirect=1',
      title:'音乐分享',
      content: '这是一首歌',
      imageUrl:'https://mobile.umeng.com/images/pic/home/social/img-1.png',
      shareType: 'music'
 }, success => {
      console.log("heyn----success: " + success);
 }, failure => {
      console.log("heyn----failure: " + failure);
 })
```
    6. 分享视频------shareType:video
```java
  /**
   * 分享视频
   * url:       视频链接 (不能为空)
   * title:     视频标题 (可为空)
   * content:   视频简介 (可为空)
   * imageUrl:  缩略图   (可为空)
   * shareType: 分享类型 (不能为空)
   */
   weex.requireModule('UMShareManager').shareParams({
        url:'http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html',
        title: '视频标题',
        content: '视频分享',
        imageUrl:'https://mobile.umeng.com/images/pic/home/social/img-1.png',
        shareType: 'video'
   }, success => {
        console.log("heyn----success: " + success);
   }, failure => {
        console.log("heyn----failure: " + failure);
   })
```
    7. 分享表情 ----shareType:emoji
```java
   /**
    * 分享表情
    * imageUrl:  表情链接 （不能为空）
    * shareType: 分享类型 （不能为空）
    */
    weex.requireModule('UMShareManager').shareParams({
         imageUrl:'https://mobile.umeng.com/images/pic/home/social/img-1.png',
         shareType: 'emoji'
    }, success => {
         console.log("heyn----success: " + success);
    }, failure => {
         console.log("heyn----failure: " + failure);
    })
```
    8. 分享小程序----shareType: minApp
```java 
   /**
    * 分享小程序
    * url:      小程序的链接
    * title:    小程序的标题
    * content:  小程序简介
    * imageUrl: 缩略图
    * path:     小程序的页面路径
    * userName: 小程序的ID
    * shareType:分享类型 
    */
    weex.requireModule('UMShareManager').shareParams({
         url:'http://mobile.umeng.com/social',
         title:'小程序白标题',
         content: '分享小程序',
         imageUrl:'https://mobile.umeng.com/images/pic/home/social/img-1.png',
         path:'pages/page10007/page10007',
         userName:'gh_3ac2059ac66f',
         shareType: 'minApp'
   }, success => {
         console.log("heyn----success: " + success);
   }, failure => {
         console.log("heyn----failure: " + failure);
   })
```
License
------
    Copyright 2018 heynchy
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
