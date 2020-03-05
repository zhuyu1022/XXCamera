package com.leqi.xxcamera.model.bean.apiV2

import com.leqi.lwcamera.model.bean.apiV2.BaseCode
import java.io.Serializable

class BodyPictureResponse: BaseCode() ,Serializable{
     val result: BodyPicture? = null
    class BodyPicture :Serializable{
        val key: String = ""
        val pose_pic: String = ""
    }
 }

