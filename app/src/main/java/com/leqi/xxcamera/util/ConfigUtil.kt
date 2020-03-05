package com.leqi.lwcamera.util

import com.blankj.utilcode.util.LogUtils
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.model.bean.apiV2.AppSwitchBean

object ConfigUtil {

    fun setConfig(appSwitchBean: AppSwitchBean){
        Config.electronic = appSwitchBean.ele_price
        Config.cropPrice = appSwitchBean.cut_pirce
        Config.replacementPrice = appSwitchBean.change_background_price
        Config.replacement2cropPrice = appSwitchBean.change_bg_and_cut_price
        Config.printPlatformId = appSwitchBean.print_platform_id
        Config.multiplePrice = appSwitchBean.multiple_background_price
        Config.changeClothePrice = appSwitchBean.change_clothe_price
        Config.changeClotheMultiBackgroundPrice = appSwitchBean.change_clothe_multi_background_price
        Config.changeClothePrintPrice = appSwitchBean.change_clothe_print_price
        Config.defaultBeautyLevel = appSwitchBean.default_beauty_level
        Config.serverBeautyVersion = appSwitchBean.server_beauty_version
        if (appSwitchBean.home_pop) {
            LogUtils.d("首页弹窗开启")
            Config.homeSwitch = appSwitchBean.home_pop
            Config.homeWindowTitle = appSwitchBean.home_message!!.title!!
            Config.homeWindowText = appSwitchBean.home_message!!.message!!
        }
    }

}