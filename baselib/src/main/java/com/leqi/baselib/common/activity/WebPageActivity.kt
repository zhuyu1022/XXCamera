package com.leqi.lwcamera.module.common.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.LogUtils
import com.leqi.baselib.R
import com.leqi.baselib.base.BaseActivity
import com.leqi.lwcamera.module.common.mvp.presenter.WebPagePresenter
import com.leqi.lwcamera.module.common.mvp.view.WebPageView
import kotlinx.android.synthetic.main.activity_webpage.*

/**
 * 描述：通用的webview
 * 作者：ZHUYU
 * 日期：2019/9/27 8:59
 */
class WebPageActivity : BaseActivity<WebPageView ,WebPagePresenter>(), WebPageView {
    override fun getContentViewLayoutID(): Int {
        return R.layout.activity_webpage
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient
        val url = intent.getStringExtra("url")
        webView.loadUrl(url)
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            LogUtils.d("progress: $newProgress")
            if (newProgress == 100) {
                webView_progress.visibility = View.GONE
            } else {
                webView_progress.progress = newProgress
            }
        }
    }
    /**
     * 在加载完成的时候将标题设置为网页标题
     */
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (URLUtil.isNetworkUrl(url)) {
                return false
            }
            if (url.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            } else {
                view.loadUrl(url)
            }
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (view.title != url) {

                setTitleText(view.title)
            } else {
                setTitleText(resources.getString(R.string.app_name))
            }
            LogUtils.d("view.title:${view.title}")
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun createPresenter(): WebPagePresenter {
        return WebPagePresenter()
    }

    override fun showWaitDialog() {

    }

    override fun hideWaitDialog() {

    }

    override fun onError(message: String) {
    }
}