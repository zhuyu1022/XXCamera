package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

class CouponListResponBean : BaseCode(), Serializable {
    var result: Result? = null

    inner class Result : Serializable {
        var unused: List<Coupon>? = null  // 未使用的优惠卷列表
        var expired: List<Coupon>? = null  // 过期优惠卷列表
        var used: List<Coupon>? = null  // 已使用的优惠卷列表
    }
}