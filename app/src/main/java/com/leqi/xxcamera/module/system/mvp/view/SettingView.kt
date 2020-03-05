package com.leqi.lwcamera.module.home.mvp.view

import com.leqi.baselib.base.BaseView
import com.leqi.lwcamera.model.bean.apiV2.VersionBean

interface SettingView:BaseView {
    fun showNewVersion(versionBean:VersionBean)

    fun showGuide(url:String )


}