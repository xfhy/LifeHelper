package com.xfhy.news.data.api

import com.xfhy.news.data.bean.NewsBean
import com.xfhy.provider.config.ApiConstants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by xfhy on 2018/3/18 14:09
 * Description : 新闻相关api
 */
interface NewsService {

    //http://jisunews.market.alicloudapi.com/news/search
    //http://jisunews.market.alicloudapi.com/news/get

    /*
    * channel	STRING	必选	频道
        num	INT	可选	数量 默认10，最大40
        start	INT	可选	起始位置，默认0
    * */
    //http://jisunews.market.alicloudapi.com/news/channel
    @Headers("Authorization: APPCODE ${ApiConstants.NEWS_ALIYUN_APP_CODE}")
    @GET("news/get")
    fun getNewsByChannel(@Query("channel") channel: String,
                         @Query("num") num: Int = 10,
                         @Query("start") start: Int = 0): Flowable<NewsBean>
}