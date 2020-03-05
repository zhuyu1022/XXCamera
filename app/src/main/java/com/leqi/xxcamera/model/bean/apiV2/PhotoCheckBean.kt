package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/1/19 - 15 : 55 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/19 - 15 : 55 
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class PhotoCheckBean : Serializable {

    var photo_format : Boolean = false
    var px_and_mm : Boolean = false
    var file_size : Boolean? = null
}