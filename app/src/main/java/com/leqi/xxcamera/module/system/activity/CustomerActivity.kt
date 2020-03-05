package com.leqi.xxcamera.module.system.activity

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.PhoneUtils
import com.leqi.lwcamera.config.Config
import com.leqi.lwcamera.model.CountClick
import com.leqi.lwcamera.model.bean.apiV2.PhoneNumberBean
import com.leqi.lwcamera.model.bean.apiV2.ProblemBean
import com.leqi.xxcamera.R
import com.leqi.xxcamera.base.BaseXxActivity
import com.leqi.xxcamera.module.system.adapter.ContactQuestionAdapter
import com.leqi.xxcamera.module.system.mvp.presenter.CustomerPresenter
import com.leqi.xxcamera.module.system.mvp.view.CustomerView
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.fragment_contact.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

/**
 * @Description:    客服反馈
 * @Author:         ZHUYU
 * @CreateDate:     2019/12/17 13:38
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2019/12/17 13:38
 * @Version:        1.0
 */
@SuppressLint("Registered")
class CustomerActivity : BaseXxActivity<CustomerView, CustomerPresenter>(), CustomerView {

    lateinit var mAdapter: ContactQuestionAdapter

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_contact
    }


    override fun initView() {
        setTitleText("客服中心")
        mAdapter = ContactQuestionAdapter(mutableListOf())
        questionRecyvlerview.adapter = mAdapter
    }

    override fun initEvent() {

        contactCallLayout.onClick {
            PhoneUtils.dial(Config.phoneNumber)
            MobclickAgent.onEvent(this@CustomerActivity, CountClick.CustomerPhone.key)
        }
        contactOnlineLayout.onClick {
            MobclickAgent.onEvent(this@CustomerActivity, CountClick.CustomerOnline.key)
            startActivity<OnlineCustomerActivity>()
        }
        feedbackbtn.onClick {
            MobclickAgent.onEvent(this@CustomerActivity, CountClick.CustomerNormalQuestion.key)
            startActivity<FeedbackActivity>()
        }
    }

    override fun initData() {
        mvpPresenter?.getPhoneNumber()
        mvpPresenter?.getQuestionData()
    }


    override fun showPhoneNumber(phoneNumberBean: PhoneNumberBean) {
        Config.phoneNumber = phoneNumberBean.phone_number!!
        Config.printNumber = phoneNumberBean.print_phone_number!!
        Config.weChatId = phoneNumberBean.wechat_id!!
    }

    override fun showQuestionList(listCache: ArrayList<ProblemBean.ProblemBean>) {
        mAdapter.setNewData(listCache)
    }

    override fun showWaitDialog() {

    }

    override fun hideWaitDialog() {

    }

    override fun createPresenter(): CustomerPresenter {
        return CustomerPresenter()
    }

    override fun onError(message: String) {
        toast(message)
    }
}