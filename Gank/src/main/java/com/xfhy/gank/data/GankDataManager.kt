package com.xfhy.gank.data

import com.xfhy.gank.data.api.GankService
import com.xfhy.gank.data.bean.GankCommonBean
import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.provider.config.ApiConstants
import io.reactivex.Flowable

/**
 * Created by xfhy on 2018/3/20 13:54
 * Description : Gank数据管理
 */
object GankDataManager {

    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 获取视频 kaiyanapp Service  实现
     */
    private val gankService: GankService
        get() = mRetrofitFactory.create(GankService::class.java, ApiConstants.GANK_BASE_URL)

    /**
     * 获取干货数据
     */
    fun getGankData(gankType: String, num: String, page: String): Flowable<GankCommonBean> {
        return gankService.getGankData(gankType, num, page)
    }

    /**
     * 获取随机数据
     */
    fun getRandomData(gankType: String, num: String): Flowable<GankCommonBean> {
        return gankService.getRandomData(gankType, num)
    }

}