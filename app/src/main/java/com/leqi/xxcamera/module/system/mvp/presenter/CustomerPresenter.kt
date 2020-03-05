package com.leqi.xxcamera.module.system.mvp.presenter

import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.leqi.baselib.base.BaseKotlinPresenter

import com.leqi.xxcamera.api.HTTP_SUCCESS
import com.leqi.xxcamera.api.HttpFactory
import com.leqi.xxcamera.module.system.mvp.view.CustomerView
import io.reactivex.functions.Consumer

class CustomerPresenter: BaseKotlinPresenter<CustomerView>() {
    /**
     * 获取手机号
     */
    fun getPhoneNumber() {

        launchRequest(request = {
            HttpFactory.getInstance().service.customerServiceNumber()
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                mvpView?.showPhoneNumber(it)
            }else{
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            ToastUtils.showShort(it.toString())
        }, onNetError = {
            ToastUtils.showShort("未检测到网络")
        })

    }


    /**
     * 获取问题列表
     */
    fun getQuestionData() {
        launchRequest(request = {
            HttpFactory.getInstance().service.problem("common_problem")
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                val listCache = it.result
                mvpView?.showQuestionList(listCache)
            }else{
                ToastUtils.showShort("${it.error}")
            }
        }, onFail = {
            ToastUtils.showShort("获取问题信息失败")
        }, onNetError = {
            ToastUtils.showShort("未检测到网络")
        })
    }
}