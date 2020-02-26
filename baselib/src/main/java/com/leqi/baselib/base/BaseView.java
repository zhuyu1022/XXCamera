package com.leqi.baselib.base;


import androidx.annotation.NonNull;

public interface BaseView {

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    void onError(@NonNull String message);


}
