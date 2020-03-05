package com.leqi.xxcamera.api

import com.leqi.baselib.net.HttpProvider
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.http.HttpService
import com.leqi.lwcamera.model.bean.apiV2.MessageCardBean
import com.leqi.lwcamera.model.bean.apiV2.*
import com.leqi.xxcamera.BuildConfig
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/1/22 - 17 : 06
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/1/12 - 17 : 06
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 */

const val HTTP_SUCCESS = 200
class HttpFactory {

    companion object {
        @Volatile
        private var INSTANCE: HttpFactory? = null

        fun getInstance(): HttpFactory = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HttpFactory().also { INSTANCE = it

            }
        }
    }
    init {
   }
     val service = HttpProvider.getInstance().create(HttpService::class.java)
    val serviceOSS = HttpProvider.getInstance().ossCreate(HttpService::class.java)


}
