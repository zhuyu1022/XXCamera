package com.leqi.xxcamera.base;

import com.leqi.baselib.base.BaseActivity;
import com.leqi.baselib.base.BasePresenter;
import com.leqi.baselib.base.BaseView;
import com.leqi.xxcamera.module.system.dialog.InProductionDialog;
import com.umeng.analytics.MobclickAgent;


/**
  * @Description:   根据就业务进行Activity二次封装
  * @CreateDate:     2019/12/3 15:32
  * @UpdateRemark:   更新说明：
  * @UpdateDate:     2019/12/3 15:32
  * @Version:        1.0
 */
public abstract  class BaseXxActivity<V extends BaseView,P extends BasePresenter<V>> extends BaseActivity<V,P> {
    private InProductionDialog mInProductionDialog;
    /**
     * 显示证件照制作中对话框
     */
    public void showInProductionDialog(String title) {

        mInProductionDialog = InProductionDialog.Companion.newInstance(title);
        mInProductionDialog.show(getSupportFragmentManager(), "mInProductionDialog");
    }

    /**
     * 隐藏证件照制作中对话框
     */
    public void hideInProductionDialog() {
        if (mInProductionDialog != null){
            mInProductionDialog.dismiss();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }




}
