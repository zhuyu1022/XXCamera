package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/7/22 - 09 : 03
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/7/22 - 09 : 03
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 */
class ManufactureRequestBean : Serializable {

    var spec_id: Int? = null //规格id,可省
    var key: String = "" // 原图 KEY
    var fair_level: FairLevel? = null //美颜参数,当不美颜时所有参数值为0
    var list_clothes_id: List<String>? = null // 服装id列表,若不换装,则为空,或者为[‘-1’]
    var need_resize: Boolean = true
    var custom_params: CustomPrarms? = null

    class FairLevel : Serializable, Cloneable {
        var leyelarge: Int = 0 // 左眼放大程度(0~10)
        var reyelarge: Int = 0 // 右眼放大程度(0~10)
        var mouthlarge: Int = 0  // 嘴巴缩小程度(0~10)
        var skinwhite: Int = 0  // 皮肤美白程度(0~10)
        var skinsoft: Int = 0 // 皮肤美肤程度(去皱纹、祛斑等)(0~10)
        var coseye: Int = 0  // 美瞳程度(0~10)
        var facelift: Int = 0  // 瘦脸程度(0~10)
        @Throws(CloneNotSupportedException::class)// 克隆失败抛出异常
        public override fun clone(): FairLevel {
            return super.clone() as FairLevel // 类型强制转换
        }
    }

//    class CustomPrarms : Serializable {
//
//        var dpi: Int = 0
//        //var photo_format: String = ""
//        var file_size: List<Int?>? = null
//        var custom_size: List<Int?>? = null
////        'file_size':[1024, None],   # 文件大小区间(单位byte), 整数,若没有要求则对应的值为None,但file_size字段必须要存在,且必须要为一个长度为2的数组.参数范围为[1024-2*1024*1024]
////            'dpi': 300, # 图片dpi,可省参数,默认为300,参数大于0
////            'photo_format':'jpg',    # 图片格式(jpg, png),可省参数,可以不传,默认为jpg
////            'custom_size':[295, 413]    # 自定义规格宽高(width, height), 整数,若spec_id为空,则会使用此参数,若此参数再为空,则为原图
//    }


}