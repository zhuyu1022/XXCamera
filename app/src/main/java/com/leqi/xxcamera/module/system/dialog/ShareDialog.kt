package com.leqi.xxcamera.module.system.dialog

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ImageUtils
import com.leqi.baselib.baseDialog.BaseDialogFragment
import com.leqi.lwcamera.util.ShareUtil
import com.leqi.xxcamera.R
import kotlinx.android.synthetic.main.dialog_share.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 描述：分享
 * 作者：ZHUYU
 * 日期：2019/9/27 16:45
 */
class ShareDialog : BaseDialogFragment() {
    private var mPath: String? = null
    private var mImageUrl: String? = null
    private var mType: Int = 0

    companion object {
        fun newInstanceShareImageByPath(path: String): ShareDialog {
            val dialog = ShareDialog()
            val bundle = Bundle()
            bundle.putString("path", path)
            bundle.putInt("type", TYPE_IMAGE_PATH)
            dialog.arguments = bundle
            return dialog
        }

        fun newInstanceShareImageByUrl(url: String): ShareDialog {
            val dialog = ShareDialog()
            val bundle = Bundle()
            bundle.putString("url", url)
            bundle.putInt("type", TYPE_IMAGE_URL)
            dialog.arguments = bundle
            return dialog
        }

        fun newInstanceShareApp(): ShareDialog {
            val dialog = ShareDialog()
            val bundle = Bundle()
            bundle.putInt("type", TYPE_APP)
            dialog.arguments = bundle
            return dialog
        }

        //分享本地图片
        const val TYPE_IMAGE_PATH = 1
        //分享url图片
        const val TYPE_IMAGE_URL = 2
        //分享app
        const val TYPE_APP = 3

    }

    override fun isShowInBottom(): Boolean {
        return true
    }


    override fun initArguments(bundle: Bundle) {
        mImageUrl = bundle.getString("url")
        mPath = bundle.getString("path")
        mType = bundle.getInt("type")
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.dialog_share
    }

    override fun initViewAndEvent(view: View) {
        dialog?.setCanceledOnTouchOutside(true)
        //分享到QQ
        shareQQLayout.onClick {
            when (mType) {
                TYPE_APP -> ShareUtil.shareAppToQQ(activity!!)
                TYPE_IMAGE_PATH -> ShareUtil.shareImageToQQ(activity!!, ImageUtils.getBitmap(mPath))
                TYPE_IMAGE_URL -> ShareUtil.shareImageToQQ(activity!!, mImageUrl)
            }
            dismiss()
        }

        //分享给微信好友
        shareWechatLayout.onClick {
            when (mType) {
                TYPE_APP -> ShareUtil.shareAppToWechatFriend(activity!!)
                TYPE_IMAGE_PATH -> ShareUtil.shareImageToWechatFriend(activity!!, ImageUtils.getBitmap(mPath))
                TYPE_IMAGE_URL -> ShareUtil.shareImageToWechatFriend(activity!!, mImageUrl)
            }
            dismiss()
        }
        //分享到微信朋友圈
        sharePengyouLayout.onClick {
            when (mType) {
                TYPE_APP -> ShareUtil.shareAppToWechatFriendCircle(activity!!)
                TYPE_IMAGE_PATH -> ShareUtil.shareImageToWechatFriendCircle(activity!!, ImageUtils.getBitmap(mPath))
                TYPE_IMAGE_URL -> ShareUtil.shareImageToWechatFriendCircle(activity!!, mImageUrl)
            }
            dismiss()
        }
        cancelTv.onClick { dismiss() }


    }
}