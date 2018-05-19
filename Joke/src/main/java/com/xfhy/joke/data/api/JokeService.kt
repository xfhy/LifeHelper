package com.xfhy.joke.data.api

import com.xfhy.joke.data.bean.JokeBean
import com.xfhy.joke.data.bean.JokeVideoBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by xfhy on 2018/3/21 10:07
 * Description : 百思不得姐 api
 */
interface JokeService {

    /**
     * 获取段子
     */
    @GET("topic/tag-topic/64/hot/budejie-android-6.9.2/{date}-20.json")
    fun getJoke(@Path("date") date: String): Flowable<JokeBean>

    /**
     * 获取搞笑视频
     */
    @GET("topic/list/chuanyue/41/budejie-android-6.9.4/{date}-20.json")
    fun getFunnyVideo(@Path("date") date: String): Flowable<JokeVideoBean>


}