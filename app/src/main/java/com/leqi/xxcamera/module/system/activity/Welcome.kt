package com.leqi.xxcamera.module.system.activity

import android.os.Handler
import android.view.View
import androidx.core.app.ActivityCompat
import com.blankj.utilcode.util.SPStaticUtils
import com.leqi.lwcamera.config.IS_AGREE_PRIVACY_PROTOCOL
import com.leqi.lwcamera.module.common.dialog.CustomDialog

import com.leqi.lwcamera.module.home.mvp.presenter.WelcomePresenter
import com.leqi.lwcamera.module.home.mvp.view.WelcomeView
import com.leqi.lwcamera.module.shoot.activity.NewCameraXActivity
import com.leqi.xxcamera.BuildConfig
import com.leqi.xxcamera.R
import com.leqi.xxcamera.base.BaseXxActivity
import com.leqi.xxcamera.module.system.dialog.PrivacyProtocolDialog
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity

class WelcomeActivity : BaseXxActivity<WelcomeView,WelcomePresenter>(), WelcomeView {


    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_welcome
    }


    override fun isNeedTitleBar(): Boolean {
        return false
    }

    override fun initView() {
        this.window.statusBarColor = ActivityCompat.getColor(this, R.color.colorPrimary)

        if (BuildConfig.FLAVOR_market == "huawei") {
            huaweiLogoImg.visibility = View.VISIBLE
        }
        if (SPStaticUtils.getBoolean(IS_AGREE_PRIVACY_PROTOCOL)) {
            //初始化userid和appswitch
            mvpPresenter?.initApp()
        } else {
            openPrivacylDialog()
        }


    }

    override fun initEvent() {

    }

    override fun initData() {

    }


    override fun onInitSuccess() {
        //延迟一秒，不然有时候太快了
        Handler().postDelayed(Runnable {
            startActivity<NewCameraXActivity>()
            finish()
        }, 1000)

    }

    override fun onInitFail() {
        val initFailDialog = CustomDialog.instance("提示", "应用初始化失败，请检查网络后重试！", "退出", "重试")
        initFailDialog.setClickListener(object : CustomDialog.CustomDialogListener {
            override fun onLeftBtnClick() {
                this@WelcomeActivity.finish()
            }

            override fun onRightBtnClick() {
                mvpPresenter?.initApp()
            }

        })
        initFailDialog.show(supportFragmentManager, "initFailDialog")
    }

    override fun showWaitDialog() {

    }

    override fun hideWaitDialog() {

    }

    /**
     * 隐私政策和用户协议
     */
    private fun openPrivacylDialog() {
        val privacyProtocolDialog = PrivacyProtocolDialog.newInstance()
        privacyProtocolDialog.setClickListener(object : PrivacyProtocolDialog.ClickListener {
            override fun onDisagreeClick() {
                privacyProtocolDialog.dismiss()
                openTipsDialog()
            }

            override fun onAgreeClick() {
                SPStaticUtils.put(IS_AGREE_PRIVACY_PROTOCOL, true)
                privacyProtocolDialog.dismiss()
                //初始化userid和appswitch
                mvpPresenter?.initApp()
            }
        })
        privacyProtocolDialog.show(supportFragmentManager, "privacyProtocolDialog")
    }


    private fun openTipsDialog() {
        val tipsDialog = CustomDialog.instance("温馨提示", "请同意并接受《用户服务协议》和《隐私政策》全部条款后再开始使用我们的服务。", "下一步", "")
        tipsDialog.setClickListener(object : CustomDialog.CustomDialogListener {
            override fun onLeftBtnClick() {
                tipsDialog.dismiss()
                openPrivacylDialog()
            }

            override fun onRightBtnClick() {

            }
        })
        tipsDialog.show(supportFragmentManager, "tipsDialog")
    }


    override fun createPresenter(): WelcomePresenter {
        return WelcomePresenter(this)
    }

    override fun onError(message: String) {

    }





}