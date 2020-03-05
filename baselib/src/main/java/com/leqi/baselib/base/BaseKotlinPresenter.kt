package com.leqi.baselib.base

import android.util.Log
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext


/**
 * @Description:    引入kotlin协程的 presenter
 * @Author:         ZHUYU
 * @CreateDate:     2020/2/27 14:03
 * @UpdateRemark:   更新说明：
 * @UpdateDate:     2020/2/27 14:03
 * @Version:        1.0
 */
abstract class BaseKotlinPresenter<V : BaseView> : BasePresenter<V>(), CoroutineScope {


    /**job用于控制协程,后面launch{}启动的协程,返回的job就是这个job对象*/
    private lateinit var job: Job
    /**继承CoroutineScope必须初始化coroutineContext变量 ,这个是标准写法,+其实是plus方法前面表示job,用于控制协程,后面是Dispatchers,指定启动的线程*/
    override val coroutineContext: CoroutineContext
        get() = job.plus(Dispatchers.Main)


    override fun attachView(view: V) {
        super.attachView(view)
        job = Job() //初始化
    }

    override fun detachView() {
        super.detachView()
        job.cancel() //结束当前协程
    }


    /**
     * 处理网络请求
     */
     fun <T> launchRequest(
        request: suspend () -> T?,
        onSuccess: (T) -> Unit = {},
        onFail: (Throwable) -> Unit = {},
        onNetError: () -> Unit = {}
    ): T? {
        launch {
            try {
               // val res: T? = withContext(Dispatchers.IO) { request() }  // IO线程中执行网络请求，成功后返回这里继续执行
                val res: T? = request() // IO线程中执行网络请求，成功后返回这里继续执行
                res?.let {
                    onSuccess(it)
                    return@let res
                }
            } catch (e: UnknownHostException) {
                //无网络时会报此异常
                onNetError()
            } catch (e: CancellationException) {
                Log.e("launchRequest", "job cancelled")
            } catch (e: Exception) {
                Log.e("launchRequest", "request caused exception")
                onFail(e)
            }

        }
        return null
    }


}