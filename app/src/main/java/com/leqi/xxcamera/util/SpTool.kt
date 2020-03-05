package com.leqi.lwcamera.util

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/1/12 - 17 : 06
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/12 - 17 : 06
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 *  sp
 */
class SpTool(private val context : Context) {

    val composingInfo : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(COMPOSING_INFO_ , Context.MODE_PRIVATE)
            return sp.getString(COMPOSING_INFO_ , "")!!
        }

    @Synchronized
    fun putComposingInfo(composingInfo : String) {
        val sp = context.getSharedPreferences(COMPOSING_INFO_ , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(COMPOSING_INFO_ , composingInfo).apply()
    }

    val phoneNumber : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(PHONE_NUMBER , Context.MODE_PRIVATE)
            return sp.getString(PHONE_NUMBER , "18012485542")!!
        }

    val printPhoneNumber : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(PRINT_PHONE_NUMBER , Context.MODE_PRIVATE)
            return sp.getString(PRINT_PHONE_NUMBER , "18020506879")!!
        }

    @Synchronized
    fun putPhoneNumber(number : String) {
        val sp = context.getSharedPreferences(PHONE_NUMBER , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString("number" , number).apply()
    }

    @Synchronized
    fun putPrintPhoneNumber(number : String) {
        val sp = context.getSharedPreferences(PRINT_PHONE_NUMBER , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString("number" , number).apply()
    }

    //默认1 开启
    val searchDialog : Int
        @Synchronized get() {
            val sp = context.getSharedPreferences(SEARCH_DIALOG , Context.MODE_PRIVATE)
            return sp.getInt(SEARCH_DIALOG , 1)
        }

    @Synchronized
    fun setSearchDialog() {
        val sp = context.getSharedPreferences(SEARCH_DIALOG , Context.MODE_PRIVATE)
        sp.edit().putInt("search_dialog_switch" , 0).apply() //关闭
    }

    // 获取缓存的搜索词
    var cacheSearchWord : List<String>
        @Synchronized get() {
            val sp = context.getSharedPreferences(SP_CACHE_NAME , Context.MODE_PRIVATE)
            val lists = sp.getString(CACHE_SEARCH_WORD , null)
            LogUtils.d("cacheList:$lists")
            return if (lists == null || lists.isEmpty()) {
                ArrayList()
            } else {
                Gson().fromJson<List<String>>(lists , object : TypeToken<List<String>>() {}.type)
            }
        }
        @Synchronized set(lists) {
            val sp = context.getSharedPreferences(SP_CACHE_NAME , Context.MODE_PRIVATE)
            val cacheWords =
                    Gson().toJson(lists)
            LogUtils.d("cacheWords:$cacheWords")
            val ed = sp.edit()
            ed.putString(CACHE_SEARCH_WORD , cacheWords.toString())
            ed.apply()
        }

    val getUserID : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(USERID , Context.MODE_PRIVATE)
            return sp.getString(USERID , "")!!
        }

    @Synchronized
    fun putUserID(key : String) {
        val sp = context.getSharedPreferences(USERID , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(USERID , key).apply()
    }

    val getUserKey : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(USERKEY , Context.MODE_PRIVATE)
            return sp.getString(USERKEY , "")!!
        }

    @Synchronized
    fun putUserKey(key : String) {
        val sp = context.getSharedPreferences(USERKEY , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(USERKEY , key).apply()
    }


    val getBeauty : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(BEAUTY , Context.MODE_PRIVATE)
            return sp.getString(BEAUTY , "")!!
        }

    @Synchronized
    fun putBeauty(key : String) {
        val sp = context.getSharedPreferences(BEAUTY , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(BEAUTY , key).apply()
    }

    val getUnBeauty : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(UNBEAUTY , Context.MODE_PRIVATE)
            return sp.getString(UNBEAUTY , "")!!
        }

    @Synchronized
    fun putUnBeauty(key : String) {
        val sp = context.getSharedPreferences(UNBEAUTY , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(UNBEAUTY , key).apply()
    }

    val getFailedFile : String
        @Synchronized get() {
            val sp = context.getSharedPreferences(FAILEDFILE , Context.MODE_PRIVATE)
            return sp.getString(FAILEDFILE , "")!!
        }

    @Synchronized
    fun putFailedFile(key : String) {
        val sp = context.getSharedPreferences(FAILEDFILE , Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(FAILEDFILE , key).apply()
    }

    companion object {
        private const val SP_CACHE_NAME = "ComplianceDetection"
        private const val CACHE_SEARCH_WORD = "cache_search_word"
        private const val COMPOSING_INFO_ = "composingInfo"
        private const val SEARCH_DIALOG = "search_dialog"
        private const val PHONE_NUMBER = "phone_number"
        private const val PRINT_PHONE_NUMBER = "Print_phone_number"
        private const val PREVIEW_TIP_CACHE = "preview_tip_cache"
        private const val USERID = "user_id"
        private const val USERKEY = "user_key"
        private const val BEAUTY = "beauty_bitmap"
        private const val UNBEAUTY = "unbeauty_bitmap"
        private const val FAILEDFILE = "FailedFile"

    }
}
