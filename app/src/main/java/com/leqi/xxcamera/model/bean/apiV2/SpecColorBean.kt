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
class SpecColorBean: Serializable {
    // 规格背景颜色
    var color_name: String? = null
    var start_color: Int? = null
    var enc_color: Int? = null

    constructor(){}

    constructor(color_name: String?,start_color: Int?,enc_color: Int?){
        this.color_name=color_name
        this.start_color=start_color
        this.enc_color=enc_color
    }
}