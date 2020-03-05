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
class AdverBean : BaseCode() , Serializable {

    var result : List<Result>? = null

    inner class Result : Serializable {
        var img_url : String? = null
        var jump_url : String? = null
    }
}
