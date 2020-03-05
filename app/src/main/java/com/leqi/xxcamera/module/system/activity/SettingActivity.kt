package com.leqi.xxcamera.module.system.activity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.model.CountClick
import com.leqi.lwcamera.model.bean.apiV2.VersionBean
import com.leqi.lwcamera.module.common.dialog.CustomDialog

import com.leqi.lwcamera.module.home.mvp.view.SettingView
import com.leqi.lwcamera.util.APKUtil
import com.leqi.lwcamera.util.APKUtil.Companion.chooseMarket
import com.leqi.lwcamera.util.IntentUtil
import com.leqi.lwcamera.util.SpTool
import com.leqi.xxcamera.BuildConfig
import com.leqi.xxcamera.R
import com.leqi.xxcamera.base.BaseXxActivity
import com.leqi.xxcamera.module.system.dialog.ShareDialog
import com.leqi.xxcamera.module.system.mvp.presenter.SettingPresnseter
import com.umeng.analytics.MobclickAgent
import com.umeng.socialize.UMShareAPI
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class SettingActivity : BaseXxActivity<SettingView, SettingPresnseter>(), SettingView {


    private var isUpdateBtnClick = false

    private var mVersionBean: VersionBean? = null

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_setting
    }


    @SuppressLint("SetTextI18n")
    override fun initView() {
        setTitleText("设置")
        versionTv.text = "Version "+AppUtils.getAppVersionName()

    }

    override fun initData() {

        mvpPresenter?.getNewVersion()
    }

    override fun initEvent() {
        //检查更新
        checkUpdateLayout.setOnClickListener {
            isUpdateBtnClick = true
            showNewVersionDialog()
        }
        //复制用户id
        copyUserIdLayout.onClick {
            copyUserId()
        }

        customerLayout.onClick {
            startActivity<CustomerActivity>()
        }
        //攻略
        strategyLayout.onClick {
            // mvpPresenter.getGuideUrl()
            //暂时不用接口获取
            IntentUtil.intentWeb(Config.SHOOT_GUIDE_URL, applicationContext)

        }
        //推荐给朋友
        recommendLayout.onClick {
            openShareDialog()
        }
        //评分
        scoreLayout.onClick {
            gotoUpdateOrScore()
        }
        //隐私协议
        privacy_agreement.onClick {
            IntentUtil.intentWeb(Config.PRIVACY_URL, this@SettingActivity)
        }
        //证照信息
        certificateInfoTv.onClick { IntentUtil.intentWeb(Config.CERTIFICATE_URL, this@SettingActivity) }
    }


    private fun openShareDialog() {
        ShareDialog.newInstanceShareApp().show(supportFragmentManager, "shareDialog")
    }


    /**
     * 版本信息提示框
     */
    private fun showNewVersionDialog() {
        if (mVersionBean?.android_version == null) {
            mvpPresenter?.getNewVersion()
            return
        }
        if (BuildConfig.VERSION_CODE < mVersionBean?.android_version!!) {
            little_red_dot.visibility = View.VISIBLE
            val dialog = CustomDialog.instance("提示", "应用有更新！", "取消", "确认")
            dialog.setClickListener(object : CustomDialog.CustomDialogListener {
                override fun onLeftBtnClick() {
                }

                override fun onRightBtnClick() {
                    gotoUpdateOrScore()
                }
            })
            dialog.show(supportFragmentManager, "CustomDialog")
        } else {
            if (isUpdateBtnClick) {
                val dialog = CustomDialog.instance("提示", "当前版本是最新版本！", "知道了", "")
                dialog.show(supportFragmentManager, "CustomDialog2")
            }
        }
    }


    /**
     * 复制用户ID
     */
    private fun copyUserId() {
        MobclickAgent.onEvent(this, CountClick.AboutCopyUseId.key)
        ToastUtils.showShort("用户ID复制成功~")
        var cm = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var userid = SpTool(this).getUserKey
        //历史遗留问题，可能这个值是空的，所以去取userkey
        if (userid.isEmpty()) {
            userid = SpTool(this).getUserID
        }
        val clipData = ClipData.newPlainText("用户ID", userid)
        cm.setPrimaryClip(clipData)
    }

    /**
     * 更新应用或评分
     */
    private fun gotoUpdateOrScore() {
        MobclickAgent.onEvent(this, CountClick.AboutScore.key)

        chooseMarket(this, object : APKUtil.ShareToMarket {
            override fun onQQMarketSuccess() {
                //跳转到应用宝市场成功
            }

            override fun onOtherMarketSuccess() {
                //未成功跳转到应用宝市场 检测其他市场可用
            }

            override fun onFailed() {
                toast("未找到应用市场")
            }
        })
    }


    /**
     * 跳转攻略页面
     */
    override fun showGuide(url: String) {
        MobclickAgent.onEvent(this, CountClick.AboutShootGuide.key)
        if (!TextUtils.isEmpty(url)) {
            IntentUtil.intentWeb(url, this)
        }
    }

    override fun showWaitDialog() {

    }

    override fun hideWaitDialog() {

    }

    override fun showNewVersion(versionBean: VersionBean) {
        mVersionBean = versionBean
        showNewVersionDialog()
    }

    override fun onError(message: String) {
        toast(message)

    }

    override fun createPresenter(): SettingPresnseter {
        return SettingPresnseter()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}