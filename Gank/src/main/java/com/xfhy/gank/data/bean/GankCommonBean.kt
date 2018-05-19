package com.xfhy.gank.data.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/20 13:57
 * Description : Gank通用 bean
 */

data class GankCommonBean(
        @SerializedName("error") var error: Boolean? = false,
        @SerializedName("results") var results: MutableList<Result>? = mutableListOf()
) {
    data class Result(
            @SerializedName("_id") var id: String? = "",
            @SerializedName("createdAt") var createdAt: String? = "",
            @SerializedName("desc") var desc: String? = "",
            @SerializedName("publishedAt") var publishedAt: String? = "",
            @SerializedName("source") var source: String? = "",
            @SerializedName("type") var type: String? = "",
            @SerializedName("url") var url: String? = "",
            @SerializedName("used") var used: Boolean? = false,
            @SerializedName("who") var who: String? = "",
            @SerializedName("images") var images: MutableList<String>? = mutableListOf(),
            @Expose(serialize = false) var gankBg: String? = ""
    )
}