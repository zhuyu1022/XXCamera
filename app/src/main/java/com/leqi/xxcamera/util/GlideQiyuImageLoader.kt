package com.leqi.lwcamera.util

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.qiyukf.unicorn.api.ImageLoaderListener
import com.qiyukf.unicorn.api.UnicornImageLoader

/**
 * Created by zhoujianghua on 2016/12/27.
 */

class GlideQiyuImageLoader(context: Context) : UnicornImageLoader {
    private val context: Context

    init {
        this.context = context.applicationContext
    }


    override fun loadImageSync(uri: String, width: Int, height: Int): Bitmap? {
        return null
    }

    override fun loadImage(uri: String, width: Int, height: Int, listener: ImageLoaderListener) {
        var width = width
        var height = height
        if (width <= 0 || height <= 0) {
            height = Integer.MIN_VALUE
            width = height
        }
        Glide.with(context).asBitmap().load(uri).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>,
                                      isFirstResource: Boolean): Boolean {
                listener.onLoadFailed(e)
                return true
            }

            override fun onResourceReady(resource: Bitmap?, model: Any, target: Target<Bitmap>, dataSource: DataSource,
                                         isFirstResource: Boolean): Boolean {

                if (resource != null) {
                    listener.onLoadComplete(resource)
                    return true
                }
                return false
            }
        }).into(object : SimpleTarget<Bitmap>(width, height) {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                listener.onLoadComplete(resource)
            }
        })
    }
}
