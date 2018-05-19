package com.xfhy.weather.data.api

import com.xfhy.provider.config.ApiConstants
import com.xfhy.weather.data.bean.WeatherBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by xfhy on 2018/3/22 14:06
 * Description : 天气 api
 */
interface WeatherService {

    /**
     * 获取天气数据
     * @param location 经纬度数据 纬度在前,经度在后  用英文逗号隔开
     */
    @Headers("Authorization: APPCODE ${ApiConstants.NEWS_ALIYUN_APP_CODE}")
    @GET("weather/query")
    fun getWeatherData(@Query("location") location: String): Flowable<WeatherBean>

}