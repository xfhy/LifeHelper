package com.xfhy.zhihu.data.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/8 22:47
 * Description : 往期日报
 */

data class PastNewsBean(
        /**
         * 日报日期
         */
		@SerializedName("date") var date: String? = "",
        /**
         * 对应日期的日报
         */
		@SerializedName("stories") var stories: MutableList<LatestDailyListBean.StoriesBean>? = mutableListOf())