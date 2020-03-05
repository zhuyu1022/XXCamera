package com.leqi.xxcamera.model.bean.apiV2

import com.leqi.lwcamera.model.bean.apiV2.BaseCode
import java.io.Serializable

class BodySerialResponse: BaseCode(),Serializable {
    val result: BodySerial? =null

    class BodySerial:Serializable {
        val serial_number: String = ""
        val url: List<String>? = null
    }

}

