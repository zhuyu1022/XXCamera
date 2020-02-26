package com.leqi.baselib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.leqi.baselib.R;

import org.greenrobot.eventbus.EventBus;


/**
 * @author zhoucl
 * @date 2019/1/15 14:42
 * @des
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected P mvpPresenter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    private int titleViewResId = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        int layoutID = getContentViewLayoutID();
        if (mvpPresenter != null) mvpPresenter.attachView(this);
        if (isNeedEventBus()) EventBus.getDefault().register(this);
        if (layoutID != 0) {
            titleViewResId = getTitleViewLayoutID();
            return buildView(layoutID, inflater);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    protected boolean isNeedEventBus() {
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();
        initData();
    }

    @Override
    public void onDestroyView() {
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        if (isNeedEventBus()) EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * 设置布局文件
     *
     * @param layoutResID
     */
    private View buildView(@LayoutRes int layoutResID, LayoutInflater inflater) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.layout_base, null);

        if (titleViewResId != 0) {
            FrameLayout flTitleBar = rootView.findViewById(R.id.fl_title_bar);
            View titleView = inflater.inflate(titleViewResId, null);
            flTitleBar.addView(titleView);
        }
        FrameLayout layoutContent = rootView.findViewById(R.id.ll_content);
        View layoutContentView = inflater.inflate(layoutResID, null);
        layoutContent.addView(layoutContentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setEnabled(false);//设置默认不可下拉刷新
        setRefreshDefaultAction();
        return rootView;
    }


    /**
     * 设置下拉刷新默认操作
     * 默认重新获取数据，调用initData方法
     */
    private void setRefreshDefaultAction() {
        if (swipeRefreshLayout == null) return;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    /**
     * 设置下拉刷新事件监听
     *
     * @param onRefreshListener
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        if (swipeRefreshLayout == null) return;
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 设置是否允许下拉刷新
     *
     * @param enabled
     */
    public void setRefreshEnabled(boolean enabled) {
        swipeRefreshLayout.setEnabled(enabled);//设置可下拉刷新
    }

    /**
     * 设置下拉刷新控件状态
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        if (swipeRefreshLayout == null) return;
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    /**
     * 设置标题栏布局文件，不复写此方法默认无标题栏
     *
     * @return
     */
    @LayoutRes
    protected int getTitleViewLayoutID() {
        return titleViewResId;
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    @LayoutRes
    protected abstract int getContentViewLayoutID();

    /**
     * init all views and add events
     * before initData()
     */
    protected abstract void initView();

    /**
     * init all events
     */
    protected abstract void initEvent();

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
