package com.leqi.lwcamera.util

import com.leqi.lwcamera.model.bean.apiV2.Coupon

object CouponUtil {
    /**
     * 判断优惠卷是否可用(根据价格判断是否满减)
     */
    fun isCouponAvailable(price: Int, coupon: Coupon): Boolean {
        // 优惠卷类型 0 无门槛 1 满减 2 免费多背景 3 免费换装 4 免费精修 5 订单类型免减 6 免费附加规格
        if (coupon.coupon_type == 1) {
            if ( price>=coupon.additional_parameters?.base_amount!! ) {
                return true
            }
        }
        return false
    }
}