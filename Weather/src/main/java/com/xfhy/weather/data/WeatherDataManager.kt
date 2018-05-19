package com.xfhy.weather.data

import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.provider.config.ApiConstants
import com.xfhy.weather.data.api.BingService
import com.xfhy.weather.data.api.WeatherService
import com.xfhy.weather.data.bean.BingImageBean
import com.xfhy.weather.data.bean.WeatherBean
import io.reactivex.Flowable

/**
 * Created by xfhy on 2018/3/22 14:05
 * Description : 天气数据管理
 */
object WeatherDataManager {

    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 获取天气 Service  实现
     */
    private val weatherService: WeatherService
        get() = mRetrofitFactory.create(WeatherService::class.java, ApiConstants.WEATHER_BASE_URL)

    private val bingService: BingService
        get() = mRetrofitFactory.create(BingService::class.java, ApiConstants.BING_BASE_URL)

    /**
     * 获取天气数据
     * @param location 经纬度数据 纬度在前,经度在后  用英文逗号隔开
     */
    fun getWeatherData(location: String): Flowable<WeatherBean> {
        return weatherService.getWeatherData(location)
    }

    /**
     * 获取必应每日一图
     */
    fun getBingDailyImage(format: String = "js", idx: String = "0", n: String = "1"): Flowable<BingImageBean> {
        return bingService.getBingDailyImage(format, idx, n)
    }

}