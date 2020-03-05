package com.leqi.xxcamera.module.system.mvp.presenter

import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.leqi.baselib.base.BaseKotlinPresenter
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.model.bean.apiV2.FeedbackRequestBean
import com.leqi.xxcamera.api.HTTP_SUCCESS
import com.leqi.xxcamera.api.HttpFactory
import com.leqi.xxcamera.module.system.mvp.view.FeedbackView
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * 描述：
 * 作者：ZHUYU
 * 日期：2019/9/26 9:28
 */
class FeedbackPresenter: BaseKotlinPresenter<FeedbackView>() {

     fun sendFeedBack(content: String, contactInformation: String, imageList: List<String>) {


        val feedbackRequestBean = FeedbackRequestBean()
        feedbackRequestBean.content = content
        feedbackRequestBean.feed_type = 1
        feedbackRequestBean.device_model = android.os.Build.MODEL
        feedbackRequestBean.system_version = android.os.Build.VERSION.SDK_INT.toString()
        if (imageList.isNotEmpty()) {
            feedbackRequestBean.images = imageList
        }
        if (contactInformation != "") {
            feedbackRequestBean.user_contact = contactInformation
        }
        val toJson = Gson().toJson(feedbackRequestBean)
        val requestBody = RequestBody.create(MediaType.parse(Config.JSON_TYPE), toJson)


         launchRequest(request = {
             HttpFactory.getInstance().service.feedback(requestBody)
         }, onSuccess = {
             if (HTTP_SUCCESS == it.code) {
                 ToastUtils.showShort("发送反馈成功，感谢您的反馈。")
                 mvpView?.clearData()
             }else{
                 mvpView?.onError("${it.error}")
             }
         }, onFail = {
             mvpView?.onError("发送反馈失败， 请稍后重试~")
         }, onNetError = {
             ToastUtils.showShort("没有网络")
         })

    }
}