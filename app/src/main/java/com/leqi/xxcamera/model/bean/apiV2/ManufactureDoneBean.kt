package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/7/19 - 18 : 50
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/7/19 - 18 : 50
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 */
class ManufactureDoneBean : BaseCode() , Serializable {

    var result : Result? = null

    class Result : Serializable {

        var fee : Int = 0
        var is_print : Boolean = false
        var serial_number : String? = null
        var url : ArrayList<String> = ArrayList()
        var url_print : ArrayList<String>? = null
    }
}