package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/7 - 13 : 32
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/7 - 13 : 32
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class SpecResult : Serializable {

    var key : String = ""
    var value : Boolean = false

    constructor(key: String, value: Boolean) {
        this.key = key
        this.value = value
    }
    constructor() {

    }
}
