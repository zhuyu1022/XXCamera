package com.leqi.xxcamera.module.system.dialog

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.leqi.baselib.baseDialog.BaseDialogFragment

import com.leqi.xxcamera.R


import kotlinx.android.synthetic.main.dialog_in_production.*

/**
 * @Description:    证件照制作中对话框
 * @Author:         ZHUYU
 * @CreateDate:     2019/10/16$ 10:13$
 * @UpdateDate:     2019/10/16$ 10:13$
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class InProductionDialog : BaseDialogFragment() {

    override fun isBackgroundTransparent(): Boolean {
        return true
    }

    var mTitle: String = ""
    override fun initArguments(bundle: Bundle) {
        mTitle = bundle.getString("title").toString()

    }

    companion object {
        fun newInstance(title: String): InProductionDialog {
            val dialog = InProductionDialog()
            val bundle = Bundle()
            bundle.putSerializable("title", title)
            dialog.arguments = bundle
            return dialog
        }
    }


    override fun getContentViewLayoutID(): Int {
        return R.layout.dialog_in_production

    }

    override fun initViewAndEvent(view: View) {
        dialog?.setCanceledOnTouchOutside(false)
        //加载攻略动图
//        Glide.with(this)
//                .load(R.drawable.in_production)
//                .into(inProuctionImg)
        if (!mTitle.isNullOrEmpty()) {
            titileTv.text = mTitle
        }

    }


}