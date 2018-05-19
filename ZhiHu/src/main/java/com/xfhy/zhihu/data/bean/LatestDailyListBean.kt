package com.xfhy.zhihu.data.bean

import com.google.gson.annotations.SerializedName
import com.xfhy.library.adapter.entity.SectionEntity

/**
 * author xfhy
 * create at 2018年3月10日16:44:46
 * description：最新日报列表
 */
class LatestDailyListBean {

    /**
     * 日期   eg:20170927
     */
    var date: String? = null
    /**
     * 当日日报
     */
    var stories: MutableList<StoriesBean>? = mutableListOf()

    /**
     * 界面顶部 ViewPager 滚动显示的显示内容（子项格式同上）
     * （请注意区分此处的 `image` 属性与 `stories` 中的 `images` 属性）
     */
    @SerializedName("top_stories")
    var topStories: MutableList<TopStoriesBean>? = mutableListOf()

    class StoriesBean : SectionEntity<StoriesBean> {

        /**
         * 作用未知
         */
        var type: Int = 0
        /**
         * `url` 与 `share_url` 中最后的数字（应为内容的 id）
         */
        var id: Int = 0
        /**
         * 供 Google Analytics 使用
         */
        @SerializedName("ga_prefix")
        var gaPrefix: String? = ""
        /**
         * 新闻标题
         */
        var title: String? = ""
        /**
         * 图像地址（官方 API 使用数组形式。
         * 目前暂未有使用多张图片的情形出现，
         * 曾见无 `images` 属性的情况，请在使用中注意 ）
         */
        /**
         * 曾见无 `images` 属性的情况，请在使用中注意
         *
         * @return 图片地址集合
         */
        var images: List<String>? = listOf()

        constructor()

        constructor(isHeader: Boolean) : super(isHeader)

        constructor(isHeader: Boolean, header: String) : super(isHeader,header)

        override fun toString(): String {
            return "StoriesBean{" +
                    "type=" + type +
                    ", id=" + id +
                    ", gaPrefix='" + gaPrefix + '\'' +
                    ", title='" + title + '\'' +
                    ", images=" + images +
                    '}'
        }
    }

    class TopStoriesBean {

        /**
         * 图片地址
         */
        var image: String? = ""
        var type: Int = 0
        /**
         * `url` 与 `share_url` 中最后的数字（应为内容的 id）
         */
        var id: Int = 0
        /**
         * 供 Google Analytics 使用
         */
        @SerializedName("ga_prefix")
        var gaPrefix: String? = ""
        /**
         * 标题
         */
        var title: String? = ""

        override fun toString(): String {
            return "TopStoriesBean{" +
                    "image='" + image + '\'' +
                    ", type=" + type +
                    ", id=" + id +
                    ", gaPrefix='" + gaPrefix + '\'' +
                    ", title='" + title + '\'' +
                    '}'
        }
    }

    override fun toString(): String {
        return "LatestDailyListBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", topStories=" + topStories +
                '}'
    }
}
