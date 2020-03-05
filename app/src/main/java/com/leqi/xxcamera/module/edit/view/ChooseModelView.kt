package com.leqi.xxcamera.module.camera.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.leqi.xxcamera.R
import com.leqi.xxcamera.model.bean.apiV2.BodySpecsResponse
import com.leqi.xxcamera.module.edit.adapter.SpecAdapter
import org.jetbrains.anko.find


@SuppressLint("ViewConstructor")
class ChooseSpecView : RelativeLayout {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
    }

    init {
        initView()
    }

    private var mList: List<BodySpecsResponse.BodySpecs>? = null
    private lateinit var mAdapter: SpecAdapter
    private var mOnItemClickListener: onItemClickListener? = null


    interface onItemClickListener {
        fun onClick(spec: BodySpecsResponse.BodySpecs)
    }


    fun initView() {
        LayoutInflater.from(context).inflate(R.layout.choose_spec_layout, this, true)
        val recyclerview = find<RecyclerView>(R.id.specRecyclerView)
        mAdapter = SpecAdapter()
        recyclerview.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            mList?.get(position)?.let {
                mAdapter.setCurrent(position)
                mOnItemClickListener?.onClick(it)
            }

        }
    }

    public fun setData(data: List<BodySpecsResponse.BodySpecs>) {
        this.mList = data
        mAdapter.setNewData(mList)
        mList?.get(0)?.let {
            mAdapter.setCurrent(0)
        }
    }


    /**
     * 设置点击监听
     */
    public fun setOnGroupItemClickListener(listener: onItemClickListener) {
        this.mOnItemClickListener = listener
    }


}