package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/8/4 - 15 : 22 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/8/4 - 15 : 22 
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 */
class CustomSpecInfo : Serializable {
    var pxWidth : Int = 0
    var pxHeight : Int = 0
    var fileMin : Int? = null
    var fileMax : Int? = null
    var dpi : Int = 0
    var mmWidth : Int = 0
    var mmHeight : Int = 0


}