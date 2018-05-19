package com.xfhy.provider.config

import com.xfhy.provider.BuildConfig


/**
 * author xfhy
 * create at 2017/9/25 18:56
 * description：API常量类
 * 存放api的base_url
 */
object ApiConstants {

    /**
     * 知乎base_url
     */
    const val ZHIHU_BASE_URL = BuildConfig.ZHIHU_BASE_URL

    /**
     * 视频 url
     */
    const val VIDEO_BASE_KAIYAN_URL = BuildConfig.VIDEO_BASE_KAIYAN_URL

    /**
     * 新闻 url
     */
    const val NEWS_BASE_URL = BuildConfig.NEWS_BASE_URL

    /**
     * 新闻 appCode 阿里云市场
     */
    const val NEWS_ALIYUN_APP_CODE = BuildConfig.NEWS_ALIYUN_APP_CODE

    /**
     * Gank url
     */
    const val GANK_BASE_URL = BuildConfig.GANK_BASE_URL

    /**
     * Joke url
     */
    const val JOKE_TEXT_BASE_URL = BuildConfig.JOKE_TEXT_BASE_URL
    const val JOKE_VIDEO_BASE_URL = BuildConfig.JOKE_VIDEO_BASE_URL

    /**
     * weather url
     */
    const val WEATHER_BASE_URL = BuildConfig.WEATHER_BASE_URL

    /**
     * 必应 base url
     */
    const val BING_BASE_URL = BuildConfig.BING_BASE_URL

    /**
     * 金山词霸开放api
     */
    const val KINGSOFT_BASE_URL = BuildConfig.KINGSOFT_BASE_URL

    /**
     * lifeHelper服务器 base url
     */
    const val LIFE_HELPER_BASE_URL = BuildConfig.LIFE_HELPER_BASE_URL
    //测试--------------
    //const val LIFE_HELPER_BASE_URL = "http://172.17.42.34:8085/"

}
