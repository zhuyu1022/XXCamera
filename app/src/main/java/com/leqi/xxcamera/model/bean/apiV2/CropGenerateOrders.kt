package com.leqi.lwcamera.model.bean.apiV2

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/6/10 - 14 : 17
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/6/10 - 14 : 17
 * @Description: institute_feature
 * @Email: zhuxs@venpoo.com
 */
class CropGenerateOrders {

    var key : String = ""
    var change_bg_and_cut : Boolean = false
    var crop_size : List<Int?> = listOf(0 , 0)
    var file_size : List<Int?> = listOf(0 , 0)
    var ppi :Int = 0
    var server_control_file_size : Boolean = false
}