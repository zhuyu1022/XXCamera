package com.leqi.ChangkuanPhoto.wxapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.leqi.lwcamera.config.Config
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class AppRegister : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val msgApi = WXAPIFactory.createWXAPI(context, null)
        msgApi.registerApp(Config.WX_APP_ID)

        LogUtils.d("微信注册回调0")
    }
}
