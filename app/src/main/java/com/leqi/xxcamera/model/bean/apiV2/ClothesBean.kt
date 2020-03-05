package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/7/19 - 18 : 40
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/7/19 - 18 : 40
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 */
class ClothesBean : BaseCode(), Serializable {

    var data: List<ClothesData>? = null

    class ClothesData : Serializable {
        var isSelected: Boolean = false
        var group_name: String = ""
        var value: List<ValueData>? = null
    }

    class ValueData : Serializable {
        var isSelected: Boolean = false
        var clothes_key: String = ""
        var url: String = ""
    }
}