package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/6/25 - 13 : 40
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/6/25 - 13 : 40
 * @Description: institute_feature
 * @Email: zhuxs@venpoo.com
 */
class SpecsGroupResponse : BaseCode() , Serializable {

    var result : List<SpecsGroup>? = null

    class SpecsGroup : Serializable {

        var isSelected :Boolean =false
        var group_name : String = "" // 分组名称
        var group_pic_url : String = "" //分组图片
        var specs_info : List<SpecInfo>? = null
    }

    class SpecInfo : Serializable {

        var isSelected :Boolean =false
        var group_pic_url : String = "" //分组图片
        var background_color : List<SpecColorBean>? = null // 规格背景
        var file_size : List<String?>? = null // 文件大小,为整数,单位为byte 对于无限制的区间以null代替,即可能有以下组合 [null,204800] [null,null] [20480,null]
        var format : String = ""
        var is_print : Boolean = false
        var ppi : Int? = null
        var spec_id : Int = 0
        var spec_name : String = ""
        var px_size : List<String> = ArrayList()// 像素大小
        var mm_size : List<String> = ArrayList()// 尺寸大小（毫米）
    }

}
