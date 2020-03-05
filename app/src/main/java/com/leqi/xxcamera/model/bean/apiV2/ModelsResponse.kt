package com.leqi.xxcamera.model.bean.apiV2

import com.leqi.lwcamera.model.bean.apiV2.BaseCode
import java.io.Serializable

class ModelsResponse:BaseCode(),Serializable{
    val result: List<ModelGroup>?=null
    class ModelGroup{
        val group_name: String = ""
        val value: List<Model>?=null
        class Model{
            val contour_url: String = ""
            val model_url: String=""
        }
    }
}







