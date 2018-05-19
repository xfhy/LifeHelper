package com.xfhy.zhihu.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/8 22:54
 * Description : 主题日报列表
 */

data class TopicDailyListBean(
        /**
         * 返回数目之限制（仅为猜测）
         */
        @SerializedName("limit") var limit: Int? = 0,
        /**
         * 已订阅条目
         */
        @SerializedName("subscribed") var subscribed: MutableList<Any?>? = mutableListOf(),
        /**
         * 其他条目
         */
        @SerializedName("others") var others: MutableList<OtherBean>? = mutableListOf()
) {
    data class OtherBean(
            /**
             * 颜色，作用未知
             */
            @SerializedName("color") var color: Int? = 0,
            /**
             * 供显示的图片地址
             */
            @SerializedName("thumbnail") var thumbnail: String? = "",
            /**
             * 主题日报的介绍
             */
            @SerializedName("description") var description: String? = "",
            /**
             * 该主题日报的编号
             */
            @SerializedName("id") var id: Int? = 0,
            /**
             * 供显示的主题日报名称
             */
            @SerializedName("name") var name: String? = ""
    )
}