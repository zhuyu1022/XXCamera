package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

class ShowSpecInfoBean : Serializable {
    var isCrop: Boolean = false
    var specName: String? = null
    var pxSize: ArrayList<String> = ArrayList()
    var printSize: ArrayList<String> = ArrayList()
    var ppi: Int? = null
    var fileSize: String? = null
}