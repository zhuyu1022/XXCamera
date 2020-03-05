package com.leqi.lwcamera.util

import android.graphics.*
import com.blankj.utilcode.util.LogUtils
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

/**
 * @Author: zhuxiaoyao
 * @Date: 2018-12-5 13:05:28
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2018-12-5 13:05:37
 * @Description: institute
 * @Email: zhuxs@venpoo.com
 *  解码(有压缩)
 */
object BitmapUtil {
    fun decodeSampleBitmap(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        return decodeSampleBitmap(byteStream.toByteArray(), reqWidth, reqHeight)
    }

    fun decodeSampleBitmap(data: ByteArray, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(data, 0, data.size, options)
    }

    private fun calculateSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val outWidth = options.outWidth
        val outHeight = options.outHeight
        var sampleSize = 1
        if (outWidth > reqWidth || outHeight > reqHeight) {
            while (outWidth / sampleSize > reqWidth || outHeight / sampleSize > reqHeight) {
                sampleSize++
            }
        }
        LogUtils.d("DecodeSampleBitmap--sampleSize: $sampleSize")
        return sampleSize
    }

    /**
     * 把Bitmap转Byte
     */
    fun bitmap2Bytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    fun setBitmapBorder(bitmap: Bitmap, color: Int, fl: Float): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = fl
        canvas.drawBitmap(bitmap, rect, rect, paint)
        canvas.drawRect(rect, paint)
        return output
    }

    //    bitmap 转BGR
    fun pixels2BGR(image: Bitmap): ByteArray {
        // calculate how many bytes our image consists of
        val bytes = image.byteCount
        val buffer = ByteBuffer.allocate(bytes) // Create a new buffer
        image.copyPixelsToBuffer(buffer) // Move the byte data to the buffer
        val temp = buffer.array() // Get the underlying array containing the data.
        val pixels = ByteArray(temp.size / 4 * 3) // Allocate for BGR
        // Copy pixels into place
        for (i in 0 until temp.size / 4) {
            pixels[i * 3] = temp[i * 4 + 2]        //B
            pixels[i * 3 + 1] = temp[i * 4 + 1]    //G
            pixels[i * 3 + 2] = temp[i * 4]        //R
        }
        return pixels
    }

    //    bitmap 转 Alpha
    fun pixels2Alpha(image: Bitmap): ByteArray {
        //返回可用于储存此位图像素的最小字节数
        val bytes = image.byteCount
        val buffer = ByteBuffer.allocate(bytes)  //  使用allocate()静态方法创建字节缓冲区
        image.copyPixelsToBuffer(buffer)  // 将位图的像素复制到指定的缓冲区
        val temp = buffer.array() // 获取包含数据的基础数组。
        val pixels = ByteArray(temp.size / 4)
        // Copy pixels into place
        for (i in 0 until temp.size / 4) {
            pixels[i] = temp[i * 4 + 3]        //A
        }
        return pixels
    }

    fun rgb2AlphaBGR(image: Bitmap): Array<ByteArray> {
        // calculate how many bytes our image consists of
        val bytes = image.byteCount
        val buffer = ByteBuffer.allocateDirect(bytes) // Create a new buffer
        image.copyPixelsToBuffer(buffer) // Move the byte data to the buffer
        val temp = buffer.array() // Get the underlying array containing the data.
        val pixelsBGR = ByteArray(temp.size / 4 * 3) // Allocate for BGR
        val pixelsAlpha = ByteArray(temp.size / 4) // Allocate for Alpha
        // Copy pixels into place
        for (i in 0 until temp.size / 4) {
            pixelsBGR[i * 3 + 0] = temp[i * 4 + 2]    //B
            pixelsBGR[i * 3 + 1] = temp[i * 4 + 1]    //G
            pixelsBGR[i * 3 + 2] = temp[i * 4 + 0]    //R
            pixelsAlpha[i] = temp[i * 4 + 3]          //A
        }
        return arrayOf(pixelsAlpha, pixelsBGR)
    }

    //  ARGB_8888 存储顺序是 R-G-B-A
    fun alphaBGR2RGB(width: Int, height: Int, maskBytes: ByteArray, faceBytes: ByteArray): Bitmap {
        val pixelRGB = ByteArray(faceBytes.size / 3 * 4)
        for (i in 0 until pixelRGB.size / 4) {
            pixelRGB[i * 4 + 0] = faceBytes[i * 3 + 2]    //R
            pixelRGB[i * 4 + 1] = faceBytes[i * 3 + 1]    //G
            pixelRGB[i * 4 + 2] = faceBytes[i * 3 + 0]    //B
            pixelRGB[i * 4 + 3] = maskBytes[i]            //A
        }
        val stitchBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(pixelRGB))
        return stitchBmp
    }

}
