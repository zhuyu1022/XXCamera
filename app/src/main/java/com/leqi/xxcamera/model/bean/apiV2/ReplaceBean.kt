package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/8 - 16 : 15 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/8 - 16 : 15 
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class ReplaceBean : BaseCode(), Serializable {

    var result: Result? = null

    inner class Result : Serializable {
        var file_name_list: List<String>? = null
        var size: List<Int>? = null
        var serial_number: String = ""
        var fee: Int? = null
        var back_colors: List<SpecColorBean>? = null
    }
}