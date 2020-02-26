package com.leqi.lwcamera.config

import android.os.Environment


/**
 * 全部配置类
 */
object Config {
    const val APP_NAME = "长宽相机"
    const val BASE_URL = "https://api.id-photo-verify.com/api/V2/"
    //长宽相机的APP_KEY
    const val APP_ID = "CHANGKUAN_PHOTO"
    //研究院
    // const val APP_ID = "ID_PHOTO_VERIFY"
    var USERKEY = ""
    //电子照价格  默认 *
    var electronic = 300
    //裁剪价格
    var cropPrice = 190
    //换背景价格
    var replacementPrice = 150
    var replacement2cropPrice = 300
    var homeSwitch = false
    var homeWindowTitle = ""
    var homeWindowText = ""
    var printPlatformId = -1
    var multiplePrice = 490
    var changeClothePrice = 0
    var changeClothePrintPrice = 0
    var changeClotheMultiBackgroundPrice: Int = 0
    var showClothe = true
   // var defaultBeautyLevel = ManufactureRequestBean.FairLevel()
    var serverBeautyVersion = 1000
    var clientBeautyVersion = 1000
    var onLineBeauty = true

    //根文件夹
    private val BASE_DIR = Environment.getExternalStorageDirectory()!!
    //根目录名称
    val BASE_FOLDER_NAME = "$BASE_DIR/$APP_NAME"
    //友盟Appkey
    const val UMENG_APPKEY = "5d9c318e4ca357ff9f000033"
    //网易七鱼appkey
    const val WANGYI_QIYU = "90d344f50c3d6ac29e60360bae8e8639"
    const val FAQID: Long = 2888530L
    //微信支付key
    const val WX_APP_ID = "wxeb6082ed7c525156"
    const val WX_APP_SECRECT = "d9ea86c92d8056605e70010d2da2d2a8"
    //QQ key
    const val QQ_APP_ID = "1110060202"

    const val PAY_RULE_URL = "http://www.id-photo-verify.com/payrule"
    const val JSON_TYPE = "application/json; charset=utf-8"

    const val imgWechat = "https://leqi-app.oss-cn-shanghai.aliyuncs.com/IDPhotoApp/%E5%88%86%E4%BA%AB%E5%9B%BE-%E5%BE%AE%E4%BF%A1.jpg"
    const val imgWeibo = "https://leqi-app.oss-cn-shanghai.aliyuncs.com/IDPhotoApp/%E5%88%86%E4%BA%AB%E5%9B%BE-%E5%BE%AE%E5%8D%9A.jpg"
    //隐私协议
    const val PRIVACY_URL = "https://id-photo-verify.com/privacy-ckkz/"
    //用户协议
    const val USER_PROTOCOL_URL = "http://www.id-photo-verify.com/treaty/"
    //证照信息协议certificate
    const val CERTIFICATE_URL = "https://id-photo-verify.com/leqi/"
    //拍摄攻略
    const val SHOOT_GUIDE_URL = "https://id-photo-verify.com/app-tutorial/?app=deep_blue"
    //分享给朋友的分享链接
    const val SHARE_URL = "http://www.id-photo-verify.com/gaojq/"

    var phoneNumber: String = "18012485542"
    var printNumber: String = "18020506879"
    var weChatId: String = "idphoto2017"

}
