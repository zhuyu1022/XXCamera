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
class CheckBean : BaseCode() , Serializable {

    var check : Boolean = false
    var photo_check : PhotoCheckBean? = null
    var spec_result : List<SpecResult>? = null
}
