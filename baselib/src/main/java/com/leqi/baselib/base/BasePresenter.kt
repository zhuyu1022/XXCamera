package com.leqi.baselib.base

import com.blankj.utilcode.util.LogUtils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


abstract class BasePresenter<V> {


    var mvpView: V? = null

    private var mCompositeDisposable: CompositeDisposable? = null

    open fun attachView(view: V) {
        this.mvpView = view

    }

    open fun detachView() {
        this.mvpView = null
        unDispose()//解除订阅
    }


    /**
     * 将 [Disposable] 添加到 [CompositeDisposable] 中统一管理
     * 停止正在执行的 RxJava 任务,避免内存泄漏(框架已自行处理)
     * @param disposable
     */
    fun addDispose(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    fun unDispose() {
        LogUtils.d("unDispose")
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

}
