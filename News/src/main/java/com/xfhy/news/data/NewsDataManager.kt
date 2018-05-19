package com.xfhy.news.data

import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.news.data.api.NewsService
import com.xfhy.news.data.bean.NewsBean
import com.xfhy.provider.config.ApiConstants
import io.reactivex.Flowable

/**
 * Created by xfhy on 2018/3/18 14:06
 * Description : 新闻数据
 */
object NewsDataManager {

    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 获取新闻 Service  实现
     */
    private val newsService: NewsService
        get() = mRetrofitFactory.create(NewsService::class.java, ApiConstants.NEWS_BASE_URL)

    /**
     * 获取新闻通过频道
     */
    fun getNewsByChannel(channel: String, num: Int, start: Int): Flowable<NewsBean> {
        return newsService.getNewsByChannel(channel, num, start)
    }

}