package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/**
 * @Description:    类作用描述
 * @Author:         ZHUYU
 * @CreateDate:     2019/12/19 13:41
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2019/12/19 13:41
 * @Version:        1.0
 */
class LoginInfoBean : BaseCode(), Serializable {
    var result: LoginInfo? = null

    class LoginInfo : Serializable{
        var head_url: String = ""  //"https://xxx", // 头像地址
        var nickname: String = ""  // 昵称
        var coupon_total: Int = 0  // 优惠卷总额(单位:分)
        var message_card: Int = 0 //留言卡片总数
        var whether_bind_account=false   //当前用户是否绑定了账号

    }


}
