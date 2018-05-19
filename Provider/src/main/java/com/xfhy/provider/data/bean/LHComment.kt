package com.xfhy.provider.data.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/25 18:42
 * Description : lifeHelper服务端的评论bean
 */

data class LHComment(
        @SerializedName("content") var content: String? = "",
        @SerializedName("commentTime") var commentTime: Long? = 0,
        @SerializedName("bmobId") var bmobId: String? = "",
        @SerializedName("userName") var userName: String? = "",
        @SerializedName("userAvatarUrl") var userAvatarUrl: String? = ""
)