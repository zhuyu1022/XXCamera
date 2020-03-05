package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/1/8 - 13 : 23 
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/8 - 13 : 23 
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */
class SearchSpecIdBean : BaseCode() , Serializable {

    var result : SpecInfoBean? = null
    var crop_information : CropInformation? = null
   // 规格分组名称
    var group_name : String= ""
     /*用于计算相机页面规格框  位置大小*/
    class CropInformation : Serializable {
        var head_height : Int = 0  // 头部高度,客户端的传值,客户端需要根据实际高度与此值一起计算出比例值
        var height : Int = 0    // 在系统预设值下的裁剪框高度
        var width : Int = 0 // 在系统预设值下的裁剪框宽度
        var top : Int = 0   // 在系统预设值下的头顶部距离裁剪框的距离
    }


}
