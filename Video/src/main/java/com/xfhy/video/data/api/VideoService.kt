package com.xfhy.video.data.api

import com.xfhy.video.data.bean.CommentBean
import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.data.bean.VideoTypeBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by xfhy on 2018/3/11 8:16
 * Description : 视频相关API
 */
interface VideoService {

    //ky base url http://baobab.kaiyanapp.com/api/

    //精选视频列表   http://baobab.kaiyanapp.com/api/v5/index/tab/feed
    //加载下一页     http://baobab.kaiyanapp.com/api/v5/index/tab/feed?date=1520557200000&num=2

    @GET("v5/index/tab/feed")
    fun loadLatestDailyList(): Flowable<HomeDataBean>

    /**
     * 获取精选视频列表
     */
    @GET("v5/index/tab/feed")
    fun loadMoreLatestDailyList(@Query("date") date: String, @Query("num") num: String):
            Flowable<HomeDataBean>

    /**
     * 获取分类数据
     */
    @GET("v4/categories/")
    fun getVideoTypes(): Flowable<MutableList<VideoTypeBean>>

    /**
     * 获取排行数据
     */
    @GET("v3/ranklist")
    fun getSortData(@Query("num") num: String, @Query("strategy") strategy: String): Flowable<VideoItemV4Bean>

    /**
     * 获取分类详情列表
     */
    @GET("v4/categories/videoList")
    fun getTypeDetailsList(@Query("id") id: String, @Query("start") start: String,
                           @Query("num") num: String, @Query("strategy") strategy: String = "date"):
            Flowable<VideoItemV4Bean>

    /**
     * 获取视频的相关推荐
     */
    @GET("v4/video/related")
    fun getVideoRelatedSuggestion(@Query("id") id: String): Flowable<VideoItemV4Bean>

    /**
     * 获取视频的评论数据
     */
    @GET("v2/replies/video")
    fun getCommentData(@Query("videoId") videoId: String): Flowable<CommentBean>

    /**
     * 获取视频的更多评论数据
     */
    @GET("v2/replies/video")
    fun getCommentData(@Query("lastId") lastId: String,
                       @Query("videoId") videoId: String,
                       @Query("num") num: String = "10",
                       @Query("type") type: String = "video"): Flowable<CommentBean>

    /**
     * 获取热搜关键词
     */
    @GET("v3/queries/hot")
    fun getHotSearchKeys(): Flowable<MutableList<String>>

    //http://baobab.kaiyanapp.com/api/v3/search?query=%E9%9C%8D%E9%87%91%E4%BC%A0+Hawking
    /**
     * 搜索视频
     */
    @GET("v3/search")
    fun searchVideoByKey(@Query("query") query: String): Flowable<HomeDataBean>

    //http://baobab.kaiyanapp.com/api/v3/search? start=10&num=10&query=%E9%98%85%E5%90%8E%E5%8D%B3%E7%9E%8E
    /**
     * 搜索视频下一页 加载更多
     */
    @GET("v3/search")
    fun searchVideoByKey(@Query("start") start: String,
                         @Query("num") num: String,
                         @Query("query") query: String): Flowable<HomeDataBean>

}