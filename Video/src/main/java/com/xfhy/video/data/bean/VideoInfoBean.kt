package com.xfhy.video.data.bean

import java.io.Serializable

/**
 * Created by xfhy on 2018/3/17 16:11
 * Description : 自己建的 专门用来数据传递视频信息的bean
 */
data class VideoInfoBean(var title: String? = "",
                         var category: String? = "",
                         var duration: Int? = 0,
                         var description: String? = "",
                         var collectionCount: Int? = 0,
                         var shareCount: Int? = 0,
                         var replyCount: Int? = 0,
                         var raw: String? = "",
                         var highUrl: String? = "",
                         var normalUrl: String? = "",
                         var id: Int? = 0) : Serializable