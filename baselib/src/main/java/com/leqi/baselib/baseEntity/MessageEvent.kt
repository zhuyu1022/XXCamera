package com.leqi.baselib.baseEntity

/**
 * Eventbus事件类
 */
open class MessageEvent {
    /**
     * 唯一确定Eventbus发送者code
     */
    var code: String=""
    /**
     * 携带信息（可选）
     */
    var message: String=""

    constructor() {

    }

    constructor(code: String) {
        this.code = code
    }

    constructor(code: String, message: String) {
        this.code = code
    }
}
