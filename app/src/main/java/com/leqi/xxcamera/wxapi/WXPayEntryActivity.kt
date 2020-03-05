package com.leqi.ChangkuanPhoto.wxapi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.blankj.utilcode.util.LogUtils
import com.leqi.xxcamera.XXApplication
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler


class WXPayEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        XXApplication.weChatApi.handleIntent(intent, this)
        LogUtils.d("微信注册回调2")
    }

    override fun onReq(baseReq: BaseReq) {
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        XXApplication.weChatApi.handleIntent(intent, this)
        LogUtils.d("微信注册回调3")
    }

    override fun onResp(baseResp: BaseResp) {
        val code = baseResp.errCode
        LogUtils.d("baseResp.errCode : $code")
        //发送支付结果回调的广播
//        val intent = Intent(PayActivity.ACTION_WECHAT)
//        intent.putExtra("code", code)
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        this.finish()
    }
}
