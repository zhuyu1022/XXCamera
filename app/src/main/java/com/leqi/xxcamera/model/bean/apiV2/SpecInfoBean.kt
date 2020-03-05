package com.leqi.lwcamera.model.bean.apiV2


import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/1/8 - 13 : 32
 * @LastEditors: zhuyu
 * @LastEditTime: 2019/10/14   添加 ppi参数
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 *
 */
class SpecInfoBean : Serializable {

    var face_params: List<String>? = null
    var photo_params: PhotoParams? = null

    class PhotoParams : Serializable {
        // 背景颜色
        var background_color: ArrayList<SpecColorBean>? = null
        // 文件大小 对于无限制的区间以null代替
        var file_size: List<String?>? = null
        // 照片格式
        var format: String = ""
        //是否可以冲印
        var is_print: Boolean = false
        // 毫米大小
        var mm_size: List<String>? = null
        // 像素大小
        var px_size: List<String> = ArrayList()
        // 规格I
        var spec_id: Int = 0
        // 规格名称
        var spec_name: String = ""
        // 尺寸说明,比如像素大小为一寸但规格名不是一寸的规格此处将显示"一寸"以对此规格大小进行描述,但是此值对于某些规格是null
        var size_description: String = ""
        var icon_url: String = ""
        // 照片的放置参数,若is_print为false时,此参数无意义
        var place_params: PlaceParams? = null
        // 分辨率
        var ppi:String =""

        var isSelected :Boolean =false

    }

    class PlaceParams : Serializable {
        var params: List<List<Int?>>? = null
        var is_rotate: Boolean = false
    }
}