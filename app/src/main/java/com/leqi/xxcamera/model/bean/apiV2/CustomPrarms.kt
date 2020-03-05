package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

class CustomPrarms : Serializable {


    var dpi: Int = 0
    //var photo_format: String = ""
    var file_size: List<Int?>? = null
    var custom_size: List<Int?>? = null
//        'file_size':[1024, None],   # 文件大小区间(单位byte), 整数,若没有要求则对应的值为None,但file_size字段必须要存在,且必须要为一个长度为2的数组.参数范围为[1024-2*1024*1024]
//            'dpi': 300, # 图片dpi,可省参数,默认为300,参数大于0
//            'photo_format':'jpg',    # 图片格式(jpg, png),可省参数,可以不传,默认为jpg
//            'custom_size':[295, 413]    # 自定义规格宽高(width, height), 整数,若spec_id为空,则会使用此参数,若此参数再为空,则为原图

}