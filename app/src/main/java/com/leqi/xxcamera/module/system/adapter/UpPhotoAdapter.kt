package com.leqi.xxcamera.module.system.adapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leqi.xxcamera.R

/**
 * 描述：
 * 作者：ZHUYU
 * 日期：2019/9/29 10:35
 */
class UpPhotoAdapter : BaseQuickAdapter<Bitmap, BaseViewHolder>(R.layout.item_up_photo) {
    private var onUpPhotoClick: OnUpPhotoClick? = null

    interface OnUpPhotoClick {
        fun cancelUpload(cancelPosition: Int)
        fun addPhoto()
    }

    fun setOnUpPhotoClick(onUpPhotoClick: OnUpPhotoClick) {
        this.onUpPhotoClick = onUpPhotoClick
    }


    override fun convert(helper: BaseViewHolder, item: Bitmap?) {
        val parent = helper.getView<ConstraintLayout>(R.id.cl_parent)
        val cancelUpload = helper.getView<ImageView>(R.id.cancel_upload)
        val addPhoto = helper.getView<ImageView>(R.id.add_photo)
        val showPhoto = helper.getView<ImageView>(R.id.show_photo)

        when (data.size) {
            0 -> {
                ToastUtils.showShort("bitmap空 异常!!")
                addPhoto.isClickable = true
                parent.visibility = View.VISIBLE
                addPhoto.visibility = View.VISIBLE
                showPhoto.visibility = View.GONE
                cancelUpload.visibility = View.GONE
            }
            in 1..4 -> {
                if (data.size == (helper.layoutPosition + 1)) {
                    //如果是最后一个，显示 upPhoto
                    addPhoto.isClickable = true
                    parent.visibility = View.VISIBLE
                    addPhoto.visibility = View.VISIBLE
                    showPhoto.visibility = View.GONE
                    cancelUpload.visibility = View.GONE
                } else {
                    addPhoto.isClickable = false
                    parent.visibility = View.VISIBLE
                    addPhoto.visibility = View.GONE
                    showPhoto.visibility = View.VISIBLE
                    cancelUpload.visibility = View.VISIBLE
                    Glide.with(mContext).load(item).into(showPhoto)
                }
            }
            5 -> {
                addPhoto.isClickable = false
                if (data.size == (helper.layoutPosition + 1)) {
                    //如果是最后一个 ，不显示 upPhoto
                    parent.visibility = View.GONE
                } else {
                    parent.visibility = View.VISIBLE
                    addPhoto.visibility = View.GONE
                    showPhoto.visibility = View.VISIBLE
                    cancelUpload.visibility = View.VISIBLE
                    Glide.with(mContext).load(item).into(showPhoto)
                }
            }
            else -> {
                addPhoto.isClickable = false
                ToastUtils.showShort("bitmap超过5个 异常不显示!!")
                parent.visibility = View.GONE
            }
        }
        cancelUpload.setOnClickListener(View.OnClickListener { view ->
            if (onUpPhotoClick != null) {
                onUpPhotoClick!!.cancelUpload(helper.layoutPosition)
            }
        })

        addPhoto.setOnClickListener(View.OnClickListener { view ->
            if (onUpPhotoClick != null) {
                onUpPhotoClick!!.addPhoto()
            }
        })
    }


}