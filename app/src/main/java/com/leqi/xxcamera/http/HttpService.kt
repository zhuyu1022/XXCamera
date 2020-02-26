package com.leqi.lwcamera.http


import com.leqi.xxcamera.module.News
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 *  接口文档地址: https://gitea.leqi.us/id-photo-verify/IDPhotoVerifyPython/src/branch/master/README.md
 *  base url : https://api.id-photo-verify.com/api/V2/
 */

interface HttpService {

    //http://v.juhe.cn/toutiao/index?type=top&key=45f2c60398d9cf829dc84eb2f2122ce0

    @GET("index?type=top&key=45f2c60398d9cf829dc84eb2f2122ce0")
    suspend fun getNews(): News


}
