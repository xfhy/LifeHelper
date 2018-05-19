package com.xfhy.weather.presenter

import android.content.Context
import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.weather.data.bean.WeatherBean

/**
 * Created by xfhy on 2018/3/22 12:46
 * Description : 天气Contract
 */
interface WeatherContract {

    interface Presenter : BasePresenter {

        /**
         * 获取必应每日一图
         */
        fun reqBingImage()

        /**
         * 定位当前位置
         */
        fun locateCurrentLocation(context: Context?)

        /**
         * 请求天气数据
         * @param latitude 纬度
         * @param longitude 经度
         */
        fun reqWeatherData(latitude: Double, longitude: Double)

        /**
         * 刷新数据
         */
        fun refreshData()

    }

    interface View : BaseView {

        /**
         * 展示必应每日一图
         */
        fun showBingImageBg(url: String)

        /**
         * 展示天气数据
         */
        fun showWeatherData(weatherData: WeatherBean.Result?)

        /**
         * 当用户未打开GPS时，提示用户开启GPS
         */
        fun showNoGPSNote()
    }

}