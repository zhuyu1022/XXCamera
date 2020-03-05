package com.leqi.xxcamera.module.camera.adapter

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
import com.leqi.xxcamera.model.bean.apiV2.ModelsResponse

/**
 * 描述：
 * 作者：ZHUYU
 * 日期：2019/9/27 14:02
 */
class ModelAdapter() : BaseQuickAdapter<ModelsResponse.ModelGroup.Model, BaseViewHolder>
(R.layout.item_model_layout) {

    var mSelected: Int = -1
    override fun convert(helper: BaseViewHolder, item: ModelsResponse.ModelGroup.Model?) {
        val icon = helper.getView<ImageView>(R.id.modelImg)
        val cardView = helper.getView<CardView>(R.id.itemCardView)
        Glide.with(mContext).load(item?.model_url).placeholder(icon.drawable).into(icon)

        if(helper.adapterPosition==mSelected){
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.colorAccent))
        }else{
            cardView.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.colorTransparent))
        }

    }

    fun setCurrent(index: Int) {
        val lastSelected = mSelected
        mSelected = index
        notifyItemChanged(mSelected)
        notifyItemChanged(lastSelected)
    }

}