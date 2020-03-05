package com.leqi.xxcamera.module.camera.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.leqi.xxcamera.R
import com.leqi.xxcamera.model.bean.apiV2.ModelsResponse
import com.leqi.xxcamera.module.camera.adapter.ModelAdapter
import kotlinx.android.synthetic.main.choose_model_layout.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor


@SuppressLint("ViewConstructor")
class ChooseModelView : RelativeLayout {


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

    private var mModelData: ModelsResponse? = null
    private var mListdata: List<ModelsResponse.ModelGroup.Model>? = null

    private lateinit var mAdapter: ModelAdapter
    private var mOnItemClickListener: onItemClickListener? = null


    interface onItemClickListener {
        fun onClick(model: ModelsResponse.ModelGroup.Model)
    }


    fun initView() {
        LayoutInflater.from(context).inflate(R.layout.choose_model_layout, this, true)
        val recyclerview = find<RecyclerView>(R.id.modelRecyclerView)
        mAdapter = ModelAdapter()
        recyclerview.adapter = mAdapter
        womanTv.textColor = resources.getColor(R.color.white)
        womanTv.setOnClickListener {
            for (modelGroup in mModelData?.result?.iterator()!!) {
                if (modelGroup.group_name.contains("女")) {
                    mListdata = modelGroup.value
                }
            }
            mAdapter.setNewData(mListdata)
            womanTv.textColor = resources.getColor(R.color.white)
            manTv.textColor = resources.getColor(R.color.color666666)
            childTv.textColor = resources.getColor(R.color.color666666)

        }
        manTv.setOnClickListener {
            for (modelGroup in mModelData?.result?.iterator()!!) {
                if (modelGroup.group_name.contains("男")) {
                    mListdata = modelGroup.value
                }
            }
            mAdapter.setNewData(mListdata)
            womanTv.textColor = resources.getColor(R.color.color666666)
            manTv.textColor = resources.getColor(R.color.white)
            childTv.textColor = resources.getColor(R.color.color666666)
        }
        childTv.setOnClickListener {
            for (modelGroup in mModelData?.result?.iterator()!!) {
                if (modelGroup.group_name.contains("儿童")) {
                    mListdata = modelGroup.value
                }
            }
            mAdapter.setNewData(mListdata)
            womanTv.textColor = resources.getColor(R.color.color666666)
            manTv.textColor = resources.getColor(R.color.color666666)
            childTv.textColor = resources.getColor(R.color.white)
        }


        mAdapter.setOnItemClickListener { adapter, view, position ->
            mListdata?.get(position)?.let {
                mAdapter.setCurrent(position)
                mOnItemClickListener?.onClick(it) }

        }
    }

    public fun setData(modelData: ModelsResponse) {
        this.mModelData = modelData
        womanTv.performClick()
        mListdata?.get(0)?.let { mOnItemClickListener?.onClick(it)
            mAdapter.setCurrent(0)}
    }


    /**
     * 设置点击监听
     */
    public fun setOnGroupItemClickListener(listener: onItemClickListener) {
        this.mOnItemClickListener = listener
    }


}