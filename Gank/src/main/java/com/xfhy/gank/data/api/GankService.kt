package com.xfhy.gank.data.api

import com.xfhy.gank.data.bean.GankCommonBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by xfhy on 2018/3/20 14:01
 * Description : Gank相关API
 */
interface GankService {

    /**
     * 获取分类数据
     */
    @GET("data/{gankType}/{num}/{page}")
    fun getGankData(@Path("gankType") gankType: String, @Path("num") num: String,
                    @Path("page") page: String): Flowable<GankCommonBean>

    /**
     * 获取随机数据
     */
    @GET("random/data/{gankType}/{num}")
    fun getRandomData(@Path("gankType") gankType: String, @Path("num") num: String): Flowable<GankCommonBean>

}