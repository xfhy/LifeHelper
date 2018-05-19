package com.xfhy.zhihu.data.bean

import com.google.gson.annotations.SerializedName

/**
 * 专栏列表
 */
data class ColumnDailyBean(@SerializedName("data")
                           var data: MutableList<DataBean>?) {
    data class DataBean(
            /**
             * 获取图像的地址
             */
            @SerializedName("thumbnail")
            var thumbnail: String? = "",
            /**
             * 专栏名称
             */
            @SerializedName("name")
            var name: String? = "",
            /**
             * 专栏描述
             */
            @SerializedName("description")
            var description: String? = "",
            /**
             * 专栏id
             */
            @SerializedName("id")
            var id: Int? = 0)
}
