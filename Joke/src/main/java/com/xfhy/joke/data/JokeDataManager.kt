package com.xfhy.joke.data

import com.xfhy.joke.data.api.JokeService
import com.xfhy.joke.data.bean.JokeBean
import com.xfhy.joke.data.bean.JokeVideoBean
import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.provider.config.ApiConstants
import io.reactivex.Flowable

/**
 * Created by xfhy on 2018/3/21 9:47
 * Description : joke 数据管理
 */
object JokeDataManager {
    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 笑话 Service  实现
     */
    private val jokeTextService: JokeService
        get() = mRetrofitFactory.create(JokeService::class.java, ApiConstants.JOKE_TEXT_BASE_URL)
    /**
     * 笑话 Service  实现
     */
    private val jokeVideoService: JokeService
        get() = mRetrofitFactory.create(JokeService::class.java, ApiConstants.JOKE_VIDEO_BASE_URL)

    /**
     * 获取搞笑视频
     */
    fun getFunnyVideo(date: String): Flowable<JokeVideoBean> {
        return jokeVideoService.getFunnyVideo(date)
    }

    /**
     * 获取内涵段子
     */
    fun getJoke(date: String): Flowable<JokeBean> {
        return jokeTextService.getJoke(date)
    }

}