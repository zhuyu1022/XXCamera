package com.leqi.lwcamera.util

import android.app.Activity
import android.util.Log
import com.blankj.utilcode.util.LogUtils

import java.util.*

/**
 * Created by YqopY on 2016/12/27.
 */

object ActivityCollector {
    private val activityList = ArrayList<Activity>()

    val topActivity: Activity?
        get() = if (activityList.isEmpty()) {
            null
        } else {
            activityList[activityList.size - 1]
        }

    fun addActivity(activity: Activity) {
        LogUtils.e("addActivity:$activity")
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        LogUtils.e("removeActivity:$activity")
        activityList.remove(activity)
    }

    fun removeAllActivity() {
        Log.e("size", activityList.size.toString() + "")
        for (i in activityList.indices) {
            Log.e("removeActivity", activityList[i].toString() + "")
            val activity = activityList[i]
            activity.finish()
        }
        activityList.clear()
    }

    fun go2mainActivity() {
        for (i in activityList.indices) {
            Log.e("removeActivity", activityList[i].toString() + "")
            if (i != 0) {
                val activity = activityList[i]
                activity.finish()
            }
        }
    }




}
