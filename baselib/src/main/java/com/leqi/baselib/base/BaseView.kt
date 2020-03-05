package com.leqi.baselib.base

interface BaseView {



    fun showWaitDialog()

    fun hideWaitDialog()
    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 `null`
     */
    fun onError(message: String)




}
