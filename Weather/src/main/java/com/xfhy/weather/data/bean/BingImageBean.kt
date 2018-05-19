package com.xfhy.weather.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/25 12:22
 * Description : 必应每日一图
 *
 *  "url": "/az/hprichbg/rb/Poortersloge_ZH-CN11453345050_1920x1080.jpg",
"urlbase": "/az/hprichbg/rb/Poortersloge_ZH-CN11453345050",
 *
 */

data class BingImageBean(
        @SerializedName("images") var images: MutableList<Image>? = mutableListOf(),
        @SerializedName("tooltips") var tooltips: Tooltips? = Tooltips()
) {
    data class Tooltips(
            @SerializedName("loading") var loading: String? = "",
            @SerializedName("previous") var previous: String? = "",
            @SerializedName("next") var next: String? = "",
            @SerializedName("walle") var walle: String? = "",
            @SerializedName("walls") var walls: String? = ""
    )

    data class Image(
            @SerializedName("startdate") var startdate: String? = "",
            @SerializedName("fullstartdate") var fullstartdate: String? = "",
            @SerializedName("enddate") var enddate: String? = "",
            @SerializedName("url") var url: String? = "",
            @SerializedName("urlbase") var urlbase: String? = "",
            @SerializedName("copyright") var copyright: String? = "",
            @SerializedName("copyrightlink") var copyrightlink: String? = "",
            @SerializedName("quiz") var quiz: String? = "",
            @SerializedName("wp") var wp: Boolean? = false,
            @SerializedName("hsh") var hsh: String? = "",
            @SerializedName("drk") var drk: Int? = 0,
            @SerializedName("top") var top: Int? = 0,
            @SerializedName("bot") var bot: Int? = 0,
            @SerializedName("hs") var hs: List<Any?>? = listOf()
    )
}
