package com.xfhy.zhihu.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/8 22:50
 * Description : 主题日报详情
 */

data class ThemeDailyDetailsBean(
        /**
         * 该主题日报中的文章列表
         */
        @SerializedName("stories") var stories: MutableList<Story>? = mutableListOf(),
        /**
         * 该主题日报的介绍
         */
        @SerializedName("description") var description: String? = "",
        /**
         * 该主题日报的背景图片（大图）
         */
        @SerializedName("background") var background: String? = "",
        /**
         * 颜色，作用未知
         */
        @SerializedName("color") var color: Int? = 0,
        /**
         * 该主题日报的名称
         */
        @SerializedName("name") var name: String? = "",
        /**
         * 背景图片的小图版本
         */
        @SerializedName("image") var image: String? = "",
        /**
         * 该主题日报的编辑（『用户推荐日报』中此项的值是一个空数组，
         * 在 App 中的主编栏显示为『许多人』，点击后访问该主题日报的介绍页面，请留意）
         */
        @SerializedName("editors") var editors: MutableList<Editor>? = mutableListOf(),
        /**
         * 图像的版权信息    一定要把版权信息加上
         */
        @SerializedName("image_source") var imageSource: String? = ""
) {
    data class Story(
            /**
             * 图像地址（其类型为数组。请留意在代码中处理无该属性与数组长度为 0 的情况）
             */
            @SerializedName("images") var images: MutableList<String>? = mutableListOf(),
            /**
             * 类型，作用未知
             */
            @SerializedName("type") var type: Int? = 0,
            /**
             * 日报的id
             */
            @SerializedName("id") var id: Int? = 0,
            /**
             * 消息的标题
             */
            @SerializedName("title") var title: String? = ""
    )

    data class Editor(
            /**
             * 主编的知乎用户主页
             */
            @SerializedName("url") var url: String? = "",
            /**
             * 主编的个人简介
             */
            @SerializedName("bio") var bio: String? = "",
            /**
             * 数据库中的唯一表示符
             */
            @SerializedName("id") var id: Int? = 0,
            /**
             * 主编的头像
             */
            @SerializedName("avatar") var avatar: String? = "",
            /**
             * 主编的用户名
             */
            @SerializedName("name") var name: String? = ""
    )
}