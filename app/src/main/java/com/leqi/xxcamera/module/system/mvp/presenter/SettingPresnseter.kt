package com.leqi.xxcamera.module.system.mvp.presenter

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.leqi.baselib.base.BaseKotlinPresenter
import com.leqi.baselib.base.BasePresenter

import com.leqi.lwcamera.module.home.mvp.view.SettingView
import com.leqi.xxcamera.api.HTTP_SUCCESS
import com.leqi.xxcamera.api.HttpFactory
import io.reactivex.functions.Consumer

class SettingPresnseter : BaseKotlinPresenter<SettingView>() {

    fun getNewVersion() {
        launchRequest(request = {
            HttpFactory.getInstance().service.checkVer()
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                mvpView?.showNewVersion(it)
            }else{
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError("获取新版本信息失败")
        }, onNetError = {
            mvpView?.onError("未检测到网络")})
    }

    fun getGuideUrl() {
        launchRequest(request = {
            HttpFactory.getInstance().service.link("photography_course")
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                if (it.url !== "") {
                    mvpView?.showGuide(it.url)
                } else {
                    mvpView?.onError("服务器连接异常~~")
                }
            } else {
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError("服务器连接异常")
        }, onNetError = {
            mvpView?.onError("未检测到网络")})
    }

}