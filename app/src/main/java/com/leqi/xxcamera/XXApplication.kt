package com.leqi.xxcamera

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.SPStaticUtils
import com.blankj.utilcode.util.Utils
import com.leqi.baselib.net.HttpProvider
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.config.USER_HEADER
import com.leqi.lwcamera.util.GlideQiyuImageLoader
import com.leqi.xxcamera.BuildConfig.FLAVOR_market
import com.leqi.xxcamera.util.GlideGifImagerLoader
import com.qiyukf.unicorn.api.StatusBarNotificationConfig
import com.qiyukf.unicorn.api.UICustomization
import com.qiyukf.unicorn.api.Unicorn
import com.qiyukf.unicorn.api.YSFOptions
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import org.jetbrains.anko.toast
import java.lang.Exception
import java.lang.ref.WeakReference


/**
 * @Description:    整个应用的Application
 * @Author:         ZHUYU
 * @CreateDate:     2019/11/11 16:07
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2019/11/11 16:07
 * @Version:        1.0
 */
class XXApplication : Application() {

    companion object {
        lateinit var weChatApi: IWXAPI
        lateinit var appContext: WeakReference<Context>
    }

    override fun onCreate() {
        super.onCreate()
        appContext = WeakReference(applicationContext)


        HttpProvider.getInstance().setConfig(Config.BASE_URL,Config.APP_ID,Config.USERKEY, BuildConfig.VERSION_NAME,BuildConfig.FLAVOR_market)

        //utilcode初始化
        Utils.init(this)
        //注册给微信
        regToWX()
        //初始化友盟
        initUmeng()
        //初始化腾讯Bugly
        initBugly()
        //初始化网易七鱼
        initQiyu()
        //初始化OpenInstall
        //initOpenInstall()
    }

    private fun regToWX() {
        weChatApi = WXAPIFactory.createWXAPI(this, Config.WX_APP_ID, false)
        weChatApi.registerApp(Config.WX_APP_ID)
    }

    private fun initUmeng() {
        UMConfigure.init(this, Config.UMENG_APPKEY, BuildConfig.FLAVOR, UMConfigure.DEVICE_TYPE_PHONE, null)
        MobclickAgent.setCatchUncaughtExceptions(false)
        UMConfigure.setLogEnabled(true)
        PlatformConfig.setWeixin(Config.WX_APP_ID, Config.WX_APP_SECRECT)
        PlatformConfig.setQQZone("1110060202","Z08nN7KRwQ64VT4b")
    }


    private fun initBugly() {
        //调试设备设置成“开发设备”。
        CrashReport.setIsDevelopmentDevice(applicationContext, BuildConfig.DEBUG)
        val strategy = UserStrategy(applicationContext)
        //在这里设置strategy的属性，在bugly初始化时传入
        strategy.appChannel = FLAVOR_market  //设置渠道
        strategy.appReportDelay = 20000 //10s后联网同步数据。改为20s
        CrashReport.initCrashReport(applicationContext, "9936d9b7cb", false, strategy)
    }

    /**
     * 初始化网易七鱼
     */
    private fun initQiyu() {
        Unicorn.init(this, Config.WANGYI_QIYU, options(), GlideQiyuImageLoader(this))
    }

    // 如果返回值为null，则全部使用默认参数。
    private fun options(): YSFOptions {
        val uiCustomization = UICustomization()
        uiCustomization.titleBarStyle = 0
        uiCustomization.titleCenter = true
        uiCustomization.hideKeyboardOnEnterConsult = true
        uiCustomization.rightAvatar= SPStaticUtils.getString(USER_HEADER)//设置用户头像
        val options = YSFOptions()
        options.statusBarNotificationConfig = StatusBarNotificationConfig()
        options.gifImageLoader = GlideGifImagerLoader(this)
        options.uiCustomization = uiCustomization
        return options
    }
//
//    /**
//     * 初始化OpenInstall
//     */
//    private fun initOpenInstall() {
//        if (isMainProcess()) {
//            OpenInstall.init(this)
//        }
//    }


    private fun isMainProcess(): Boolean {
        val pid = android.os.Process.myPid()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return applicationInfo.packageName == appProcess.processName
            }
        }
        return false
    }


}
