package com.leqi.lwcamera.module.shoot.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.model.bean.apiV2.UserIDBean
import com.tencent.bugly.crashreport.biz.UserInfoBean
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * @Description:    相机管理类  采用camerax
 * @Author:         ZHUYU
 * @CreateDate:     2019/10/17 13:31
 * @UpdateDate:     2020/03/01
 * @UpdateRemark:   更新说明：
 * @Version:        beta1.0
 */

object CameraManager {

    const val TAG = "CameraManager"

    /** Blocking camera operations are performed using this executor 使用此执行器执行阻塞摄像机操作*/
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private const val FILENAME = "yyyyMMddHHmmss"
    private const val PHOTO_EXTENSION = ".jpg"
    private const val RATIO_4_3_VALUE = 4.0 / 3.0
    private const val RATIO_16_9_VALUE = 16.0 / 9.0
    //默认摄像头是后置
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    //闪光灯模式
    private  var mFlashMode=ImageCapture.FLASH_MODE_AUTO
    private var camera: Camera? = null

    private lateinit var mViewFinder: PreviewView
    private lateinit var outputDirectory: String
    private lateinit var mContext: LifecycleOwner
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private lateinit var cameraSelector:CameraSelector

    /**
     * 初始化相机
     */
    fun init(context: LifecycleOwner, viewFinder: PreviewView) {
        this.mContext = context
        this.mViewFinder = viewFinder
        bindCameraUseCases()
        outputDirectory = Config.BASE_FOLDER_NAME
        setFocus()
    }


    /**
     * 声明和绑定预览、捕获和分析用例
     */
    private fun bindCameraUseCases() {
        // Get screen metrics used to setup
        // camera for full screen resolution
        val metrics = DisplayMetrics().also { mViewFinder.display.getRealMetrics(it) }
        Log.d(TAG, "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")
        val rotation = mViewFinder.display.rotation
        // Bind the CameraProvider to the LifeCycleOwner
         cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(mContext as Context)
        cameraProviderFuture.addListener(Runnable {
            // CameraProvider
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // 预览设置
            preview = Preview.Builder()
                // 宽高比  We request aspect ratio but no resolution
                .setTargetAspectRatio(screenAspectRatio)
                // 设定初始目标旋转  Set initial target rotation
                .setTargetRotation(rotation)
                .build()
            // Attach the viewfinder's surface provider to preview use case
            preview?.setSurfaceProvider(mViewFinder.previewSurfaceProvider)

            // 拍照设置
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                // We request aspect ratio but no resolution to match preview config, but letting
                // CameraX optimize for whatever specific resolution best fits our use cases
                .setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(rotation)
                .setFlashMode(mFlashMode)
                .build()

            // 图片分析配置
            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()
            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll()
            try {
                // A variable number of use-cases can be passed here -
                // camera provides access to CameraControl & CameraInfo
                camera = cameraProvider.bindToLifecycle(
                    mContext, cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(mContext as Context))
    }

    /**
     *  [androidx.camera.core.ImageAnalysisConfig] requires enum value of
     *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
     *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
     *  of preview ratio to one of the provided values.
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    /**
     * 拍照
     */
    fun takePhoto(takePictureListener: takePictureListener?) {
        // Get a stable reference of the modifiable image capture use case
        imageCapture?.let { imageCapture ->

            // Create output file to hold the image
             // val photoFile = File(outputDirectory, SimpleDateFormat(FILENAME, Locale.US).format(System.currentTimeMillis()) + PHOTO_EXTENSION)
            //创建临时文件
            val photoFile = createTempFile(System.currentTimeMillis().toString(), PHOTO_EXTENSION)
            LogUtils.e(photoFile.path)
            // Setup image capture metadata
            val metadata = ImageCapture.Metadata().apply {
                // Mirror image when using the front camera
                isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
            }
            // Create output options object which contains file + metadata
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                .setMetadata(metadata)
                .build()
            // Setup image capture listener which is triggered after photo has been taken
            imageCapture.takePicture(
                outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                        takePictureListener?.onError()
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                        takePictureListener?.onSuccess(savedUri)
                        Log.d(TAG, "Photo capture succeeded: $savedUri")
                         // 保存到系统相册，通知相册更新
//                        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//                        val contentUri = Uri.fromFile(photoFile)
//                        mediaScanIntent.data = contentUri
//                        (mContext as Context).sendBroadcast(mediaScanIntent)
                    }
                })
        }
    }

   // private val mtakePictureListener: takePictureListener? = null
    interface takePictureListener{
        fun onError()
        fun onSuccess(uri:Uri)
    }
    /**
     * 切换闪光灯模式
     */
    fun switchFlashMode() {
        if (mFlashMode == ImageCapture.FLASH_MODE_OFF) {
            mFlashMode = ImageCapture.FLASH_MODE_AUTO
            imageCapture?.flashMode = mFlashMode
        } else {
            mFlashMode = ImageCapture.FLASH_MODE_OFF
            imageCapture?.flashMode = mFlashMode
        }
    }

    fun getFlashMode(): Int {
        return mFlashMode
    }



    /**
     * 支持点击进行对焦
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setFocus() {
        mViewFinder.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                try {
                 val pointFactory = DisplayOrientedMeteringPointFactory(mViewFinder.display,cameraSelector,mViewFinder.width.toFloat(), mViewFinder.height.toFloat())
                   val meteringPoint =pointFactory.createPoint(event!!.x, event.y)
                    LogUtils.d("x:${event.x},y:${event.y}")
                    val focusMeteringAction=FocusMeteringAction.Builder(meteringPoint).build()
                    camera?.cameraControl?.startFocusAndMetering(focusMeteringAction)
                } catch (e: CameraInfoUnavailableException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            }
        })
    }

    /**
     * 打开相机、再次调用切换摄像头
     */
    fun switchCamera() {
        lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
            CameraSelector.LENS_FACING_BACK
        } else {
            CameraSelector.LENS_FACING_FRONT
        }
        // Re-bind use cases to update selected camera
        bindCameraUseCases()
    }

    /**
     * 解绑
     */
    fun unbind() {
        // Shut down our background executor  关闭后台执行器
        cameraExecutor.shutdown()
    }

}