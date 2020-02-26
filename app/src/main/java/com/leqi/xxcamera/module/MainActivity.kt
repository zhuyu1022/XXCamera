package com.leqi.xxcamera.module

import com.leqi.baselib.base.BaseActivity
import com.leqi.xxcamera.R
import com.leqi.xxcamera.http.RetrofitUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.toast


class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {

    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun onError(message: String) {

    }

    override fun initView() {

    }

    override fun initEvent() {
        button.setOnClickListener {

            val retrofit = RetrofitUtil.getInstanse()
            launch {
                val result = withContext(Dispatchers.IO) { retrofit.createApi().getNews() }
                delay(2000)
                tv.text = result.toString()
                toast("收到返回结果")
            }
        }
    }

    override fun initData() {

    }


}
