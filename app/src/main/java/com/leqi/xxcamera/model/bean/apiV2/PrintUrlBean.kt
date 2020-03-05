package com.leqi.lwcamera.model.bean.apiV2

class PrintUrlBean {

    /**
     * formal : {"imageUrl":"xxxx","serialNumber":"sssssss"}
     * origin : {"imageUrl":"xxxx","serialNumber":"sssssss"}
     */

    var formal: ImageBean? = null
    var origin: ImageBean? = null

    class ImageBean {
        /**
         * imageUrl : xxxx
         * serialNumber : sssssss
         */

        var imageUrl: String? = null
        var serialNumber: String? = null
    }
}
