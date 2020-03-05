package com.leqi.xxcamera.module.edit.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leqi.lwcamera.model.bean.apiV2.ProblemBean
import com.leqi.xxcamera.R
import com.leqi.xxcamera.model.bean.apiV2.BodySpecsResponse
import com.leqi.xxcamera.model.bean.apiV2.ModelsResponse
import org.jetbrains.anko.backgroundDrawable

/**
 * 描述：
 * 作者：ZHUYU
 * 日期：2019/9/27 14:02
 */
class SpecAdapter() : BaseQuickAdapter<BodySpecsResponse.BodySpecs, BaseViewHolder>
    (R.layout.item_spec_layout) {

    var mSelected: Int = -1
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: BodySpecsResponse.BodySpecs?) {

        val specNameTv = helper.getView<TextView>(R.id.specNameTv)
        val pxSizeTv = helper.getView<TextView>(R.id.pxSizeTv)
        specNameTv.text = item?.name
        pxSizeTv.text = "${item?.px_size?.get(0)}x${item?.px_size?.get(1)}"
        if (helper.adapterPosition == mSelected) {
            helper.itemView.backgroundDrawable =
                ContextCompat.getDrawable(mContext, R.drawable.bg_corner_spec_select)
            specNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent))
            pxSizeTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent))
        } else {
            helper.itemView.backgroundDrawable =
                ContextCompat.getDrawable(mContext, R.drawable.bg_corner_spec_unselect)
            specNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            pxSizeTv.setTextColor(ContextCompat.getColor(mContext, R.color.normalDarkTextColor))
        }

    }

    fun setCurrent(index: Int) {
        val lastSelected = mSelected
        mSelected = index
        notifyItemChanged(mSelected)
        notifyItemChanged(lastSelected)
    }

}