package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/7/19 - 18 : 10
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/7/19 - 18 : 10
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 */
class CropSpecBean : BaseCode(), Serializable {

    var data: List<CropData>? = null

    class CropData : Serializable {
        var isSelected: Boolean = false
        var name: String? = null
        var spec_id: Int = 0
        var px_size: List<String> = ArrayList()
        var mm_size: List<String> = ArrayList()
        var ppi: Int = 300
        var file_size: List<String?>? = null
        var url: String? = null
    }
}