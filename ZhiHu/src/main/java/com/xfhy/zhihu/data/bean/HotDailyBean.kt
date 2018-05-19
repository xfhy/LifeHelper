package com.xfhy.zhihu.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/8 22:41
 * Description : 热门日报
 *
 * 请注意！此 API 仍可访问，但是其内容未出现在最新的『知乎日报』 App 中。
 */

data class HotDailyBean(
        /**
         * 日报列表
         */
        @SerializedName("recent") var recent: MutableList<RecentBean>? = mutableListOf()
) {
    data class RecentBean(
            /**
             * 日报id
             */
            @SerializedName("news_id") var newsId: Int? = 0,
            /**
             * 日报详情url 可以直接使用的   返回{@link DailyContentBean}的是JSON数据
             */
            @SerializedName("url") var url: String? = "",
            /**
             * 日报小图片  (用于列表展示时的小图片)
             */
            @SerializedName("thumbnail") var thumbnail: String? = "",
            /**
             * 日报标题
             */
            @SerializedName("title") var title: String? = ""
    )
}