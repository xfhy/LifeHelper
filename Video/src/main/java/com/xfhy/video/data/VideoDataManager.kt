package com.xfhy.video.data

import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.provider.config.ApiConstants
import com.xfhy.video.data.api.VideoService
import com.xfhy.video.data.bean.CommentBean
import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.data.bean.VideoTypeBean
import io.reactivex.Flowable
import retrofit2.http.Query

/**
 * Created by xfhy on 2018/3/11 8:14
 * Description : 视频 数据管理
 */
object VideoDataManager {

    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 获取视频 kaiyanapp Service  实现
     */
    private val videoKYService: VideoService
        get() = mRetrofitFactory.create(VideoService::class.java, ApiConstants.VIDEO_BASE_KAIYAN_URL)

    /**
     * 获取精选视频列表
     */
    fun getHomeDataBeanList(): Flowable<HomeDataBean> {
        return videoKYService.loadLatestDailyList()
    }

    /**
     * 获取更多精选视频列表
     */
    fun getHomeDataBeanList(date: String, num: String): Flowable<HomeDataBean> {
        return videoKYService.loadMoreLatestDailyList(date, num)
    }

    /**
     * 获取视频分类
     */
    fun getVideoTypes(): Flowable<MutableList<VideoTypeBean>> {
        return videoKYService.getVideoTypes()
    }

    /**
     * 获取排行数据
     */
    fun getSortData(num: String, strategy: String): Flowable<VideoItemV4Bean> {
        return videoKYService.getSortData(num, strategy)
    }

    /**
     * 获取分类详情列表
     */
    fun getTypeDetailsList(id: String, start: String, num: String, strategy: String = "date"): Flowable<VideoItemV4Bean> {
        return videoKYService.getTypeDetailsList(id, start, num, strategy)
    }

    /**
     * 获取视频相关推荐
     */
    fun getVideoRelatedSuggestion(id: String): Flowable<VideoItemV4Bean> {
        return videoKYService.getVideoRelatedSuggestion(id)
    }

    /**
     * 获取视频的评论数据
     */
    fun getCommentData(videoId: String): Flowable<CommentBean> {
        return videoKYService.getCommentData(videoId)
    }

    /**
     * 获取视频的更多评论数据
     */
    fun getCommentData(lastId: String, videoId: String, num: String = "10", type: String = "video"):
            Flowable<CommentBean> {
        return videoKYService.getCommentData(lastId, videoId, num, type)
    }

    /**
     * 获取热搜关键词
     */
    fun getHotSearchKeys(): Flowable<MutableList<String>> {
        return videoKYService.getHotSearchKeys()
    }

    /**
     * 搜索视频
     */
    fun searchVideoByKey(query: String): Flowable<HomeDataBean> {
        return videoKYService.searchVideoByKey(query)
    }

    /**
     * 搜索视频加载更多
     */
    fun searchVideoByKey(start: String, num: String, query: String): Flowable<HomeDataBean> {
        return videoKYService.searchVideoByKey(start, num, query)
    }

}