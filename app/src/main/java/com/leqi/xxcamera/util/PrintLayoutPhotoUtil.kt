package com.leqi.lwcamera.util

import android.graphics.*
import com.blankj.utilcode.util.LogUtils
import com.leqi.lwcamera.model.bean.apiV2.SpecInfoBean

/**
 * @Author: zhuxiaoyao
 * @Date: 2019/4/9 - 09 : 28
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/4/9 - 09 : 28
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 * 生成本地排版照片
 */
object PrintLayoutPhotoUtil {

    private const val compressionFactor = 2
    fun typesettingPhoto(bitmap : Bitmap , specInfo : SpecInfoBean) : Bitmap {
        val printPhotoBitmap = Bitmap.createBitmap(898 , 603 , Bitmap.Config.ARGB_8888)
        val printCanvas = Canvas(printPhotoBitmap)
        printCanvas.drawColor(Color.WHITE)
        //设置绘画以使位图平滑
        val paint = Paint()
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        paint.strokeWidth = 1F
        paint.style = Paint.Style.FILL
        if (specInfo.photo_params!!.is_print) {
            val thumbnail = thumbnail(bitmap , specInfo)
            drawBaseLine(specInfo.photo_params!!.place_params!!.params!! , printCanvas , thumbnail.width , thumbnail.height , paint)
            // 摆放照片
            drawImage(thumbnail , specInfo.photo_params!!.place_params!!.params!! , printCanvas , paint)
            // 文字
            paint.color = Color.BLACK
            paint.textSize = 18F
            printCanvas.drawText("请 使 用 六 寸 相 纸 打 印" , 683F , 568F , paint)
        } else {
            paint.color = Color.BLACK
            paint.textSize = 35F
            printCanvas.drawText("此 规 格 不 支 持 冲 印" , 550F , 528F , paint)
        }

        val paint1 = Paint()
        paint1.textSize = 200f
        paint1.color = Color.WHITE
        paint1.alpha=80
        paint.strokeWidth = 30F
        drawText(printCanvas, "证件照", 150f, 150f,paint1,45f)
        return printPhotoBitmap
    }


    private fun drawText(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint, angle: Float) {
        if (angle != 0f) {
            canvas.rotate(angle, x, y)
        }
        canvas.drawText(text, x, y, paint)
        if (angle != 0f) {
            canvas.rotate(-angle, x, y)
        }
    }

    /*生成缩略图*/
    private fun thumbnail(bitmap : Bitmap , specInfo : SpecInfoBean) : Bitmap {
        LogUtils.d("涂抹原图: ${bitmap.width} -- ${bitmap.height}")
        //旋转原图
        val matrix = Matrix()
        if (specInfo.photo_params!!.place_params!!.is_rotate) {
            matrix.postRotate(270.0f) // 旋转
        }
        //生成规格对应尺寸的一半 thumbnail
        val width = bitmap.width
        val height = bitmap.height
        val newWidth : Int = specInfo.photo_params?.px_size!![0].toInt() / compressionFactor
        val newHeight : Int = specInfo.photo_params?.px_size!![1].toInt() / compressionFactor
        val scaleWidth = newWidth.toFloat() / width.toFloat()
        val scaleHeight = newHeight.toFloat() / height.toFloat()
        matrix.postScale(scaleWidth , scaleHeight)
        val thumbnail = Bitmap.createBitmap(bitmap , 0 , 0 , bitmap.width , bitmap.height , matrix , true)
        LogUtils.d("缩略原图: ${thumbnail.width} -- ${thumbnail.height}")
        return thumbnail
    }

    /*画图*/
    private fun drawImage(photo : Bitmap , params : List<List<Int?>> , canvas : Canvas , paint : Paint) {
        LogUtils.d("排版几张==${params.size}")
        paint.color = Color.WHITE
        for (photoPaperSlot in params) {
            val rectF = RectF(photoPaperSlot[0]!!.toFloat() / compressionFactor - 8 , photoPaperSlot[1]!!.toFloat() / compressionFactor - 8 , photoPaperSlot[0]!!.toFloat() / compressionFactor + photo.width + 8 , photoPaperSlot[1]!!.toFloat() / compressionFactor + photo.height + 8)
            canvas.drawRect(rectF , paint)
            canvas.drawBitmap(photo , photoPaperSlot[0]!!.toFloat() / compressionFactor , photoPaperSlot[1]!!.toFloat() / compressionFactor , paint)
        }
    }

    /*画裁剪线*/
    private fun drawBaseLine(params : List<List<Int?>> , canvas : Canvas , width : Int , height : Int , paint : Paint) {
        LogUtils.d("排版几张==${params.size}")
        for (slot in params) {
            paint.color = Color.rgb(66 , 66 , 66)
            val baseX = slot[0]!!.toFloat() / compressionFactor
            val baseY = slot[1]!!.toFloat() / compressionFactor
            //横轴
            canvas.drawLine(0F , baseY , 898F , baseY , paint)
            //纵轴
            canvas.drawLine(baseX , 0F , baseX , 603F , paint)
            //横轴
            canvas.drawLine(0F , baseY + height , 898F , baseY + height , paint)
            //纵轴
            canvas.drawLine(baseX + width , 0F , baseX + width , 603F , paint)
            //找最小最大顶点
            if (params.size > 1) {
                var minX = 1795F
                var maxX = 0F
                var minY = 1795F
                var maxY = 0F
                for (paramsSlot in params) {
                    if (minX > paramsSlot[0]!!.toFloat()) {
                        minX = paramsSlot[0]!!.toFloat()
                    }
                    if (minY > paramsSlot[1]!!.toFloat()) {
                        minY = paramsSlot[1]!!.toFloat()
                    }
                    if (maxX < paramsSlot[0]!!.toFloat()) {
                        maxX = paramsSlot[0]!!.toFloat()
                    }
                    if (maxY < paramsSlot[1]!!.toFloat()) {
                        maxY = paramsSlot[1]!!.toFloat()
                    }
                }
                LogUtils.d("minX: $minX  minY:$minY maxX$maxX  maxY$maxX ")
                // 画白色方块盖住黑线  为了显示效果和服务器生成的照片一样
                val rectF = RectF(minX / compressionFactor - 8 , minY / compressionFactor - 8 , maxX / compressionFactor + 8 + width , maxY / compressionFactor + 8 + height)
                paint.color = Color.WHITE
                canvas.drawRect(rectF , paint)
            }
            //           画白色方块盖住黑线
            //           并没有什么卵用 显示效果上是等同 画白色方块盖住黑线
            //            if (params.size == 2) {
            //                val rectF = RectF(params[0][0]!!.toFloat() / compressionFactor - 8 , params[0][1]!!.toFloat() / compressionFactor - 8 , params[1][0]!!.toFloat() / compressionFactor + 8 + width , params[1][1]!!.toFloat() / compressionFactor + 8 + height)
            //                paint.color = Color.WHITE
            //                canvas.drawRect(rectF , paint)
            //            }
        }
    }

}