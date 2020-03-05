package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
* @Author: zhuxiaoyao
* @Date: 2019/1/7 - 13 : 32
* @LastEditors: zhuxiaoyao
* @LastEditTime: 2019/1/7 - 13 : 32
* @Description: idPhotoKotlin
* @Email: zhuxs@venpoo.com
*/
open class UserIDBean : BaseCode(), Serializable {

    var user_key: String? = null

    var user_id: String? = null
}
