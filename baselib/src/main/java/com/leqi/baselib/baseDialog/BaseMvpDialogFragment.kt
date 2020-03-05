package com.leqi.baselib.baseDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leqi.baselib.base.BasePresenter
import com.leqi.baselib.base.BaseView

import org.greenrobot.eventbus.EventBus


/**
 * 引入mvp架构的dialog
 * @param <P>
</P> */
abstract class BaseMvpDialogFragment<V:BaseView,P : BasePresenter<V>> : BaseDialogFragment() {

     var mvpPresenter: P? = null

    open  val isNeedEventBus: Boolean
        get() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mvpPresenter = createPresenter()
        if (mvpPresenter != null) mvpPresenter!!.attachView(this as V)
        if (isNeedEventBus) EventBus.getDefault().register(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun onDestroyView() {
        if (mvpPresenter != null) {
            mvpPresenter!!.detachView()
        }
        if (isNeedEventBus) EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    /**
     * init data
     * after initViewAndEvent()
     */
    protected abstract fun initData()

    /**
     * 创建presenter
     */
    protected abstract fun createPresenter(): P

}
