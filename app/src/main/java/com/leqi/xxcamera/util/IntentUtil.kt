package com.leqi.lwcamera.util

import android.content.Context
import android.content.Intent
import com.leqi.lwcamera.module.common.activity.WebPageActivity


/**
 * Created by 84449 on 2018/2/5.
 *  加载网页
 */
object IntentUtil {

    fun intentWeb(url : String , context : Context) {
        val intent = Intent(context , WebPageActivity::class.java)
        intent.putExtra("url" , url)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }
}
