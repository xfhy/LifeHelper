package com.xfhy.provider.data.api

import com.xfhy.library.data.bean.BaseResp
import com.xfhy.provider.data.bean.LHCollect
import com.xfhy.provider.data.bean.LHComment
import com.xfhy.provider.data.bean.LHProductInfo
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by xfhy on 2018/3/25 16:53
 * Description : lifeHelper后台  api
 */
interface LHService {

    /**
     * 签到
     */
    @POST("user/sign")
    fun signIn(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 查询用户总积分
     */
    @GET("user/integral")
    fun getUserIntegral(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 查询用户今天是否已经签到
     */
    @GET("user/isSign")
    fun isSigned(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 添加用户
     */
    @POST("user/add")
    fun addUser(@Query("bmobId") bmobId: String,
                @Query("userName") userName: String,
                @Query("phone") phone: String,
                @Query("password") password: String): Flowable<BaseResp<String>>

    /**
     * 更新头像地址
     */
    @FormUrlEncoded  //application/x-www-form-urlencoded
    @PUT("user/updateAvatar")
    fun updateAvatar(@Field("bmobId") bmobId: String,
                     @Field("userAvatarUrl") userAvatarUrl: String): Flowable<BaseResp<String>>

    /**
     * 查询是否已收藏
     */
    @GET("collect/isCollect")
    fun isCollect(@Query("bmobId") bmobId: String,
                  @Query("productId") productId: String): Flowable<BaseResp<String>>

    /**
     * 收藏产品
     * @param productType 1:知乎  2:视频
     */
    @POST("collect/collectProduct")
    fun collectProduct(@Query("bmobId") bmobId: String,
                       @Query("productId") productId: String,
                       @Query("productType") productType: String): Flowable<BaseResp<String>>

    /**
     * 取消收藏
     */
    @POST("collect/cancelCollect")
    fun cancelCollect(@Query("bmobId") bmobId: String,
                      @Query("productId") productId: String): Flowable<BaseResp<String>>

    /**
     * 查询用户所有的收藏
     */
    @GET("collect/collects")
    fun getAllCollects(@Query("bmobId") bmobId: String): Flowable<BaseResp<LHCollect>>

    /**
     * 添加评论
     */
    @POST("comment/addComment")
    fun addComment(@Query("bmobId") bmobId: String,
                   @Query("productId") productId: String,
                   @Query("content") content: String): Flowable<BaseResp<String>>

    /**
     * 获取评论列表
     */
    @GET("comment/comments")
    fun getComments(@Query("productId") productId: String): Flowable<BaseResp<MutableList<LHComment>>>

    /**
     * 增加分享数量
     */
    @POST("product/share")
    fun addShareCount(@Query("productId") productId: String): Flowable<BaseResp<String>>

    /**
     * 获取产品额外信息(点赞,评论,分享)
     */
    @GET("product/info")
    fun getProductInfo(@Query("productId") productId: String): Flowable<BaseResp<LHProductInfo>>

    /**
     * 点赞
     */
    @POST("product/like")
    fun addLikeCount(@Query("bmobId") bmobId: String,
                     @Query("productId") productId: String): Flowable<BaseResp<String>>

    /**
     * 增加知乎阅读次数
     */
    @POST("/interest/zh")
    fun addZhCount(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 增加视频阅读次数
     */
    @POST("/interest/video")
    fun addVideoCount(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 增加新闻阅读次数
     */
    @POST("/interest/news")
    fun addNewsCount(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 增加天气阅读次数
     */
    @POST("/interest/weather")
    fun addWeatherCount(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 增加笑话阅读次数
     */
    @POST("/interest/joke")
    fun addJokeCount(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 增加技术阅读次数
     */
    @POST("/interest/tech")
    fun addTechCount(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

    /**
     * 查询用户最大兴趣点
     */
    @GET("/interest/max")
    fun getMaxInterest(@Query("bmobId") bmobId: String): Flowable<BaseResp<String>>

}