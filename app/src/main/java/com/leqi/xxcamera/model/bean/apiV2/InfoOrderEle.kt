package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/9 - 09 : 50 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/9 - 09 : 50 
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class InfoOrderEle : Serializable {
    var waiver_amount: Int = 0
    var order_id: String = ""
    var spec_name: String = ""
    var paymmet_time: Int = 0
    var create_time: Long = 0L
    var out_time: Int = 0
    var url: ArrayList<String> = ArrayList()
    var url_print: ArrayList<String>? = null
    var serial_number: String = ""
    var spec_id: Int = 0
    var fee: Int = 0
    var is_print: Boolean = false
    var order_state: Boolean = false
    var extraction_code: String? = null
    var is_fair: Boolean = false
    var back_number: Int = 0
    var mm_size: List<String?>? = null
    var px_size: List<String?>? = null
    var spec_add_params: SpecAddParams? = null
    var composition: Composition? = null

    class SpecAddParams : Serializable {
        var background_color: List<SpecColorBean>? = null
        var exp: String? = null
    }

    class Composition : Serializable {
        var basic_price: Int = 0
        var additional_composition: List<AdditionalComposition> = ArrayList()
    }

    class AdditionalComposition : Serializable {
        var exp: String? = null
        var price: Int = 0
    }

}