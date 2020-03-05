package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/8 - 16 : 20 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/8 - 16 : 20 
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class CropBean : BaseCode() , Serializable {

    var result : Result? = null

    inner class Result : Serializable {
        var fee : Int = 0
        var order_id : String= ""
        var payment_state : Int = 0
        var promotion_price : Int = 0
        var share_price : Int = 0
        var comment_price : Int = 0
    }
}