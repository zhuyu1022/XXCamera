package com.leqi.lwcamera.module.home.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.LogUtils.getConfig
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPStaticUtils
import com.blankj.utilcode.util.ToastUtils
import com.leqi.baselib.base.BaseKotlinPresenter
import com.leqi.baselib.base.BasePresenter
import com.leqi.baselib.net.HttpProvider
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.config.USER_ID
import com.leqi.lwcamera.config.USER_KEY
import com.leqi.lwcamera.http.HttpService
import com.leqi.lwcamera.module.home.mvp.view.WelcomeView
import com.leqi.lwcamera.util.SpTool
import com.leqi.xxcamera.api.HTTP_SUCCESS
import com.leqi.xxcamera.api.HttpFactory
import io.reactivex.functions.Consumer

class WelcomePresenter(context: Context) : BaseKotlinPresenter<WelcomeView>() {
    private val mContext: Context = context
    private var count: Int = 0 //控制一下递归次数 防止oom
    private var countPrice: Int = 0 //控制一下递归次数 防止oom
    //初始化app ，就是获取用户id
    fun initApp() {
        if (SPStaticUtils.getString(USER_KEY).isNullOrEmpty()) {
            launchRequest(request = {
                HttpFactory.getInstance().service.userID()
            }, onSuccess = {
                when (HTTP_SUCCESS == it.code) {
                    true -> {
                        LogUtils.d("获取到userID====${it.user_key!!}")
                        SPStaticUtils.put(USER_KEY, it.user_key!!)
                        SPStaticUtils.put(USER_ID, it.user_id!!)
                        Config.USERKEY = it.user_key!!
                        HttpProvider.getInstance().setConfig( Config.USERKEY)
                        getConfig()
                    }
                    false -> {
                        if (this.count < 5) {
                            count++
                            initApp()
                        } else {
                            ToastUtils.showShort("${it?.error}")
                            System.out
                        }
                    }
                }
            }, onFail = {
                mvpView?.onInitFail()
                ToastUtils.showShort(it.toString())
            }, onNetError = {
                mvpView?.onInitFail()
            })
        } else {
            Config.USERKEY = SPStaticUtils.getString(USER_KEY)
            HttpProvider.getInstance().setConfig( Config.USERKEY)
            getConfig()
            LogUtils.d("CKApplication:::::::Config.USERID===${Config.USERKEY}")
        }

    }

    private fun getConfig() {
        launchRequest(request = {

            HttpFactory.getInstance().service.appSwitch()
        }, onSuccess = {
            when (HTTP_SUCCESS == it.code) {
                true -> {
                    LogUtils.d("缓存服务器价格正常!! 使用服务器价格")
                    Config.electronic = it.ele_price
                    Config.cropPrice = it.cut_pirce
                    Config.replacementPrice = it.change_background_price
                    Config.replacement2cropPrice = it.change_bg_and_cut_price
                    Config.printPlatformId = it.print_platform_id
                    Config.multiplePrice = it.multiple_background_price
                    Config.changeClothePrice = it.change_clothe_price
                    Config.changeClotheMultiBackgroundPrice =
                        it.change_clothe_multi_background_price
                    Config.changeClothePrintPrice = it.change_clothe_print_price
                    Config.defaultBeautyLevel = it.default_beauty_level
                    Config.serverBeautyVersion = it.server_beauty_version
                    if (it.home_pop) {
                        LogUtils.d("首页弹窗开启")
                        Config.homeSwitch = it.home_pop
                        Config.homeWindowTitle = it.home_message!!.title!!
                        Config.homeWindowText = it.home_message!!.message!!
                    }
                    mvpView?.onInitSuccess()
                }
                false -> {
                    if (countPrice < 10) {
                        countPrice++
                        getConfig()
                    } else {
                        mvpView?.onInitFail()
                        LogUtils.d("缓存服务器价格出错!! 使用默认的本地价格")
                    }
                }
            }
        }, onFail = {
            LogUtils.d("缓存服务器价格出错!! 使用默认的本地价格")
        }, onNetError = {
            mvpView?.onInitFail()
        })
    }

}