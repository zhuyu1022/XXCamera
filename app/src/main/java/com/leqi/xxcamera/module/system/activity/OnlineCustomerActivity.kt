package com.leqi.xxcamera.module.system.activity

import android.widget.Toast
import com.leqi.baselib.base.BaseView
import com.leqi.lwcamera.config.Config
import com.leqi.xxcamera.R
import com.leqi.xxcamera.base.BaseXxActivity
import com.leqi.xxcamera.module.system.mvp.presenter.OnlineCustomerPresenter
import com.qiyukf.unicorn.api.ConsultSource
import com.qiyukf.unicorn.api.Unicorn

/**
 * @Description:   在线客服
 * @Author:         ZHUYU
 * @CreateDate:     2019/11/21 15:02
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2019/11/21 15:02
 * @Version:        1.0
 */
class OnlineCustomerActivity : BaseXxActivity<BaseView, OnlineCustomerPresenter>(), BaseView {

    override fun getContentViewLayoutID(): Int {
        return R.layout.online_customer_activity

    }

    override fun initView() {
        setTitleText("在线客服")
        val source = ConsultSource(null, "在线客服", null)
        source.faqGroupId = Config.FAQID
        val fragment = Unicorn.newServiceFragment("在线客服", source, null)
        // 七鱼未初始化或初始化失败
        if (fragment == null) {
            Toast.makeText(this, "暂时无法联系客服", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.containerLayout, fragment)
        try {
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
        }
    }

    override fun initEvent() {

    }

    override fun initData() {

    }


    override fun createPresenter(): OnlineCustomerPresenter {
        return OnlineCustomerPresenter()
    }

    override fun showWaitDialog() {

    }

    override fun hideWaitDialog() {

    }

    override fun onError(message: String) {

    }

}