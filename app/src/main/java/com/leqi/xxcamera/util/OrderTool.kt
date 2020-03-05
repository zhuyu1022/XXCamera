//package com.leqi.lwcamera.util
//
//import android.content.ClipData
//import android.content.ClipboardManager
//import android.content.Context
//import android.content.Intent
//import android.graphics.Typeface.BOLD_ITALIC
//import android.net.Uri
//import android.os.Build
//import android.os.VibrationEffect
//import android.os.Vibrator
//import android.text.Spannable
//import android.text.SpannableStringBuilder
//import android.text.style.StyleSpan
//import androidx.appcompat.app.AppCompatActivity
//import com.blankj.utilcode.util.ToastUtils
//import com.google.gson.Gson
//import com.google.gson.JsonObject
//import com.leqi.lwcamera.BuildConfig
//import com.leqi.lwcamera.config.Config
//import com.leqi.lwcamera.http.HTTP_SUCCESS
//import com.leqi.lwcamera.http.HttpFactory
//import com.leqi.lwcamera.model.bean.apiV2.InfoOrderEle
//import com.leqi.lwcamera.model.bean.apiV2.PlatformRequestBean
//import com.leqi.lwcamera.module.order.activity.H5PrintWebPageActivity
//import com.leqi.lwcamera.module.order.dialog.SaveDialog
//import com.leqi.lwcamera.module.order.dialog.SaveSuccessDialog
//import io.reactivex.Observable
//import io.reactivex.Observer
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.disposables.Disposable
//import io.reactivex.functions.Consumer
//import io.reactivex.schedulers.Schedulers
//import okhttp3.MediaType
//import okhttp3.RequestBody
//
///**
// * @Author: zhuxiaoyao
// * @Date: 2019/7/10 - 14 : 40
// * @LastEditors: zhuxiaoyao
// * @LastEditTime: 2019/7/10 - 14 : 40
// * @Description: institute-Android
// * @Email: zhuxs@venpoo.com
// */
//object OrderTool {
//
//    /**
//     * 下载证件照
//     */
//    fun saveIdPhoto(orderInfo: InfoOrderEle, context: Context) {
//        var allSave = true
//        val urlArray: ArrayList<String> = ArrayList()
//        urlArray.addAll(orderInfo.url)
//        if (orderInfo.is_print) {
//            urlArray.addAll(orderInfo.url_print!!)
//        }
//        Observable.fromIterable(urlArray).map { s ->
//            val execute = HttpFactory.providerOSS.downloadIdPhoto(s).execute()
//            FileTool.writeResponseBody2Disk(execute.body(), System.currentTimeMillis().toString() + orderInfo.order_id + ".jpg", context)
//        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Boolean> {
//            override fun onSubscribe(d: Disposable) {
//                ToastUtils.showShort("正在下载证件照...")
//            }
//
//            override fun onNext(value: Boolean) {
//                if (!value) {
//                    allSave = value
//                    ToastUtils.showShort("部分照片保存失败! 请稍候重试！")
//                }
//            }
//
//            override fun onError(e: Throwable) {
//                ToastUtils.showShort("部分照片保存失败! 请稍候重试！$e")
//            }
//
//            override fun onComplete() {
//                if (allSave) {
//                    ToastUtils.showLong(getFormattedMessage())
//                    val saveSuccessDialog=SaveSuccessDialog.newInstance()
//                    saveSuccessDialog.show((context as AppCompatActivity) .supportFragmentManager,"saveSuccessDialog")
//                } else {
//                    ToastUtils.showLong("全部照片保存失败! \n请到系统设置检查应用储存权限！")
//                }
//            }
//        })
//    }
//
//    private fun getFormattedMessage(): CharSequence {
//        val prefix = "证件照已保存~\n" + "请到"
//        val highlight = "相册里(其他相册/不常用图集)找到「" + Config.APP_NAME + "」的文件夹"
//        val suffix = "里查找证件照哦~~"
//        val ssb = SpannableStringBuilder(prefix).append(highlight).append(suffix)
//        val prefixLen = prefix.length
//        ssb.setSpan(StyleSpan(BOLD_ITALIC), prefixLen, prefixLen + highlight.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        return ssb
//    }
//
//    /**
//     * 发送到邮箱
//     */
//    fun sendEleOrderEmail(context:Context ,orderId: String, mail: String, mailTitle: String, fileName: String, content: String) {
//        val jsonObject = JsonObject()
//        jsonObject.addProperty("is_print", false)
//        jsonObject.addProperty("user_email", mail)
//        jsonObject.addProperty("filename", fileName)
//        val customData = JsonObject()
//        customData.addProperty("title", mailTitle)
//        customData.addProperty("content", content)
//        jsonObject.add("custom_data", customData)
//        val requestBody = RequestBody.create(MediaType.parse(Config.JSON_TYPE), jsonObject.toString())
//        CompositeDisposable().add(HttpFactory.email(orderId, requestBody, Consumer {
//            when (it.code == HTTP_SUCCESS) {
//                true -> {
//                    ToastUtils.showShort("发送成功~请到邮箱查看~~")
//                    val saveSuccessDialog=SaveSuccessDialog.newInstance()
//                    saveSuccessDialog.show((context as AppCompatActivity) .supportFragmentManager,"saveSuccessDialog")
//                }
//                false -> {
//                    ToastUtils.showShort("${it.error}")
//                }
//            }
//        }, Consumer {
//            ToastUtils.showShort("发送失败~请稍后重试~~")
//        }))
//
//    }
//
//    fun copyOrderID(orderId: String, context: Context) {
//        var cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        val clipData = ClipData.newPlainText("订单号", orderId)
//        cm.setPrimaryClip(clipData)
//        val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
//        } else {
//            @Suppress("DEPRECATION") vib.vibrate(500)
//        }
//        ToastUtils.showShort("您的订单编号:$orderId")
//    }
//
//    /**
//     * 去冲印订单H5页面
//     */
//    fun goPrintH5(serialNumber: String, isOn: Boolean, backNumber: Int, context: Context) {
//        val platformRequestBean = PlatformRequestBean()
//        platformRequestBean.serial_number = serialNumber
//        platformRequestBean.back_number = backNumber
//        platformRequestBean.is_fair = isOn
//        val toJson = Gson().toJson(platformRequestBean)
//        val requestBody = RequestBody.create(MediaType.parse(Config.JSON_TYPE), toJson)
//        HttpFactory.printPlatform(requestBody, Consumer {
//            when (HTTP_SUCCESS == it.code) {
//                true -> {
//                    val buildUpon = Uri.parse(BuildConfig.PRINT_URL).buildUpon()
//                    buildUpon.appendQueryParameter("image_url", it.image_url)
//                    buildUpon.appendQueryParameter("spec_name", it.spec_name)
//                    buildUpon.appendQueryParameter("sheets_number", it.unit_per_printing.toString())
//                    buildUpon.appendQueryParameter("token", "Bearer ${Config.printPlatformId}:${Config.USERKEY}")
//                    if (APKUtil.isInstallAPP(context, 1)) {
//                        buildUpon.appendQueryParameter("payment", "wechat,alipay")
//                    } else {
//                        buildUpon.appendQueryParameter("payment", "alipay")
//                    }
//                    val intent = Intent(context, H5PrintWebPageActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    intent.putExtra("url", buildUpon.toString())
//                    intent.putExtra("serial_number", serialNumber)
//                    intent.putExtra("back_number", backNumber)
//                    intent.putExtra("is_fair", isOn)
//                    intent.putExtra("UserAgent", " app/leqiApp")
//                    context.startActivity(intent)
//                }
//                false -> {
//                    ToastUtils.showLong("${it.error}")
//                }
//            }
//        }, Consumer {
//            ToastUtils.showLong("上传图片失败 请稍后重试~~~${it.message}")
//        })
//    }
//
//
//    /**
//     *  去电子订单支付
//     */
////    fun eleOrder2Pay(orderInfo: InfoOrderEle, context: Context) {
////        val intent = Intent(context, PayEleOrderActivity::class.java)
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////        intent.putExtra("fee", orderInfo.fee)
////        intent.putExtra("orderId", orderInfo.order_id)
////        intent.putExtra("fromWhere", "Local")
////        context.startActivity(intent)
////    }
//
//    fun fileSize(fileSize: ArrayList<String?>): String {
//        var max: Int? = null
//        var min: Int? = null
//        if (fileSize[0] != null) {
//            min = Integer.valueOf(fileSize[0] ?: "0") / 1024
//        }
//        if (fileSize[1] != null) {
//            max = Integer.valueOf(fileSize[1] ?: "0") / 1024
//        }
//        if (max == null && min == null) {
//            return "无要求"
//        }
//        return when {
//            max == null -> "不小于" + min + "KB"
//            min == null -> "不大于" + max + "KB"
//            else -> min.toString() + "～" + max + "KB"
//        }
//    }
//
//
//}
