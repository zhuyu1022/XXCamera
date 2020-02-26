package com.leqi.baselib.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.leqi.baselib.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext


abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> : AppCompatActivity(), CoroutineScope {


    //job用于控制协程,后面launch{}启动的协程,返回的job就是这个job对象
    private lateinit var job: Job
    //继承CoroutineScope必须初始化coroutineContext变量 ,这个是标准写法,+其实是plus方法前面表示job,用于控制协程,后面是Dispatchers,指定启动的线程
    override val coroutineContext: CoroutineContext
        get() = job .plus( Dispatchers.Main)



     var mvpPresenter: P? = null
    /**下拉刷新*/
     var swipeRefreshLayout: SwipeRefreshLayout? = null
    /**标题*/
    private var tvTitle: TextView? = null
    /**返回键和右侧按键*/
    private var btnBack: ImageButton? = null
    private var btnRight: ImageButton? = null
    private var currentActivity: Activity? = null

    /**
     * 是否需要Eventbus  默认false
     */
     open  fun isNeedEventBus(): Boolean {
        return false
    }

    /**
     * 默认有标题栏，若不需要默认标题栏，重写并返回false
     * @return
     */
    open fun isNeedTitleBar(): Boolean {
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        //在onCreate中初始化job
        job = Job()
        mvpPresenter = createPresenter()
        if (mvpPresenter != null) mvpPresenter!!.attachView(this as V)
        super.onCreate(savedInstanceState)
        setContentView(getContentViewLayoutID())
        //设置状态栏
        initStatus()
        //设置竖屏
        ScreenUtils.setPortrait(this)
        initView()
        initEvent()
        initData()
        if (isNeedEventBus()) EventBus.getDefault().register(this)
    }


    private fun initStatus() {
        //设置状态栏浅色模式，文字会变为深色
        BarUtils.setStatusBarLightMode(this, true)
    }


    override fun setContentView(layoutResID: Int) {
        if (layoutResID != 0) {
            currentActivity = this
            super.setContentView(buildView(layoutResID))
        } else {
            throw IllegalArgumentException("You must return a right contentView layout resource Id")
        }
    }

    override fun onDestroy() {
        if (mvpPresenter != null) {
            mvpPresenter!!.detachView()
        }
        if (isNeedEventBus()) EventBus.getDefault().unregister(this)
        //当acitivity结束之后,我们不需要再请求网络了,结束当前协程
        job.cancel()
        super.onDestroy()
    }

    /**
     * 设置布局文件
     *
     * @param layoutResID
     */
    private fun buildView(@LayoutRes layoutResID: Int): View {
        val layoutInflater = LayoutInflater.from(this)
        val rootView = layoutInflater.inflate(R.layout.layout_base, null) as LinearLayout

        if (isNeedTitleBar()) {
            val flTitleBar = rootView.findViewById<FrameLayout>(R.id.fl_title_bar)
            val titleView: View = layoutInflater.inflate(R.layout.layout_default_title_bar, null)
            tvTitle = titleView.findViewById(R.id.tvEntName)
            btnBack = titleView.findViewById(R.id.btn_back)
            btnRight = titleView.findViewById(R.id.btn_right)
            tvTitle!!.isFocusable = true
            tvTitle!!.isFocusableInTouchMode = true
            tvTitle!!.requestFocus()
            setBackBtnDefaultClickListener()
            flTitleBar.addView(titleView)
        }
        val layoutContent = rootView.findViewById<FrameLayout>(R.id.ll_content)
        val layoutContentView = layoutInflater.inflate(layoutResID, null)
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
    open fun setRefreshDefaultAction() {
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
    open  fun setRefreshEnabled(enabled: Boolean) {
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
     * 设置标题栏文字
     *
     * @param resId
     */
    open fun setTitleText(@StringRes resId: Int) {
        if (tvTitle == null) return
        tvTitle!!.setText(resId)
    }

    /**
     * 设置返回按钮默认点击操作：finish
     */
    open fun setBackBtnDefaultClickListener() {
        btnBack!!.setOnClickListener {
            LogUtils.d(currentActivity!!.javaClass.name)
            onBackPressed()
        }
    }

    /**
     * 设置返回按钮点击事件
     *
     * @param clickListener
     */
    open fun setBackBtnClickListener(clickListener: View.OnClickListener) {
        if (btnBack != null)
            btnBack!!.setOnClickListener(clickListener)
    }

    /**
     * 设置标题栏右侧按钮点击事件
     *
     * @param clickListener
     */
    open fun setRightBtnClickListener(clickListener: View.OnClickListener) {
        if (btnRight != null)
            btnRight!!.setOnClickListener(clickListener)
    }

    /**
     * 设置标题栏右侧按钮图片资源
     *
     * @param resId
     */
    open fun setRightBtnImageRes(@DrawableRes resId: Int) {
        if (btnRight != null)
            btnRight!!.setImageResource(resId)
    }

    /**
     * bind layout resource file
     * 必须复写
     *
     * @return id of layout resource
     */
    @LayoutRes
    protected abstract fun getContentViewLayoutID(): Int

    /**
     * init all views and add events
     * 必须复写
     * before initData()
     */
    protected abstract fun initView()

    /**
     * init all events
     */
    protected abstract fun initEvent()

    /**
     * init data
     * 必须复写
     * after initViewAndEvent()
     */
    protected abstract fun initData()

    /**
     * 创建presenter
     * 必须复写
     *
     * @return
     */
    protected abstract fun createPresenter(): P

}
