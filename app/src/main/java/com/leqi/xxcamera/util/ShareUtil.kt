package com.leqi.lwcamera.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import com.blankj.utilcode.util.ToastUtils
import com.leqi.lwcamera.config.Config
import com.leqi.xxcamera.R

import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb


/**
 * Created by 84449 on 2018/2/5.
 *  分享
 */
object ShareUtil {
    /****************************************分享APP******************************************/
    /**
     * 分享APP到QQ
     */
    fun shareAppToQQ(context: Context) {
        shareApp(context, SHARE_MEDIA.QQ)
    }

    /**
     * 分享APP给微信好友
     */
    fun shareAppToWechatFriend(context: Context) {
        shareApp(context, SHARE_MEDIA.WEIXIN)
    }

    /**
     * 分享APP到微信朋友圈
     */
    fun shareAppToWechatFriendCircle(context: Context) {
        shareApp(context, SHARE_MEDIA.WEIXIN_CIRCLE)
    }
    /****************************************分享图片 （bitmap）******************************************/
    /**
     * 分享图片给QQ
     */
    fun shareImageToQQ(context: Context, bitmap: Bitmap) {
        shareImage(context, bitmap, SHARE_MEDIA.QQ)
    }

    /**
     * 分享图片给微信好友
     */
    fun shareImageToWechatFriend(context: Context, bitmap: Bitmap) {
        shareImage(context, bitmap, SHARE_MEDIA.WEIXIN)
    }

    /**
     * 分享图片到朋友圈
     */
    fun shareImageToWechatFriendCircle(context: Context, bitmap: Bitmap) {
        shareImage(context, bitmap, SHARE_MEDIA.WEIXIN_CIRCLE)
    }


    /****************************************分享图片 （url）******************************************/
    /**
     * 分享图片给QQ
     */
    fun shareImageToQQ(context: Context, url: String?) {
        shareImage(context, url, SHARE_MEDIA.QQ)
    }


    /**
     * 分享图片到朋友圈
     */
    fun shareImageToWechatFriendCircle(context: Context, url: String?) {
        shareImage(context, url, SHARE_MEDIA.WEIXIN_CIRCLE)
    }

    /**
     * 分享图片给微信好友
     */
    fun shareImageToWechatFriend(context: Context, url: String?) {
        shareImage(context, url, SHARE_MEDIA.WEIXIN)
    }


    /**
     * 判断是否安装微信
     */
    private fun isInstallWX(context: Context): Boolean {
        if (!APKUtil.isInstallAPP(context.applicationContext, 1)) {
            ToastUtils.showShort("未安装微信")
            return false
        }
        return true
    }

    /**
     * 分享app
     */
    private fun shareApp(context: Context, type: SHARE_MEDIA) {
        if (isInstallWX(context)) {
            val web = UMWeb(Config.SHARE_URL)
            web.title = "我正在使用【长宽快照】一款人性化的多功能证件照拍摄软件"//标题
            web.setThumb(UMImage(context, R.drawable.share_logo))  //缩略图
            web.description = "您的私人证件照形象摄影师"//描述
            ShareAction(context as Activity?)
                    .setPlatform(type)//传入平台
                    .withMedia(web)
                    .share()
        }
    }

    /**
     * 分享图片
     */
    private fun shareImage(context: Context, bitmap: Bitmap, type: SHARE_MEDIA) {
        if (isInstallWX(context)) {
            val image = UMImage(context, bitmap)
            val thumb = UMImage(context, bitmap)//缩略图
            image.setThumb(thumb)
            ShareAction(context as Activity?)
                    .setPlatform(type)//传入平台
                    .withMedia(image)
                    .share()
        }
    }

    /**
     * 分享图片
     */
    private fun shareImage(context: Context, url: String?, type: SHARE_MEDIA) {
        if (isInstallWX(context)) {
            val image = UMImage(context, url)
            val thumb = UMImage(context, url)//缩略图
            image.setThumb(thumb)
            ShareAction(context as Activity?)
                    .setPlatform(type)//传入平台
                    .withMedia(image)
                    .share()
        }
    }


}
