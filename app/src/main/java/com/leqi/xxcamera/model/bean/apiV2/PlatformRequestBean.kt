package com.leqi.lwcamera.model.bean.apiV2

class PlatformRequestBean  {

    /**
     * serial_number 流水号
     * back_number : 背景颜色下标
     * is_fair 是否美颜 true 美颜 false 未美颜
     */

    var serial_number: String? = null
    var consignee_name: String? = null
    var consignee_phone: String? = null
    var back_number: Int? = null
    var print_number: Int ?= null
    var is_fair: Boolean = true
}
