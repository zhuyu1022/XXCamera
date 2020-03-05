package com.leqi.baselib.net

import android.util.Log
import com.leqi.baselib.BuildConfig

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Provider
import java.util.concurrent.TimeUnit

/**
 * @Description:    Retrofit封装
 * @Author:         ZHUYU
 * @CreateDate:     2020/2/27 17:09
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2020/2/27 17:09
 * @Version:        1.0
 */
class HttpProvider {
    private val time: Long = 20000 //默认20s超时
    var baseurl: String = ""
    var appId: String = ""
    var userKey: String = ""
    var versionName: String = ""
    var market: String = ""



    fun setConfig(baseurl: String, appId: String, userKey: String, versionName: String, market: String) {
        this.baseurl = baseurl
        this.appId = appId
        this.userKey = userKey
        this.versionName = versionName
        this.market = market
        init()
    }
    fun setConfig(userKey: String) {
        this.userKey = userKey
        init()
    }


    /***
     * Okhttp日志拦截器
     */
    private val okHttpLogIntercept = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("HttpProvider", message)
        }

    }).apply { level = HttpLoggingInterceptor.Level.BODY }

    private fun okHttpClient(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(headersInterceptor())
        .connectTimeout(time, TimeUnit.MILLISECONDS)
        .readTimeout(time, TimeUnit.MILLISECONDS)
        .writeTimeout(time, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(BuildConfig.DEBUG)
        .addInterceptor(okHttpLogIntercept)
        .build()

    lateinit var retrofit: Retrofit
    lateinit var retrofitOSS: Retrofit


    fun init() {
        retrofit = Retrofit.Builder()
            .baseUrl(baseurl)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofitOSS = Retrofit.Builder()
            .baseUrl(baseurl)
            .client(okHttpClientOSS())
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    private fun okHttpClientOSS(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(time, TimeUnit.MILLISECONDS)
        .readTimeout(time, TimeUnit.MILLISECONDS)
        .writeTimeout(time, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(okHttpLogIntercept)
        .build()

    /**
     * 同意添加请求头
     */
    private fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain
                .request()
                .newBuilder()
                .addHeader("App-Key", appId)
                .addHeader("Client-Type", market)
                .addHeader("User-Key", userKey)
                .addHeader("Software-Version", versionName)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Connection", "close")
                .build()
        )
    }

    companion object {
        @Volatile
        private var INSTANCE: HttpProvider? = null

        fun getInstance(): HttpProvider = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HttpProvider().also { INSTANCE = it }
        }
    }


    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun <T> ossCreate(service: Class<T>): T {
        return retrofitOSS.create(service)
    }

}