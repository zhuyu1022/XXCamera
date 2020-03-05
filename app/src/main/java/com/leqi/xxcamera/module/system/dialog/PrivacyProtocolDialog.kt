package com.leqi.xxcamera.module.system.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.blankj.utilcode.util.SpanUtils

import com.leqi.baselib.baseDialog.BaseDialogFragment
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.util.IntentUtil
import com.leqi.xxcamera.R
import kotlinx.android.synthetic.main.dialog_privacy_protocol.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PrivacyProtocolDialog : BaseDialogFragment() {
    companion object {
        fun newInstance(): PrivacyProtocolDialog {
            val dialog = PrivacyProtocolDialog()
            val bundle = Bundle()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun initArguments(bundle: Bundle) {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.dialog_privacy_protocol

    }

    override fun initViewAndEvent(view: View) {

        SpanUtils.with(messageTV)
                .append("欢迎使用长宽快照！我们非常重视您的隐私及个人信息保护。所以在您使用长宽快照之前，请仔细阅读我们的")
                .append("《隐私政策》").setClickSpan(privacyProtocolClickListener)
                .append("和")
                .append("《用户协议》").setClickSpan(userProtocolClickListener)
                .append("。在您同意并接受全部条款后方可开始体验长宽快照，感谢您的信任。")
                .create()
        agreeBtn.onClick { mListener?.onAgreeClick() }
        disagreeBtn.onClick { mListener?.onDisagreeClick() }

    }


    /**
     * 隐私协议点击事件
     */
    private val privacyProtocolClickListener = object : ClickableSpan() {
        override fun onClick(widget: View) {
            IntentUtil.intentWeb(Config.PRIVACY_URL, activity!!)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = Color.BLUE
            ds.isUnderlineText = false
        }
    }
    /**
     * 用户协议点击事件
     */
    private val userProtocolClickListener = object : ClickableSpan() {
        override fun onClick(widget: View) {
            IntentUtil.intentWeb(Config.USER_PROTOCOL_URL, activity!!)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = Color.BLUE
            ds.isUnderlineText = false
        }
    }


    interface ClickListener {
        fun onDisagreeClick()
        fun onAgreeClick()
    }

    private var mListener: ClickListener? = null
    fun setClickListener(listener: ClickListener) {
        this.mListener = listener
    }


}