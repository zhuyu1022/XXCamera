package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

class Coupon : Serializable {

    var coupon_id: Int = 0   // 优惠卷ID,在使用优惠卷时需要传此ID
    var coupon_amount: Int = 0  // 优惠卷优惠金额(单位 分)
    var coupon_type: Int = 0   // 优惠卷类型 0 无门槛 1 满减 2 免费多背景 3 免费换装 4 免费精修 5 订单类型免减 6 免费附加规格
    var create_time: Long = 0  // 优惠卷创建时间时间戳(单位 秒)
    var expired_time: Long = 0   // 优惠卷过期时间时间戳(单位 秒)
    var use_time: Int? = null  // 使用时间,对于未使用的此字段为null
    var state: Int = 0
    var coupon_name = ""  //优惠卷名称 如"新年红包(多背景)",
    var additional_parameters: Additional? = null  // 优惠卷附加信息,里面有些参数在特定格式的优惠卷的时候才有意义

    class Additional:Serializable {
        var base_amount: Int = 0 // 满减的基础价格,只有当coupon_type为1,5时才有意义
        var coupon_note: String = ""   //安卓", // 优惠卷使用说明
        var exclude_specs_id: Int? = null// 不能使用此优惠卷的规格列表,若没有时为null,只有当coupon_type为5时才有意义
        var specs_id: Int? = null // 能使用此优惠卷的规格列表,若没有时为null,只有当coupon_type为5时才有意义
    }
}