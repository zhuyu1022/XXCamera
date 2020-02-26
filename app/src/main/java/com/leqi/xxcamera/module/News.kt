package com.leqi.xxcamera.module

import java.io.Serializable

class News : Serializable {

    var reason: String = ""
    var error_code: String = ""
    var result: Result? = null

    class Result : Serializable {
        var stat: String = ""
        var data: List<NewsBean>? = null
        class NewsBean{
            var uniquekey: String = ""
            var title: String = ""
            var date: String = ""
            var category: String = ""
            var author_name: String = ""
            var url: String = ""
            var thumbnail_pic_s: String = ""
            override fun toString(): String {
                return "NewBean(uniquekey='$uniquekey', title='$title', date='$date', category='$category', author_name='$author_name', url='$url', thumbnail_pic_s='$thumbnail_pic_s')"
            }
        }

        override fun toString(): String {
            return "Result(stat='$stat', data=$data)"
        }


    }

    override fun toString(): String {
        return "News(reason='$reason', error_code='$error_code', new=$result)"
    }
}