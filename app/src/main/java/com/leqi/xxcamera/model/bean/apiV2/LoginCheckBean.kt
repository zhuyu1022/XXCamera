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
class LoginCheckBean : BaseCode() , Serializable {


    var credential: String? = null  // 绑定凭据,当该账号已经绑定其他user_id,并且需要切换user_id为当前账号时,需要携带此凭据调用72号接口
    //var have_bind_user_id:Boolean = false // 该账号是否已经绑定其他user_id,当为false时credential参数和bind_user_info参数为null, 无意义,不可用
    var bind_user_info: UserIDBean? = null// 原微信账号绑定的用户信息,如果不绑定解绑,务必要将本地的user信息替换成此信息
}
