package com.leqi.baselib.baseDialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.leqi.baselib.R;

public abstract class BaseDialogFragment extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            initArguments(bundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getContentViewLayoutID(), container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        initViewAndEvent(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowInBottom()) {
            showInBottom();
        }
        if (isShowInRight()) {
            showInRight();
        }
        if (isHideShadow()) {
            setNoNeedShadow();
        }
        if (isBackgroundTransparent()) {
            setBackgroundTransparent();
        }

    }

    /**
     * 设置显示在界面底部
     */
    private void showInBottom() {
        //设置动画、位置、宽度等属性（注意一：必须放在onStart和onResume()中设置才有效）
        Window window = getDialog().getWindow();
        if (window != null) {
            // 注意二：一定要设置Background，如果不设置，window属性设置无效
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.windowAnimations = R.style.BaseBottomDialogStyle;//动画
            layoutParams.gravity = Gravity.BOTTOM; // 位置
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;//宽度满屏
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setBackgroundDrawableResource(android.R.color.transparent);//设置背景透明无边框
            window.setAttributes(layoutParams);
        }

    }
    private void showInRight() {
        //设置动画、位置、宽度等属性（注意一：必须放在onStart和onResume()中设置才有效）
        Window window = getDialog().getWindow();
        if (window != null) {
            // 注意二：一定要设置Background，如果不设置，window属性设置无效
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.windowAnimations = R.style.BaseRightDialogStyle;//动画
            layoutParams.gravity = Gravity.END; // 位置
            window.setBackgroundDrawableResource(android.R.color.transparent);//设置背景透明无边框
            window.setAttributes(layoutParams);
        }
    }
    /**
     * 设置阴影完全透明
     */
    private void setNoNeedShadow() {
        //设置动画、位置、宽度等属性（注意一：必须放在onStart和onResume()中设置才有效）
        Window window = getDialog().getWindow();
        if (window != null) {
            //window.setDimAmount(0f);
            WindowManager.LayoutParams lp= window.getAttributes();
            lp.dimAmount=0;
            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(lp);

        }
    }



    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }


    /**
     * 与 show方法对应，避免bug
     * zhuyu
     * 2019年3月13日添加，如有问题，直接删除该方法
     */
    @Override
    public void dismiss() {
        if ( getActivity() != null && !getActivity().isFinishing()) {
            super.dismissAllowingStateLoss();
        }
    }



    /**
     * 设置背景透明
     */
    private void setBackgroundTransparent() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);//设置背景透明无边框
        }
    }


    /**
     * 是否显示再底部
     *
     * @return
     */
    protected boolean isShowInBottom() {
        return false;
    }

    /**
     * 是否显示在右侧
     * @return
     */
    protected boolean isShowInRight() {
        return false;
    }

    /**
     * 是否隐藏阴影
     *
     * @return
     */
    protected boolean isHideShadow() {
        return false;
    }

    /**
     * 背景是否透明
     *
     * @return
     */
    protected boolean isBackgroundTransparent() {
        return false;
    }


    protected abstract void initArguments(@NonNull Bundle bundle);

    @LayoutRes
    protected abstract int getContentViewLayoutID();

    protected abstract void initViewAndEvent(View view);

}
