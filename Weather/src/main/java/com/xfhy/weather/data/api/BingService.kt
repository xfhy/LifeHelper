package com.xfhy.weather.data.api

import com.xfhy.weather.data.bean.BingImageBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by xfhy on 2018/3/25 12:31
 * Description : 必应 service
 */
interface BingService {

    /**
     * 获取必应每日一图
     */
    @GET("HPImageArchive.aspx")
    fun getBingDailyImage(@Query("format") format: String = "js",
                          @Query("idx") idx: String = "0",
                          @Query("n") n: String = "1"): Flowable<BingImageBean>

}