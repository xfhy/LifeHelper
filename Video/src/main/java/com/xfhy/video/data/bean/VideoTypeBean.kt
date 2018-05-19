package com.xfhy.video.data.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by xfhy on 2018/3/11 20:12
 * Description : 视频分类
 */

data class VideoTypeBean(
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("name") val name: String? = "",
        @SerializedName("description") val description: String? = "",
        @SerializedName("bgPicture") val bgPicture: String? = "",
        @SerializedName("bgColor") val bgColor: String? = "",
        @SerializedName("headerImage") val headerImage: String? = ""
) : Serializable