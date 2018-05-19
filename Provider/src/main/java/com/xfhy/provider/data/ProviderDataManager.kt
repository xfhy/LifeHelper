package com.xfhy.provider.data

import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.provider.config.ApiConstants
import com.xfhy.provider.data.api.LHService
import com.xfhy.provider.data.bean.LHCollect
import com.xfhy.provider.data.bean.LHComment
import com.xfhy.provider.data.bean.LHProductInfo
import io.reactivex.Flowable

/**
 * Created by xfhy on 2018/3/25 16:47
 * Description : lifeHelper 通用数据管理
 */
object ProviderDataManager {

    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 获取lifeHelper Service  实现
     */
    private val lhService: LHService
        get() = mRetrofitFactory.create(LHService::class.java, ApiConstants.LIFE_HELPER_BASE_URL)

    /**
     * 查询用户总积分
     */
    fun getUserIntegral(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.getUserIntegral(bmobId)
    }

    /**
     * 查询用户今天是否已经签到
     */
    fun isSigned(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.isSigned(bmobId)
    }

    /**
     * 添加用户
     */
    fun addUser(bmobId: String, userName: String, phone: String, password: String):
            Flowable<BaseResp<String>> {
        return lhService.addUser(bmobId, userName, phone, password)
    }

    /**
     * 更新头像地址
     */
    fun updateAvatar(bmobId: String, userAvatarUrl: String): Flowable<BaseResp<String>> {
        return lhService.updateAvatar(bmobId, userAvatarUrl)
    }

    /**
     * 查询是否已收藏
     */
    fun isCollect(bmobId: String, productId: String): Flowable<BaseResp<String>> {
        return lhService.isCollect(bmobId, productId)
    }

    /**
     * 收藏产品
     * @param productType 1:知乎  2:视频
     */
    fun collectProduct(bmobId: String, productId: String,
                       productType: String): Flowable<BaseResp<String>> {
        return lhService.collectProduct(bmobId, productId, productType)
    }

    /**
     * 取消收藏
     */
    fun cancelCollect(bmobId: String,
                      productId: String): Flowable<BaseResp<String>> {
        return lhService.cancelCollect(bmobId, productId)
    }

    /**
     * 查询用户所有的收藏
     */
    fun getAllCollects(bmobId: String): Flowable<BaseResp<LHCollect>> {
        return lhService.getAllCollects(bmobId)
    }

    /**
     * 添加评论
     */
    fun addComment(bmobId: String, productId: String, content: String): Flowable<BaseResp<String>> {
        return lhService.addComment(bmobId, productId, content)
    }

    /**
     * 获取评论列表
     */
    fun getComments(productId: String): Flowable<BaseResp<MutableList<LHComment>>> {
        return lhService.getComments(productId)
    }

    /**
     * 增加分享数量
     */
    fun addShareCount(productId: String): Flowable<BaseResp<String>> {
        return lhService.addShareCount(productId)
    }

    /**
     * 获取产品额外信息(点赞,评论,分享)
     */
    fun getProductInfo(productId: String): Flowable<BaseResp<LHProductInfo>> {
        return lhService.getProductInfo(productId)
    }

    /**
     * 点赞
     */
    fun addLikeCount(bmobId: String,
                     productId: String): Flowable<BaseResp<String>> {
        return lhService.addLikeCount(bmobId, productId)
    }

    /**
     * 添加知乎阅读次数
     */
    fun addZhCount(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.addZhCount(bmobId)
    }

    /**
     * 添加视频阅读次数
     */
    fun addVideoCount(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.addVideoCount(bmobId)
    }

    /**
     * 添加新闻阅读次数
     */
    fun addNewsCount(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.addNewsCount(bmobId)
    }

    /**
     * 添加天气阅读次数
     */
    fun addWeatherCount(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.addWeatherCount(bmobId)
    }

    /**
     * 添加笑话阅读次数
     */
    fun addJokeCount(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.addJokeCount(bmobId)
    }

    /**
     * 添加技术阅读次数
     */
    fun addTechCount(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.addTechCount(bmobId)
    }

    /**
     * 获取用户最大兴趣点
     */
    fun getMaxInterest(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.getMaxInterest(bmobId)
    }

}