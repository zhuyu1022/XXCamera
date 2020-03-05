package com.leqi.lwcamera.model.bean.apiV2

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/7/22 - 09 : 17
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/7/22 - 09 : 17
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 */
class ManufactureDoneRequestBean {

    var spec_id : Int = 0
    var img_key : String = "" //制作结果对应的key
    var original_key : String = ""// 原图上传时的key
    var custom_params: CustomPrarms? = null
}