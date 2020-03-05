package com.leqi.xxcamera.module.edit.activity

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.leqi.xxcamera.R
import com.leqi.xxcamera.base.BaseXxActivity
import com.leqi.xxcamera.model.bean.apiV2.BodyBackgroundResponse
import com.leqi.xxcamera.model.bean.apiV2.BodyPictureResponse
import com.leqi.xxcamera.model.bean.apiV2.BodySpecsResponse
import com.leqi.xxcamera.module.camera.view.ChooseSpecView
import com.leqi.xxcamera.module.edit.mvp.presenter.EditMainPresenter
import com.leqi.xxcamera.module.edit.mvp.view.EditMainView
import kotlinx.android.synthetic.main.activity_edit_layout.*

class EditActivity : BaseXxActivity<EditMainView, EditMainPresenter>(), EditMainView {

    companion object {
        fun actionStart(context: Context, imageKey: String?, url: String?) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("imageKey", imageKey)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    var animExit: Animation? = null
    var animEnter: Animation? = null
    private var mImageKey: String? = null
    private var mUrl: String? = null

    var mBackgrounds: List<BodyBackgroundResponse.BodyBackground>? = null
    private fun initArguments() {
        mImageKey = this.intent.getStringExtra("imageKey")
        mUrl = this.intent.getStringExtra("url")
    }

    override fun isNeedTitleBar(): Boolean {
        return false
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_edit_layout
    }

    override fun initView() {
        initArguments()
        animExit = AnimationUtils.loadAnimation(this@EditActivity, R.anim.input_method_exit)
        animEnter = AnimationUtils.loadAnimation(this@EditActivity, R.anim.input_method_enter)

        Glide.with(this.applicationContext).load(mUrl).placeholder(previewImg.drawable).into(previewImg)

    }

    override fun initEvent() {
        chooseSpecLayout.setOnClickListener {
            if (chooseSpecView.isVisible) {
                chooseSpecView.startAnimation(animExit)
                Handler().postDelayed({
                    chooseSpecView.visibility = View.INVISIBLE
                }, 400)
                chooseSpecBg.setImageDrawable(resources.getDrawable(R.drawable.btn_default_bg))

            } else {
                chooseSpecView.startAnimation(animEnter)
                chooseSpecBg.setImageDrawable(resources.getDrawable(R.drawable.btn_click_bg))
                chooseSpecView.visibility = View.VISIBLE
            }
        }
        chooseSpecView.setOnGroupItemClickListener(object : ChooseSpecView.onItemClickListener {
            override fun onClick(spec: BodySpecsResponse.BodySpecs) {
                specNameTv.text = "已选：${spec.name}"
                mvpPresenter?.makePicture(spec.spec_id, mImageKey)
            }

        })
    }

    override fun initData() {
       // mvpPresenter?.getBodySpecs()
        mvpPresenter?.getBodyBackground()
    }

    override fun createPresenter(): EditMainPresenter {
        return EditMainPresenter()
    }

    override fun showSpecs(it: BodySpecsResponse) {
        it.result?.let { it1 -> chooseSpecView.setData(it1) }
        specNameTv
    }

    override fun showBackground(it: BodyBackgroundResponse) {
        mBackgrounds = it.result
        Glide.with(this.applicationContext).load(mBackgrounds?.get(0)?.url).into(previewBgImg)
    }

    override fun showPicture(it: BodyPictureResponse) {
        hideWaitDialog()
        mUrl = it.result?.pose_pic
        Glide.with(this.applicationContext).load(mUrl).placeholder(previewImg.drawable).into(previewImg)
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
}