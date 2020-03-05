package com.leqi.lwcamera.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.loader.ImageLoader

/**
 * Created by YqopY on 2019/2/18.
 * @描述 ${Banner图片加载器}
 * // 设置加载图片 边角圆
 */
class GlideImageLoader(errorPath: Int) : ImageLoader() {

    private var mErrorPath: Int = errorPath
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {





        Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(RoundedCorners(ScreenUtil.dip2px(context, 10)))).error(mErrorPath).into(imageView)
    }
}
