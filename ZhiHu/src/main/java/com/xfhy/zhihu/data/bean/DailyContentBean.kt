package com.xfhy.zhihu.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/8 22:34
 * Description : 日报内容
 */

data class DailyContentBean(
        /**
         * HTML 格式的日报内容
         */
        @SerializedName("body") var body: String? = "",
        /**
         * 图片的内容提供方。为了避免被起诉非法使用图片，在显示图片时最好附上其版权信息。
         */
        @SerializedName("image_source") var imageSource: String? = "",
        /**
         * 日报标题
         */
        @SerializedName("title") var title: String? = "",
        /**
         * 获得的图片同 `最新消息` 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
         */
        @SerializedName("image") var image: String? = "",
        /**
         * 供在线查看内容与分享至 SNS 用的 URL
         */
        @SerializedName("share_url") var shareUrl: String? = "",
        /**
         * js一般都是空的,不知道里面放的什么类型的
         * 供手机端的 WebView(UIWebView) 使用
         */
        @SerializedName("js") var js: List<String>? = listOf(),
        /**
         * 供 Google Analytics 使用
         */
        @SerializedName("ga_prefix") var gaPrefix: String? = "",
        /**
         * 缩略图
         */
        @SerializedName("images") var images: List<String>? = listOf(),
        /**
         * 新闻的类型
         */
        @SerializedName("type") var type: Int? = 0,
        /**
         * 新闻的 id
         */
        @SerializedName("id") var id: Int? = 0,
        /**
         * 供手机端的 WebView(UIWebView) 使用
         * 可知，知乎日报的文章浏览界面利用 WebView(UIWebView) 实现
         */
        @SerializedName("css") var css: List<String>? = listOf()
)