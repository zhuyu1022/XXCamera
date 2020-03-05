package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/9 - 08 : 34 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/9 - 08 : 34 
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class WechatPayBean : BaseCode() , Serializable {

    var result : Result? = null

    inner class Result : Serializable {
        var appid : String? = null
        var noncestr : String? = null
        var partnerid : String? = null
        var prepayid : String? = null
        var sign : String? = null
        var timestamp : String? = null
    }
}
