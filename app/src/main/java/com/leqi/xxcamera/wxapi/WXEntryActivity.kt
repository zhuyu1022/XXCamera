package com.leqi.ChangkuanPhoto.wxapi

import android.content.Intent
import android.os.Bundle

import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.blankj.utilcode.util.LogUtils
import com.leqi.xxcamera.XXApplication
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.umeng.socialize.weixin.view.WXCallbackActivity


/**
 * @Description:    用于微信分享和登录的回调
 * @Author:         ZHUYU
 * @CreateDate:     2019/12/18 16:49
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2019/12/18 16:49
 * @Version:        1.0
 */
class WXEntryActivity : WXCallbackActivity(), IWXAPIEventHandler {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        XXApplication.weChatApi.handleIntent(intent, this)
        LogUtils.d("微信注册回调2")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        XXApplication.weChatApi.handleIntent(intent, this)
        LogUtils.d("微信注册回调3")
    }

    override fun onReq(p0: BaseReq?) {

    }

    override fun onResp(baseResp: BaseResp?) {
        val errCode = baseResp!!.errCode
        val code = ( baseResp as SendAuth.Resp).code
        LogUtils.d("baseResp.errCode : $errCode")
        LogUtils.d("baseResp.code : $code")
//        val intent = Intent(LoginActivity.ACTION_WECHAT_LOGIN)
//        intent.putExtra("errCode", errCode)
//        intent.putExtra("code", code)
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        this.finish()
    }


}
