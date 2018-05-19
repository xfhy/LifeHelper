package com.xfhy.zhihu.data.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/8 22:39
 * Description :日报额外信息
 */

data class DailyExtraInfoBean(
        /**
         * 长评论总数
         */
		@SerializedName("long_comments") var longComments: Int? = 0,
        /**
         * 点赞总数
         */
		@SerializedName("popularity") var popularity: Int? = 0,
        /**
         * 短评论总数
         */
		@SerializedName("short_comments") var shortComments: Int? = 0,
        /**
         * 评论总数
         */
		@SerializedName("comments") var comments: Int? = 0
)