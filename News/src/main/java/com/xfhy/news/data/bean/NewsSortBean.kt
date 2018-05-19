package com.xfhy.news.data.bean

import java.io.Serializable

/**
 * Created by xfhy on 2018/3/19 15:32
 * Description : 新闻排序bean
 *
 * 新闻类型,是否被选中
 */
data class NewsSortBean(var newsType: String = "", var isChecked: Boolean = true) : Serializable