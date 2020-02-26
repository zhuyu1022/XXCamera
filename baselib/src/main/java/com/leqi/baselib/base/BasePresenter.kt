package com.leqi.baselib.base

import com.blankj.utilcode.util.LogUtils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


open class BasePresenter<V> : CoroutineScope {
    /**job用于控制协程,后面launch{}启动的协程,返回的job就是这个job对象*/
    private lateinit var job: Job
    /**继承CoroutineScope必须初始化coroutineContext变量 ,这个是标准写法,+其实是plus方法前面表示job,用于控制协程,后面是Dispatchers,指定启动的线程*/
    override val coroutineContext: CoroutineContext
        get() = job.plus(Dispatchers.Main)

    var mvpView: V? = null

    private var mCompositeDisposable: CompositeDisposable? = null

    fun attachView(view: V) {
        this.mvpView = view
        job = Job()
    }

    fun detachView() {
        this.mvpView = null
        job.cancel() //结束当前协程
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
