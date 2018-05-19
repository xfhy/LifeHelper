package com.xfhy.zhihu.data.bean


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * 专栏详情里面的列表
 */
data class ColumnDailyDetailsBean(
        /**
         * 专栏详情里面的列表
         */
        @SerializedName("stories")
        var stories: MutableList<StoriesBean>?,
        /**
         * 专栏名称
         */
        @SerializedName("name")
        var name: String? = "",
        /**
         * 时间戳
         */
        @SerializedName("timestamp")
        var timestamp: Int? = 0) {

    data class StoriesBean(
            /**
             * 日期 yyyyMMdd
             */
            @SerializedName("date")
            var date: String? = "",
            /**
             * 日报图片  列表的小图片
             */
            @SerializedName("images")
            var images: List<String>?,
            /**
             * 显示日期
             */
            @SerializedName("display_date")
            var displayDate: String? = "",
            /**
             * 日报id
             */
            @SerializedName("id")
            var id: Int? = 0,
            /**
             * 作用未知  可能没有值   我将此字段排除了,不序列化
             */
            @Expose(serialize = false)
            var multipic: Boolean = false,
            /**
             * 日报标题
             */
            @SerializedName("title")
            var title: String? = "")
}


