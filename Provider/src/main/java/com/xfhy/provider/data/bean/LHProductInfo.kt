package com.xfhy.provider.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/25 18:52
 * Description : lifeHelper服务端的产品额外信息bean
 */

data class LHProductInfo(
        @SerializedName("likeCount") var likeCount: Int? = 0,
        @SerializedName("shareCount") var shareCount: Int? = 0,
        @SerializedName("commentCount") var commentCount: Int? = 0
)