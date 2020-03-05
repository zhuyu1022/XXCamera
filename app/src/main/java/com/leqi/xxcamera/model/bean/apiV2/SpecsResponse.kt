package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * 包括1、热门规格 2、规格详情 3、规格搜索  共用
 */
class SpecsResponse:BaseCode(),Serializable{
    var result :List<SpecInfoBean>?=null
}