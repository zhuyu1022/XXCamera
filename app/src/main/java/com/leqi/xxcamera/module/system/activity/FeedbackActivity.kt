package com.leqi.xxcamera.module.system.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.config.Config.ALBUM
import com.leqi.lwcamera.util.FileTool
import com.leqi.xxcamera.R
import com.leqi.xxcamera.base.BaseXxActivity
import com.leqi.xxcamera.module.system.adapter.UpPhotoAdapter
import com.leqi.xxcamera.module.system.mvp.presenter.FeedbackPresenter
import com.leqi.xxcamera.module.system.mvp.view.FeedbackView
import com.leqi.xxcamera.util.GlideEngine
import kotlinx.android.synthetic.main.activity_feedbacks.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


/**
 * 描述：问题反馈页面
 * 作者：ZHUYU
 * 日期：2019/9/26 9:28
 */
class FeedbackActivity : BaseXxActivity<FeedbackView, FeedbackPresenter>(), FeedbackView {

    private val addPhoto: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.mipmap.up_photo)
    }
    private var upPhotoList: ArrayList<Bitmap> = ArrayList() // 列表最后一个必须为addPhoto
    private var photoAdapter: UpPhotoAdapter? = null
    private var mImagePath: String? = null

    companion object {
        fun actionStart(context: Context, imagePath: String) {
            val intent = Intent(context, FeedbackActivity::class.java)
            intent.putExtra("imagePath", imagePath)
            context.startActivity(intent)
        }
    }

    private fun initArguments() {
        mImagePath = this.intent.getStringExtra("imagePath")
        if (!mImagePath.isNullOrEmpty()) {
            upPhotoList.add(ImageUtils.getBitmap(mImagePath))
        }
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_feedbacks
    }

    override fun initView() {
        initArguments()
        setTitleText(resources.getString(R.string.feedback_title))
        upPhotoList.add(addPhoto)
        photoAdapter = UpPhotoAdapter()
        photoAdapter!!.setNewData(upPhotoList)
        feedback_upPhotot_recyclerview.adapter = photoAdapter
    }

    override fun initEvent() {

        feedback_content_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val contactInformation = feedback_contact_infor_et.text.toString()
                if (s.isNotEmpty() && s.length >= 10 && contactInformation.isNotEmpty()) {
                    feedback_send_button.background = ContextCompat.getDrawable(
                        this@FeedbackActivity,
                        R.drawable.bg_corner_button
                    )
                } else {
                    feedback_send_button.background = ContextCompat.getDrawable(
                        this@FeedbackActivity,
                        R.drawable.bg_corner_button_disable
                    )
                }
            }
        })
        feedback_contact_infor_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val content = feedback_content_et.text.toString()
                if (s.isNotEmpty() && content.isNotEmpty() && content.length >= 10) {
                    feedback_send_button.background = ContextCompat.getDrawable(
                        this@FeedbackActivity,
                        R.drawable.bg_corner_button
                    )
                } else {
                    feedback_send_button.background = ContextCompat.getDrawable(
                        this@FeedbackActivity,
                        R.drawable.bg_corner_button_disable
                    )
                }
            }
        })
        feedback_send_button.setOnClickListener {

        }
        photoAdapter!!.setOnUpPhotoClick(object : UpPhotoAdapter.OnUpPhotoClick {
            override fun addPhoto() {
                album()
            }

            override fun cancelUpload(cancelPosition: Int) {
                upPhotoList.removeAt(cancelPosition)
                photoAdapter!!.notifyDataSetChanged()
            }

        })



        feedback_send_button.onClick { send() }
    }

    override fun initData() {

    }

    @Suppress("DEPRECATION")
    private fun album() {
        EasyPhotos.createAlbum(this, false, GlideEngine.getInstance())
            .setPuzzleMenu(false)
            .setCleanMenu(false)
            .start(ALBUM)
    }

    /**
     * 发送反馈意见
     */
    private fun send() {
        //意见
        var content = feedback_content_et.text.toString()
        //联系方式
        var contactInformation = feedback_contact_infor_et.text.toString()
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort("请填写反馈内容，需大于10个字。")
            return
        }
        if (content.length < 10) {
            ToastUtils.showShort("请填写反馈内容，需大于10个字。")
            return
        }
        if (TextUtils.isEmpty(contactInformation)) {
            ToastUtils.showShort("请填写联系方式哦。")
            return
        }
        //showInProductionDialog("反馈提交中...")
        showWaitDialog()
        val upBase64: ArrayList<String> = ArrayList()
        if (upPhotoList.size > 1) {
            val tempData: ArrayList<Bitmap> = ArrayList()
            tempData.addAll(upPhotoList)
            tempData.removeAt(tempData.size - 1)
            tempData.forEach {
                val base64 = FileTool.bitmapToBase64(it)
                upBase64.add(base64)
            }
        }
        mvpPresenter?.sendFeedBack(content!!, contactInformation!!, upBase64)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK && data != null && requestCode == ALBUM) {
            //val path = data.getStringArrayListExtra(EasyPhotos.RESULT_PHOTOS)
            val resultPhotos: ArrayList<Photo> =
                data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS)
            if (resultPhotos.size == 0) {
                ToastUtils.showShort(getString(R.string.camera_activity_null_data_photo))
                this.finish()
                return
            }
            LogUtils.e("resultPaths[0]: ${resultPhotos[0]}")
            zipImage(resultPhotos[0].path)
        }
    }

    private fun zipImage(image: String) {
        Luban.with(this)
            .load(image)
            .ignoreBy(5)
            .setTargetDir(Config.BASE_FOLDER_NAME)
            .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                    //  压缩开始前调用，可以在方法内启动 loading UI
                    LogUtils.d("开始压缩图片")
                }

                override fun onSuccess(file: File) {
                    // 压缩成功后调用，返回压缩后的图片文件
                    addPicture(file)
                }

                override fun onError(e: Throwable) {
                    //  当压缩过程出现问题时调用
                    LogUtils.d("压缩图片异常")
                }
            }).launch()
    }


    private fun addPicture(file: File) {
        LogUtils.d("压缩图片完成")
        val size = (File(file.absolutePath).length() / 1024).toInt()
        val imageBitmap = BitmapFactory.decodeFile(file.absolutePath)
        LogUtils.d("压缩后图片大小 和像素 ：：：$size:::::${imageBitmap.width}PX::::::${imageBitmap.height}PX")
        FileTool.deleteFile(this, file)
        if (upPhotoList.size in 1..4) {
            LogUtils.d("upPhotoList.size=${upPhotoList.size}")
            upPhotoList.removeAt(upPhotoList.size - 1)
            upPhotoList.add(imageBitmap)
            upPhotoList.add(addPhoto)
            LogUtils.d("upPhotoList.size=${upPhotoList.size}")
        } else {
            //数据异常重置
            upPhotoList.clear()
            upPhotoList.add(addPhoto)
        }
        photoAdapter!!.notifyDataSetChanged()
    }


    override fun clearData() {
        hideWaitDialog()
        feedback_content_et.setText("")
        feedback_contact_infor_et.setText("")
        upPhotoList.clear()
        upPhotoList.add(addPhoto)
        photoAdapter!!.notifyDataSetChanged()
    }

    override fun showWaitDialog() {
        setRefreshing(true)
    }

    override fun hideWaitDialog() {
        setRefreshing(false)
    }

    override fun createPresenter(): FeedbackPresenter {
        return FeedbackPresenter()
    }

    override fun onError(message: String) {
        hideWaitDialog()
        toast(message)
    }
}