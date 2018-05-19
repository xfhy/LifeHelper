package com.xfhy.provider.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/25 23:12
 * Description : LH收藏
 */

data class LHCollect(
        @SerializedName("collectId") var collectId: Int? = 0,
        @SerializedName("bmobId") var bmobId: String? = "",
        @SerializedName("productId") var productId: Int? = 0,
        @SerializedName("productType") var productType: Int? = 0
)