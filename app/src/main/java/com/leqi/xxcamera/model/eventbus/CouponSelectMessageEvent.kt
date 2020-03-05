package com.leqi.lwcamera.model.eventbus

import com.leqi.baselib.Entity.MessageEvent
import com.leqi.lwcamera.model.bean.apiV2.Coupon

class CouponSelectMessageEvent : MessageEvent() {
    var coupon: Coupon? = null
}