package com.leqi.xxcamera.module.edit.mvp.presenter

import com.google.gson.JsonObject
import com.leqi.baselib.base.BaseKotlinPresenter
import com.leqi.lwcamera.config.Config

import com.leqi.xxcamera.api.HTTP_SUCCESS
import com.leqi.xxcamera.api.HttpFactory
import com.leqi.xxcamera.module.edit.mvp.view.EditMainView
import okhttp3.MediaType
import okhttp3.RequestBody


/**
 * @Description:    类作用描述
 * @Author:         ZHUYU
 * @CreateDate:     2019/10/16$ 10:55$
 * @UpdateDate:     2019/10/16$ 10:55$
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EditMainPresenter : BaseKotlinPresenter<EditMainView>() {
    /**
     * 形象照获取规格
     */
    fun getBodySpecs() {
        launchRequest(request = {
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
            mvpView?.onError("未检测到网络网络")
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
            mvpView?.onError("未检测到网络网络")
        })
    }



    /**
     * 制作 形象照返回无背景图片
     */
    fun makePicture(specId:Int,imageKey:String ?){
        mvpView?.showWaitDialog()
        launchRequest(request = {
            val jsonObject = JsonObject()
            jsonObject.addProperty("spec_id", specId)
            jsonObject.addProperty("key", imageKey)
            val requestBody = RequestBody.create(MediaType.parse(Config.JSON_TYPE), jsonObject.toString())
            HttpFactory.getInstance().service.getBodyPicture(requestBody)
        }, onSuccess = {
            if (HTTP_SUCCESS == it.code) {
                mvpView?.showPicture(it)
            }else{
                mvpView?.onError("${it.error}")
            }
        }, onFail = {
            mvpView?.onError(it.toString())
        }, onNetError = {
            mvpView?.onError("未检测到网络网络")
        })
    }


//
//    /**
//     * 制作
//     */
//    fun manufacture(originalKey: String?, selectSpecId: Int?, fairLevel: ManufactureRequestBean.FairLevel, clothes: String, customPrarms: CustomPrarms?, isOriginal: Boolean) {
//
//        val manufactureRequestBean = ManufactureRequestBean()
//        manufactureRequestBean.key = originalKey.toString()
//        manufactureRequestBean.spec_id = selectSpecId
//        manufactureRequestBean.fair_level = fairLevel
//        manufactureRequestBean.list_clothes_id = arrayListOf(clothes)
//        manufactureRequestBean.need_resize = false   //这里传false， 使得制作后的图片不需要按照真实规格像素返回，但key不可用来生成流水号
//        manufactureRequestBean.custom_params=customPrarms
//        val toJson = Gson().toJson(manufactureRequestBean)
//        val requestBody = RequestBody.create(MediaType.parse(Config.JSON_TYPE), toJson)
//        addDispose(HttpFactory.manufacture(requestBody, Consumer {
//            when (it.code == HTTP_SUCCESS) {
//                true -> {
//                    // 显示图片 保存制作信息
//                    if (isOriginal) {
//                        mvpView.saveManufactureInfoOriginal(it)
//                    } else {
//                        mvpView.saveManufactureInfo(it)
//                    }
//
//                }
//                false -> {
//                    mvpView.onError("${it.error}")
//                    if (it.code == 405) {
//                        //该图片不能制作该规格
//                        mvpView.onManufactureFail()
//                    }
//
//                }
//            }
//        }, Consumer {
//            mvpView.onError("照片制作失败,请稍后重试!")
//        }))
//    }

}