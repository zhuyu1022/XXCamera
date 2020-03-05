package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

class PlatformBean : BaseCode(), Serializable {

    /**
     * code : 200
     * image_key : SJKDFGHJSDKFFDSFSD
     * spec_name : 一寸
     * unit_per_printing : 3
     * image_url : http://xxxx
     */

    var image_key: String? = null
    var spec_name: String? = null
    var unit_per_printing: Int = 0
    var image_url: String? = null
}
