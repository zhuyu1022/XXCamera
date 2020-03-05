package com.leqi.lwcamera.module.shoot.mvp.view

import com.leqi.baselib.base.BaseView
import com.leqi.lwcamera.model.bean.apiV2.ManufactureBean
import com.leqi.lwcamera.model.bean.apiV2.SearchSpecIdBean
import com.leqi.lwcamera.model.bean.apiV2.SpecsResponse
import com.leqi.lwcamera.model.bean.apiV2.SpecsGroupResponse
import com.leqi.xxcamera.model.bean.apiV2.BodyBackgroundResponse
import com.leqi.xxcamera.model.bean.apiV2.BodyPictureResponse
import com.leqi.xxcamera.model.bean.apiV2.BodySpecsResponse
import com.leqi.xxcamera.model.bean.apiV2.ModelsResponse

interface NewCameraView :BaseView{

    fun showModels(it: ModelsResponse)
    fun showBodyPicture(it: BodyPictureResponse)

    /**
     * 获取规格
     */
    fun showSpecs(it: BodySpecsResponse)

    /**
     * 获取形象照背景色
     */
    fun showBackground(it: BodyBackgroundResponse)

}