package com.leqi.lwcamera.module.common.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.app.ActivityCompat
import com.leqi.baselib.R
import com.leqi.baselib.baseDialog.BaseDialogFragment
import kotlinx.android.synthetic.main.custom_dialog_layout.*

import org.jetbrains.anko.textColor


class CustomDialog : BaseDialogFragment() {


    private var title: String? = null
    private var message: String? = null
    private var rightMsg: String? = null
    private var leftMsg: String? = null
    private var customDialogListener: CustomDialogListener? = null

    companion object {

        fun instance(title: String, message: String, leftMsg: String, rightMsg: String): CustomDialog {
            val fragment = CustomDialog()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("message", message)
            bundle.putString("leftMsg", leftMsg)
            bundle.putString("rightMsg", rightMsg)
            fragment.arguments = bundle
            return fragment
        }


    }

    interface CustomDialogListener {
        fun onLeftBtnClick()
        fun onRightBtnClick()
    }

    fun setClickListener(customDialogListener: CustomDialogListener) {
        this.customDialogListener = customDialogListener
    }


    override fun initArguments(bundle: Bundle) {
        this.title = bundle.getString("title")
        this.message = bundle.getString("message")
        this.leftMsg = bundle.getString("leftMsg")
        this.rightMsg = bundle.getString("rightMsg")
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.custom_dialog_layout
    }

    override fun initViewAndEvent(view: View) {

        titleTv.text = title
        messageTV.text = message
        rightBtn.text = rightMsg
        leftBtn.text = leftMsg
        if (TextUtils.isEmpty(title)) {
            titleTv.visibility = View.GONE
        }
        if (TextUtils.isEmpty(message)) {
            messageTV.visibility = View.GONE
        }
        if (TextUtils.isEmpty(rightMsg)) {
            rightBtn.visibility = View.GONE
            lineCenter.visibility = View.GONE
        }
        if (TextUtils.isEmpty(leftMsg)) {
            leftBtn.visibility = View.GONE
            lineCenter.visibility = View.GONE
        }
        leftBtn.setOnClickListener {
            dismiss()
            customDialogListener?.onLeftBtnClick()
        }
        rightBtn.setOnClickListener {
            dismiss()
            customDialogListener?.onRightBtnClick()
        }
        if (isLeftRed) {
            leftBtn.textColor = ActivityCompat.getColor(activity!!, R.color.dialogWarningTextColor)
        }
        if (isRightRed) {
            rightBtn.textColor = ActivityCompat.getColor(activity!!, R.color.dialogWarningTextColor)
        }
    }


    var isLeftRed = false
    var isRightRed = false
    fun setLeftBtnRed() {
        isLeftRed = true
    }

    fun setRightBtnRed() {
        isRightRed = true
    }

}