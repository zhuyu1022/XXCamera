package com.leqi.xxcamera.module.system.mvp.view

import com.leqi.baselib.base.BaseView
import com.leqi.lwcamera.model.bean.apiV2.PhoneNumberBean
import com.leqi.lwcamera.model.bean.apiV2.ProblemBean
import java.util.ArrayList

interface CustomerView:BaseView {
    fun showPhoneNumber(phoneNumberBean: PhoneNumberBean)

    fun showQuestionList(listCache: ArrayList<ProblemBean.ProblemBean>)
}