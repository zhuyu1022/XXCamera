package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Description:    后台接口参数
 * @Author:         ZHUYU
 * @CreateDate:     2020/2/28 10:07
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2020/2/28 10:07
 * @Version:        1.0
 */
abstract class BaseCode : Serializable {
    var code: Int = 0
    var error: String? = null
}
