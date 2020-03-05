package com.leqi.lwcamera.module.shoot.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.display.DisplayManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.MimeTypeMap
import android.webkit.PermissionRequest
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.leqi.lwcamera.config.Config.ALBUM
import com.leqi.lwcamera.model.CountClick
import com.leqi.lwcamera.model.bean.apiV2.CustomPrarms
import com.leqi.lwcamera.module.shoot.mvp.presenter.NewCameraPresenter
import com.leqi.lwcamera.module.shoot.mvp.view.NewCameraView
import com.leqi.lwcamera.module.shoot.util.CameraManager
import com.leqi.lwcamera.util.FileTool

import com.leqi.xxcamera.R
import com.leqi.xxcamera.XXApplication
import com.leqi.xxcamera.base.BaseXxActivity
import com.leqi.xxcamera.model.bean.apiV2.BodyBackgroundResponse
import com.leqi.xxcamera.model.bean.apiV2.BodyPictureResponse
import com.leqi.xxcamera.model.bean.apiV2.BodySpecsResponse
import com.leqi.xxcamera.model.bean.apiV2.ModelsResponse
import com.leqi.xxcamera.module.camera.view.ChooseModelView
import com.leqi.xxcamera.module.edit.activity.EditActivity
import com.leqi.xxcamera.module.system.activity.SettingActivity
import com.leqi.xxcamera.util.GlideEngine
import com.umeng.analytics.MobclickAgent
import com.umeng.socialize.utils.DeviceConfigInternal.context
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_new_camerax.*
import kotlinx.coroutines.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @Description:   拍摄证件照主界面
 * @Author:         ZHUYU
 * @CreateDate:     2019/10/17 14:59
 * @UpdateDate:     2019/10/17 14:59
 * @UpdateRemark:   更新说明：默认打开规格搜索框
 * @Version:        1.1
 */

class NewCameraXActivity : BaseXxActivity<NewCameraView, NewCameraPresenter>(), NewCameraView,
    LifecycleOwner {


    var animExit: Animation? = null
    var animEnter: Animation? = null
    //默认规格
    var mDefaultSpecId: String? = null
    //默认背景色
    var mDefaultBackground: Int = 0
    fun initArguments() {

    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_new_camerax
    }

    override fun isNeedTitleBar(): Boolean {
        return false
    }

    override fun initView() {
        animExit = AnimationUtils.loadAnimation(this@NewCameraXActivity, R.anim.input_method_exit)
        animEnter = AnimationUtils.loadAnimation(this@NewCameraXActivity, R.anim.input_method_enter)
        initArguments()
        //初始化状态栏，区别其他页面的白色样式
        initStatus()
        //初始化权限
        initPermission()

    }


    /**
     * 初始化状态栏
     */
    private fun initStatus() {
        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        //设置状态栏暗色模式，文字会变为浅色
        ScreenUtils.setFullScreen(this)
    }


    override fun initEvent() {
        /**切换摄像头**/
        switchCameraImg.setOnClickListener {
            CameraManager.switchCamera()
        }
        /**拍照**/
        shootBtn.setOnClickListener {
            showWaitDialog()
            CameraManager.takePhoto(object : CameraManager.takePictureListener {
                override fun onError() {

                }

                override fun onSuccess(uri: Uri) {
                    uri.path?.let { it -> {
                        mvpPresenter?.startMakePicture(it,mDefaultSpecId)
                    } }
                }

            })
        }
        /**切换闪光灯模式**/
        switchFlashImg.setOnClickListener {
            CameraManager.switchFlashMode()
            if (CameraManager.getFlashMode() == ImageCapture.FLASH_MODE_OFF) {
                switchFlashImg.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.mipmap.camera_flash_off
                    )
                )
            } else {
                switchFlashImg.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.mipmap.camera_flash_auto
                    )
                )
            }
        }
        /**设置**/
        settingImg.setOnClickListener {
            startActivity<SettingActivity>()
        }

        chooseModelView.setOnGroupItemClickListener(object : ChooseModelView.onItemClickListener {
            override fun onClick(model: ModelsResponse.ModelGroup.Model) {
                Glide.with(this@NewCameraXActivity.applicationContext).load(model.contour_url).placeholder(contourImg.drawable).into(contourImg)
                Glide.with(this@NewCameraXActivity.applicationContext).load(model.model_url).placeholder(modelImg.drawable).into(modelImg)
            }
        })
        /**打开模特选择页面**/
        chooseModelLayout.setOnClickListener {
            if (chooseModelView.isVisible) {
                chooseModelView.startAnimation(animExit)
                Handler().postDelayed({
                    chooseModelView.visibility = View.INVISIBLE
                }, 400)
                chooseModelBgImg.backgroundDrawable = getDrawable(R.drawable.btn_default_bg)
            } else {
                chooseModelView.startAnimation(animEnter)
                chooseModelBgImg.backgroundDrawable = getDrawable(R.drawable.btn_click_bg)
                chooseModelView.visibility = View.VISIBLE

            }
        }
        /**人像抠图，打开相册**/
        albumLayout.setOnClickListener {
            album()
        }
    }

    override fun initData() {
        mvpPresenter?.getModels()
        //mvpPresenter?.getBodySpecs()
    }

    /**
     * 初始化权限操作
     */
    fun initPermission() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    // Wait for the views to be properly laid out
                    previewView.post {
                        CameraManager.init(this@NewCameraXActivity, previewView)
                    }
                }

                override fun onDenied() {
                    // toast("不同意")
                }

            }).request()
    }

    /**
     * 打开相册
     */
    private fun album() {
        // MobclickAgent.onEvent(this@NewCameraXActivity, CountClick.ShootAlbum.key)
        EasyPhotos.createAlbum(this, false, GlideEngine.getInstance())
            .setPuzzleMenu(false)
            .setCleanMenu(false)
            .start(ALBUM)
    }


    /**
     * 处理相册里选择的图片
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK && data != null && requestCode == ALBUM) {
            val resultPhotos: ArrayList<Photo> = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS)
            if (resultPhotos.size == 0) {
                ToastUtils.showShort(getString(R.string.camera_activity_null_data_photo))
                this.finish()
                return
            }
            val imageByteArray = FileTool.file2Byte(resultPhotos[0].path)
            if (imageByteArray == null) {
                ToastUtils.showShort(getString(R.string.camera_activity_null_data_photo))
                return
            }
            if (imageByteArray.size > 10 * 1024 * 1024) {
                ToastUtils.showShort(getString(R.string.camera_activity_too_large_photo))
                return
            }
            showInProductionDialog("证件照制作中")
            //制作照片
            mvpPresenter?.startMakePicture(resultPhotos[0].path,mDefaultSpecId)
        }
    }


    override fun showModels(it: ModelsResponse) {
        chooseModelView.setData(it)

    }

    override fun showBodyPicture(it: BodyPictureResponse) {
        hideWaitDialog()
        EditActivity.actionStart(this, it.result?.key, it.result?.pose_pic)
    }

    override fun showSpecs(it: BodySpecsResponse) {
        if(it.result!=null&& it.result.isNotEmpty()){
            mDefaultSpecId= it.result[0].spec_id.toString()
        }

    }

    override fun showBackground(it: BodyBackgroundResponse) {

    }


    override fun createPresenter(): NewCameraPresenter {
        return NewCameraPresenter()
    }

    override fun showWaitDialog() {
        showInProductionDialog("制作中...")
    }

    override fun hideWaitDialog() {
        hideInProductionDialog()
    }


    override fun onError(message: String) {
        hideWaitDialog()
        ToastUtils.showShort(message)
    }

    override fun onDestroy() {
        //相机解绑
        CameraManager.unbind()
        super.onDestroy()
    }


    //按两次返回键退出app的间隔时间
    private val TIME_EXIT = 2000
    private var mBackPressed: Long = 0

    override fun onBackPressed() {
        //先判断 选择模特页面先去关闭，在进行其他操作
        if (chooseModelView.isVisible) {
            chooseModelLayout.performClick()
            return
        }
        if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            toast("再按一次退出")
            mBackPressed = System.currentTimeMillis()

        }
    }

}