package com.leqi.xxcamera.model.bean.apiV2

import com.leqi.lwcamera.model.bean.apiV2.BaseCode
import java.io.Serializable

class BodySpecsResponse: BaseCode(),Serializable {
    val result: List<BodySpecs>? = null
    class BodySpecs {
        val head_height: List<Double>? = null
        val headtop_margin: List<Double>? = null
        val is_print: Boolean = false
        val is_rotate: Boolean = false
        val mm_size: List<Int> ? = null
        val name: String = ""
        val params: String = ""
        val px_size: List<Int>? = null
        val quantity: Int = 0
        val spec_id: Int = 0
    }
}

