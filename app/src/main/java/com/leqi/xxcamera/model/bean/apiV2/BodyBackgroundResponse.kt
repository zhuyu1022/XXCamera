package com.leqi.xxcamera.model.bean.apiV2

import com.leqi.lwcamera.model.bean.apiV2.BaseCode
import java.io.Serializable

/**
  * @Description:    形象照背景色
  * @Author:         ZHUYU
  * @CreateDate:     2020/3/3 11:05
  * @UpdateRemark:   更新说明：
  * @UpdateDate:     2020/3/3 11:05
  * @Version:        1.0
 */
class BodyBackgroundResponse : BaseCode(),Serializable {

    var result : List<BodyBackground>? = null
    class BodyBackground : Serializable {
        var name : String? = null
        var url : String? = null
    }

}