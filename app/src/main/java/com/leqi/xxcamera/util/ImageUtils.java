package com.leqi.xxcamera.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/8/8 - 00 : 23
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/8/8 - 00 : 23
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 */
public class ImageUtils {

    private static final int HEAD = 8;
    private static final int IHDR = 12;
    private static byte[] pHYS = {0x00, 0x00, 0x00, 0x09, 0x70, 0x48, 0x59, 0x73, 0x00, 0x00,
            0x1e, (byte) 0xc2, 0x00, 0x00, 0x1e, (byte) 0xc2, 0x01, 0x6e, (byte) 0xd0, 0x75, 0x3e}; //png物理像素尺寸数据块


    /**
     * 设置dpi
     * @param imageData png数据
     */
    public static byte[] changePngDpi(byte[] imageData) {
        try {
            //计算左块数据长度
            byte[] leftLength = new byte[4];
            leftLength[0] = imageData[HEAD];
            leftLength[1] = imageData[HEAD + 1];
            leftLength[2] = imageData[HEAD + 2];
            leftLength[3] = imageData[HEAD + 3];

            int length = byteArrayToLength(leftLength) + HEAD + IHDR; //IHDR数据块总长度

            byte[] left = new byte[length];
            System.arraycopy(imageData, 0, left, 0, length);
            byte[] right = new byte[imageData.length - length];
            System.arraycopy(imageData, length, right, 0, imageData.length - length);

            byte[] newData = new byte[pHYS.length + imageData.length];
            System.arraycopy(left, 0, newData, 0, left.length);
            System.arraycopy(pHYS, 0, newData, length, pHYS.length);
            System.arraycopy(right, 0, newData, left.length + pHYS.length, right.length);
            return newData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageData;
    }

    /**
     * 设置dpi
     *
     * @param imageData jpeg数据
     * @param dpi       Dpi
     */
    public void changeJpegDpi(byte[] imageData, int dpi) {
        imageData[13] = 1;
        imageData[14] = (byte) (dpi >> 8);
        imageData[15] = (byte) (dpi & 0xff);
        imageData[16] = (byte) (dpi >> 8);
        imageData[17] = (byte) (dpi & 0xff);
    }

    /**
     * png透明化处理
     *
     * @param filePath 原始png文件路径
     */
    public static byte[] toTransparent(String filePath) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            Bitmap oldBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas newCanvas = new Canvas(oldBitmap);
            newCanvas.drawBitmap(bitmap, 0, 0, null);

            Bitmap newBitmap = transBitmapAlpha(oldBitmap);

            ByteArrayOutputStream imageByteArray = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, imageByteArray);

            return imageByteArray.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * png透明化处理
     *
     * @param bitmap png位图
     */
    public static byte[] toTransparent(Bitmap bitmap) {
        try {
            Bitmap oldBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas newCanvas = new Canvas(oldBitmap);
            newCanvas.drawBitmap(bitmap, 0, 0, null);
            Bitmap newBitmap = transBitmapAlpha(oldBitmap);
            ByteArrayOutputStream imageByteArray = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, imageByteArray);

            return imageByteArray.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int byteArrayToLength(byte[] byteArray) {
        int result = 0;
        for (int i = 0; i < byteArray.length; i++) {
            result = (byteArray[i] & 0x000000FF) ^ result;
            if (i < byteArray.length - 1) {
                result <<= 8;
            }
        }
        return result;
    }

    private static Bitmap transBitmapAlpha(Bitmap localBits) {
        for (int y = localBits.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < localBits.getWidth(); x++) {
                if (localBits.getPixel(x, y) == 0xFFFFFFFF) {
                    localBits.setPixel(x, y, 0);
                }
            }
        }
        return localBits;
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        assert sdDir != null;
        return sdDir.toString();
    }

}