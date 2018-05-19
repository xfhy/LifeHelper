package com.xfhy.news.data.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by xfhy on 2018/3/18 14:13
 * Description : 新闻item数据
 */

data class NewsBean(
        @SerializedName("status") var status: String? = "",
        @SerializedName("msg") var msg: String? = "",
        @SerializedName("result") var result: Result? = Result()
) : Serializable {
    data class Result(
            @SerializedName("channel") var channel: String? = "",
            @SerializedName("num") var num: String? = "",
            @SerializedName("list") var list: MutableList<Item>? = mutableListOf()
    ) : Serializable {
        data class Item(
                @SerializedName("title") var title: String? = "",
                @SerializedName("time") var time: String? = "",
                @SerializedName("src") var src: String? = "",
                @SerializedName("category") var category: String? = "",
                @SerializedName("pic") var pic: String? = "",
                @SerializedName("content") var content: String? = "",
                @SerializedName("url") var url: String? = "",
                @SerializedName("weburl") var weburl: String? = ""
        ) : Serializable
    }
}