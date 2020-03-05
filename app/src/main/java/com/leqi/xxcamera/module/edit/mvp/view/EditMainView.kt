package com.leqi.xxcamera.module.edit.mvp.view

import com.leqi.baselib.base.BaseView
import com.leqi.lwcamera.model.bean.apiV2.ClothesBean
import com.leqi.lwcamera.model.bean.apiV2.ManufactureBean
import com.leqi.lwcamera.model.bean.apiV2.SpecsGroupResponse
import com.leqi.lwcamera.model.bean.apiV2.SpecsResponse
import com.leqi.xxcamera.model.bean.apiV2.BodyBackgroundResponse
import com.leqi.xxcamera.model.bean.apiV2.BodyPictureResponse
import com.leqi.xxcamera.model.bean.apiV2.BodySpecsResponse

/**
 * @Description:    类作用描述
 * @Author:         ZHUYU
 * @CreateDate:     2019/10/16$ 10:54$
 * @UpdateDate:     2019/10/16$ 10:54$
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface EditMainView : BaseView {

    /**
     * 获取规格
     */
    fun showSpecs(it: BodySpecsResponse)

    /**
     * 获取形象照背景色
     */
    fun showBackground(it: BodyBackgroundResponse)

    /**
     * 返回制作形象照
     */
    fun showPicture(it: BodyPictureResponse)


}