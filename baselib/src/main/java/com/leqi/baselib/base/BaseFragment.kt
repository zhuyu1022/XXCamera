package com.leqi.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.leqi.baselib.R

import org.greenrobot.eventbus.EventBus


/**
  * @Description:    BaseFragment封装
  * @Author:         ZHUYU
  * @CreateDate:     2020/2/28 9:44
  * @UpdateRemark:   更新说明：
  * @UpdateDate:     2020/2/28 9:44
  * @Version:        1.0
 */
abstract class BaseFragment<V : BaseView, P : BasePresenter<V>> : Fragment() {

    var mvpPresenter: P? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null


    open val isNeedEventBus: Boolean
        get() = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mvpPresenter = createPresenter()
        val layoutID = getContentViewLayoutID()
        if (mvpPresenter != null) mvpPresenter!!.attachView(this as V)
        if (isNeedEventBus) EventBus.getDefault().register(this)
        if (layoutID != 0) {
            return buildView(layoutID, inflater)
        } else {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
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
     * 设置布局文件
     *
     * @param layoutResID
     */
    private fun buildView(@LayoutRes layoutResID: Int, inflater: LayoutInflater): View {
        val rootView = inflater.inflate(R.layout.layout_base, null) as LinearLayout
        val layoutContent = rootView.findViewById<FrameLayout>(R.id.ll_content)
        val layoutContentView = inflater.inflate(layoutResID, null)
        layoutContent.addView(
            layoutContentView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout!!.setColorSchemeResources(R.color.colorAccent)
        swipeRefreshLayout!!.isEnabled = false//设置默认不可下拉刷新
        setRefreshDefaultAction()
        return rootView
    }


    /**
     * 设置下拉刷新默认操作
     * 默认重新获取数据，调用initData方法
     */
    private fun setRefreshDefaultAction() {
        if (swipeRefreshLayout == null) return
        swipeRefreshLayout!!.setOnRefreshListener { initData() }
    }

    /**
     * 设置下拉刷新事件监听
     *
     * @param onRefreshListener
     */
    open fun setOnRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
        if (swipeRefreshLayout == null) return
        swipeRefreshLayout!!.setOnRefreshListener(onRefreshListener)
    }

    /**
     * 设置是否允许下拉刷新
     *
     * @param enabled
     */
    open fun setRefreshEnabled(enabled: Boolean) {
        swipeRefreshLayout!!.isEnabled = enabled//设置可下拉刷新
    }

    /**
     * 设置下拉刷新控件状态
     *
     * @param refreshing
     */
    open fun setRefreshing(refreshing: Boolean) {
        if (swipeRefreshLayout == null) return
        swipeRefreshLayout!!.isRefreshing = refreshing
    }

    /**
     * bind layout resource file
     * @return id of layout resource
     */
    @LayoutRes
    protected abstract fun getContentViewLayoutID(): Int

    /**
     * init all views and add events
     * before initData()
     */
    protected abstract fun initView()

    /**
     * init all events
     */
    protected abstract fun initEvent()

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
