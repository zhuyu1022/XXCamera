package com.leqi.baselib.baseDialog

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.leqi.baselib.R

abstract class BaseDialogFragment : DialogFragment() {


    /**
     * 是否显示再底部
     *
     * @return
     */
    open fun isShowInBottom(): Boolean{
        return false
    }

    /**
     * 是否显示在右侧
     * @return
     */
    open fun isShowInRight(): Boolean{
        return false
    }


    /**
     * 是否隐藏阴影
     *
     * @return
     */
    open fun isHideShadow(): Boolean{
        return false
    }


    /**
     * 背景是否透明
     *
     * @return
     */
    open fun isBackgroundTransparent(): Boolean{
        return false
    }

    @LayoutRes
    protected abstract fun getContentViewLayoutID(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            initArguments(bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(getContentViewLayoutID(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCanceledOnTouchOutside(false)
        initViewAndEvent(view)
    }

    override fun onStart() {
        super.onStart()
        if (isShowInBottom()) {
            showInBottom()
        }
        if (isShowInRight()) {
            showInRight()
        }
        if (isHideShadow()) {
            setNoNeedShadow()
        }
        if (isBackgroundTransparent()) {
            setBackgroundTransparent()
        }

    }

    /**
     * 设置显示在界面底部
     */
    private fun showInBottom() {
        //设置动画、位置、宽度等属性（注意一：必须放在onStart和onResume()中设置才有效）
        val window = dialog!!.window
        if (window != null) {
            // 注意二：一定要设置Background，如果不设置，window属性设置无效
            val layoutParams = window.attributes
            layoutParams.windowAnimations = R.style.BaseBottomDialogStyle//动画
            layoutParams.gravity = Gravity.BOTTOM // 位置
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT//宽度满屏
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.setBackgroundDrawableResource(android.R.color.transparent)//设置背景透明无边框
            window.attributes = layoutParams
        }

    }

    private fun showInRight() {
        //设置动画、位置、宽度等属性（注意一：必须放在onStart和onResume()中设置才有效）
        val window = dialog!!.window
        if (window != null) {
            // 注意二：一定要设置Background，如果不设置，window属性设置无效
            val layoutParams = window.attributes
            layoutParams.windowAnimations = R.style.BaseRightDialogStyle//动画
            layoutParams.gravity = Gravity.END // 位置
            window.setBackgroundDrawableResource(android.R.color.transparent)//设置背景透明无边框
            window.attributes = layoutParams
        }
    }

    /**
     * 设置阴影完全透明
     */
    private fun setNoNeedShadow() {
        //设置动画、位置、宽度等属性（注意一：必须放在onStart和onResume()中设置才有效）
        val window = dialog!!.window
        if (window != null) {
            //window.setDimAmount(0f);
            val lp = window.attributes
            lp.dimAmount = 0f
            lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            window.attributes = lp

        }
    }


    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }


    /**
     * 与 show方法对应，避免bug
     * zhuyu
     * 2019年3月13日添加，如有问题，直接删除该方法
     */
    override fun dismiss() {
        if (activity != null && !activity!!.isFinishing) {
            super.dismissAllowingStateLoss()
        }
    }


    /**
     * 设置背景透明
     */
    private fun setBackgroundTransparent() {
        val window = dialog!!.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


    protected abstract fun initArguments(bundle: Bundle)

    protected abstract fun initViewAndEvent(view: View)

}
