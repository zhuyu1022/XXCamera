package com.leqi.xxcamera.module.system.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leqi.lwcamera.model.bean.apiV2.ProblemBean
import com.leqi.xxcamera.R

/**
 * 描述：
 * 作者：ZHUYU
 * 日期：2019/9/27 14:02
 */
class ContactQuestionAdapter(data: MutableList<ProblemBean.ProblemBean>) : BaseQuickAdapter<ProblemBean.ProblemBean, BaseViewHolder>
(R.layout.item_question, data) {
    override fun convert(helper: BaseViewHolder, item: ProblemBean.ProblemBean?) {
        val title = helper.getView<LinearLayout>(R.id.ll_question_title)
        val message = helper.getView<TextView>(R.id.tv_question_message)
        val icon = helper.getView<ImageView>(R.id.iv_question_more)
        helper.setText(R.id.tv_question_title, item?.title)
        message.text = item?.content

        title.setOnClickListener {
            if (message.isVisible) {
                message.visibility = View.GONE
                icon.setImageResource(R.mipmap.item_question_open)
            } else {
                message.visibility = View.VISIBLE
                icon.setImageResource(R.mipmap.item_question_close)
            }
        }
    }
}