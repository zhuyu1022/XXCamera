package com.leqi.baselib.baseDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.leqi.baselib.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;



/**
 * 引入mvp架构的dialog
 * @param <P>
 */
public abstract class BaseMvpDialogFragment<P extends BasePresenter> extends BaseDialogFragment {

    protected P mvpPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        if (mvpPresenter != null) mvpPresenter.attachView(this);
        if(isNeedEventBus()) EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected  boolean isNeedEventBus(){return  false;}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        if(isNeedEventBus()) EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * init data
     * after initViewAndEvent()
     */
    protected abstract void initData();

    /**
     * 创建presenter
     */
    protected abstract P createPresenter();

}
