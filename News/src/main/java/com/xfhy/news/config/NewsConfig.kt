package com.xfhy.news.config

import com.xfhy.news.data.bean.NewsSortBean

/**
 * Created by xfhy on 2018/3/19 15:26
 * Description : 新闻类型
 */
object NewsConfig {
    /**
     * 新闻的标题栏
     */
    val NEWS_TYPE = mutableListOf(
            NewsSortBean("头条", true),
            NewsSortBean("新闻", true),
            NewsSortBean("财经", true),
            NewsSortBean("体育", true),
            NewsSortBean("娱乐", true),
            NewsSortBean("军事", true),
            NewsSortBean("教育", true),
            NewsSortBean("科技", true),
            NewsSortBean("NBA", true),
            NewsSortBean("股票", true),
            NewsSortBean("星座", true),
            NewsSortBean("女性", true),
            NewsSortBean("健康", true),
            NewsSortBean("育儿", true))

    /**
     * news 排序 SharedPreferences 键
     */
    val NEWS_SORT_CONFIG = "news_sort_config"
}