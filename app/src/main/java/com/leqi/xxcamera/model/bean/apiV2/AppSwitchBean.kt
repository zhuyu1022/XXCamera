package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/7 - 13 : 32
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/7 - 13 : 32
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class AppSwitchBean : BaseCode(), Serializable {

    var home_pop: Boolean = false    // 首页弹屏开关 false 关 true 开
    var print_pop: Boolean = false   // 冲印页面弹屏开关 false 关 true开
    var change_message_pop: Boolean = false  // 换背景页面弹窗开关 false 关 true开
    var home_message: MessageInfo? = null
    var change_message: MessageInfo? = null
    var print_price: PrintPrice? = null
    var share_cash_price: Int = 0

    var cut_pirce: Int = 0   // 裁剪金额
    var ele_price: Int = 0    // 电子照金额
    var change_background_price: Int = 0  //　换背景金额
    var change_bg_and_cut_price: Int = 0  // 换背景并裁剪金额
    var crop_pixel_minimum: List<Int>? = null  // 裁剪像素的宽高最小值(宽,高)
    var print_platform_id: Int = -1   // APP在冲印平台的ID
    var multiple_background_price: Int = 0 // 多背景保存的价格(分)
    var change_clothe_price: Int = 0// 换装单背景价格(分)
    var change_clothe_multi_background_price: Int = 0// 换装多背景价格
    var change_clothe_print_price: Int = 0// 冲印换装单背景价格(分)
    var default_beauty_level: ManufactureRequestBean.FairLevel = ManufactureRequestBean.FairLevel()
    var server_beauty_version: Int = 1000

    inner class MessageInfo : Serializable {
        var message: String? = null    // 内容
        var title: String? = null  // 标题
    }

    inner class PrintPrice : Serializable {
        var normal: Int = 0  // 普通快递冲印单价
        var urgent: Int = 0   // 加急快递冲印单价
    }
}
