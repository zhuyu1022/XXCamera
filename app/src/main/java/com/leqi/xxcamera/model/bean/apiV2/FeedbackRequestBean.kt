package com.leqi.lwcamera.model.bean.apiV2

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/6/25 - 15 : 09
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/6/25 - 15 : 09
 * @Description: institute_feature
 * @Email: zhuxs@venpoo.com
 */
class FeedbackRequestBean {

    var title: String? = null
    var feed_type: Int = 0 //反馈类型 0为APP的bug信息提交 1为用户的反馈信息(此类别将会把反馈内容发送到管理人员邮箱中
    var content: String = ""
    var user_contact: String? = null
    var device_model: String? = null
    var system_version: String? = null
    var images: List<String>? = null
}