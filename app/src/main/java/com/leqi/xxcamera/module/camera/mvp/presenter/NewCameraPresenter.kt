package com.leqi.lwcamera.module.shoot.mvp.presenter

import android.util.Log
import com.blankj.utilcode.util.*
import com.google.gson.JsonObject
import com.huantansheng.easyphotos.utils.bitmap.BitmapUtils
import com.leqi.baselib.base.BaseKotlinPresenter
import com.leqi.baselib.base.BasePresenter
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.module.shoot.mvp.view.NewCameraView
import com.leqi.lwcamera.util.BitmapUtil
import com.leqi.lwcamera.util.FileTool
import com.leqi.xxcamera.api.HTTP_SUCCESS
import com.leqi.xxcamera.api.HttpFactory
import com.leqi.xxcamera.model.bean.apiV2.BodySpecsResponse
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.net.UnknownHostException


class NewCameraPresenter : BaseKotlinPresenter<NewCameraView>() {


    var mSpecId: String? = null
    /**
     * 获取模特图
     */
    fun getModels() {
        launchRequest(request = {
            HttpFactory.getInstance().service.getModels()
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                mvpView?.showModels(it)
            } else {
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError(it.toString())
        }, onNetError = {
            mvpView?.onError("未检测到网络网络")
        })
    }


    fun startMakePicture(file: String, specId: String?) {
        mSpecId = specId
        launch {
            if (specId.isNullOrEmpty()) {
                LogUtils.d("规格为空，先去请求规格")
                // mSpecId =  HttpFactory.getInstance().service.getBodySpecs()?.result?.get(0)?.spec_id.toString()
               // mSpecId = getBodySpecs()?.result?.get(0)?.spec_id.toString()
               // LogUtils.d("mSpecId:${mSpecId}")

               withContext(Dispatchers.IO){
                        val tt = getBodySpecs()?.result?.get(0)?.spec_id.toString()
                        LogUtils.d("mSpecId:${tt}")
                        delay(2000)

           }
            }
            ToastUtils.showShort("哈哈")
            LogUtils.d("去上传图片")
            //getUploadAddress(file)
        }

    }


    /**
     * 1、获取上传地址
     */
    fun getUploadAddress(file: String) {

        launchRequest(request = {
            HttpFactory.getInstance().service.upOriginOSS()
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                upOriginalImage(file, it.key, it.url)
            } else {
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError("请求服务器失败---请稍后重试~~")
        }, onNetError = {
            mvpView?.onError("未检测到网络网络")
        })
    }

    /**
     * 2、上传图片
     */
    private fun upOriginalImage(file: String, imageKey: String, imageUrl: String) {
        LogUtils.d("file:$file,imageKey：：： $imageKey--------imageUrl：：： $imageUrl")


        launchRequest(request = {
            //           val bytes= FileIOUtils.readFile2BytesByStream(file)
//            val responseBody = RequestBody.create(null, bytes)
            val fileName = FileTool.bitmapToBase64(ImageUtils.getBitmap(file))
            val base64ToBitmap = FileTool.base64ToBitmap(fileName)
            val byteArray = BitmapUtil.bitmap2Bytes(base64ToBitmap)
            val responseBody = RequestBody.create(null, byteArray)
            HttpFactory.getInstance().serviceOSS.upOSS(imageUrl, responseBody)

        }, onSuccess = {
            if (HTTP_SUCCESS == it.code()) {
                LogUtils.d("上传图片成功")
                getPicture(imageKey)
            } else {
                mvpView?.onError("${it.errorBody()}")
            }
        }, onFail = {
            mvpView?.onError(it.message.toString())
            //mvpView?.onError("上传图片失败--请稍后重试~~")
        }, onNetError = {
            mvpView?.onError("未检测到网络网络")
        })
    }


    /**
     * 形象照返回无背景图片
     */
    fun getPicture(imageKey: String) {
        launchRequest(request = {
            val jsonObject = JsonObject()
            jsonObject.addProperty("spec_id", mSpecId)
            jsonObject.addProperty("key", imageKey)
            val requestBody =
                RequestBody.create(MediaType.parse(Config.JSON_TYPE), jsonObject.toString())
            HttpFactory.getInstance().service.getBodyPicture(requestBody)
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                mvpView?.showBodyPicture(it)
            } else {
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError(it.toString())
        }, onNetError = {
            mvpView?.onError("未检测到网络")
        })
    }

    /**
     * 形象照获取规格
     */
    fun getBodySpecs(): BodySpecsResponse? {
        return launchRequest(request = {
            HttpFactory.getInstance().service.getBodySpecs()
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                mvpView?.showSpecs(it)
            } else {
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError(it.toString())
        }, onNetError = {
            mvpView?.onError("未检测到网络")
        })
    }

    /**
     * 获取背景色
     */
    fun getBodyBackground() {
        launchRequest(request = {
            HttpFactory.getInstance().service.getBodyBackground()
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                mvpView?.showBackground(it)
            } else {
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError(it.toString())
        }, onNetError = {
            mvpView?.onError("未检测到网络")
        })
    }


}