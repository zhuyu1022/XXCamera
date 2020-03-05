package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/9 - 09 : 51 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/9 - 09 : 51 
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class OrderListInfoEleBean : BaseCode() , Serializable {

    var order_list_final_ordered : List<InfoOrderEle>? = null

}