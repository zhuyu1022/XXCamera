package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/7/19 - 18 : 45
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/7/19 - 18 : 45
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 *  制作结果
 */
class ManufactureBean : BaseCode(), Serializable {

    var result: List<Result>? = null
    var beauty_intermediate_result: String? = null
    var dress_up_message: String? = null

    class Result : Serializable {
        var cloth_id: String? = null // 对应的服装ID, 若制作时没有选定服装,则数组元素将只有一个,且此字段值为'-1'
        var image_url: String? = null // 透明背景的预览图
        var key: String? = null // 此制作结果对应的key,将在生成流水号时使用
    }
}