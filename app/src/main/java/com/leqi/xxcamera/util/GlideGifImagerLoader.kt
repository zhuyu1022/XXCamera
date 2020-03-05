package com.leqi.xxcamera.util

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.leqi.xxcamera.R

import com.qiyukf.unicorn.api.UnicornGifImageLoader

import java.io.Serializable

class GlideGifImagerLoader(internal var context: Context) : UnicornGifImageLoader, Serializable {

    override fun loadGifImage(url: String?, imageView: ImageView, imgName: String?) {
        if (url == null || imgName == null) {
            return
        }
        Glide.with(context).load(url).placeholder(R.mipmap.logo).error(R.mipmap.order_default_img)
                .into(imageView)
    }
}

