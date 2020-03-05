package com.leqi.lwcamera.model.bean.apiV2

import java.io.Serializable

class ProblemBean : BaseCode() , Serializable {

    var result : ArrayList<ProblemBean> = ArrayList()

    class ProblemBean : Serializable {
        var title : String = ""
        var content : String = ""
    }

}

 