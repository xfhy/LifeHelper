package com.xfhy.zhihu.data

import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.provider.config.ApiConstants
import com.xfhy.zhihu.data.api.ZhiHuService
import com.xfhy.zhihu.data.bean.*

import io.reactivex.Flowable

/**
 * Created by xfhy on 2018年3月10日16:44:56
 * Description : 知乎数据处理管理类
 */
object ZHDataManager {

    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 获取知乎Service  实现
     */
    private val zhiHuService: ZhiHuService
        get() = mRetrofitFactory.create(ZhiHuService::class.java, ApiConstants.ZHIHU_BASE_URL)

    /**
     * 获取最新日报列表
     *
     * @return 返回最新日报列表  被观察者 发射的类型是LatestDailyListBean
     */
    val latestDailyList: Flowable<LatestDailyListBean>
        get() = zhiHuService.latestDailyList

    /**
     * 获取主题日报列表
     */
    val topicDailyList: Flowable<TopicDailyListBean>
        get() = zhiHuService.topicDailyList

    /**
     * 获取专栏日报列表
     */
    val columnDailyList: Flowable<ColumnDailyBean>
        get() = zhiHuService.columnDailyList

    /**
     * 获取热门日报文章列表
     */
    val hotDailyList: Flowable<HotDailyBean>
        get() = zhiHuService.hotDailyList

    /**
     * 获取日报内容
     *
     * @param id 日报的id
     * @return 返回日报内容
     */
    fun getDailyContent(id: String): Flowable<DailyContentBean> {
        return zhiHuService.getDailyContent(id)
    }

    /**
     * 获取日报的额外信息
     *
     * @param id 日报的id
     * @return 返回日报的额外信息
     */
    fun getDailyExtraInfo(id: String): Flowable<DailyExtraInfoBean> {
        return zhiHuService.getDailyExtraInfo(id)
    }

    /**
     * 获取往期日报
     *
     *
     * 注意!!
     * 当url为https://news-at.zhihu.com/api/4/news/before/20170919
     * 实际上服务器返回的数据是20170918的
     *
     *
     * 当url为20170901,服务器返回的数据是20170831的
     *
     * @param date 日期  格式必须为yyyyMMdd  eg:20170924
     * @return 返回往期日报
     */
    fun getPastNews(date: String): Flowable<PastNewsBean> {
        return zhiHuService.getPastNews(date)
    }

    /**
     * 获取日报长评论
     *
     * @param id 日报id
     * @return 返回日报长评论
     */
    fun getDailyLongComments(id: String): Flowable<DailyCommentBean> {
        return zhiHuService.getDailyLongComments(id)
    }

    /**
     * 获取日报短评论
     *
     * @param id 日报id
     * @return 返回日报短评论
     */
    fun getDailyShortComments(id: String): Flowable<DailyCommentBean> {
        return zhiHuService.getDailyShortComments(id)
    }

    /**
     * 获取主题日报详情
     *
     * @param number 主题日报编号
     * @return 返回主题日报详情
     */
    fun getThemeDailyDetails(number: String): Flowable<ThemeDailyDetailsBean> {
        return zhiHuService.getThemeDailyDetails(number)
    }

    /**
     * 获取专栏日报详情列表
     *
     * @param id 专栏编号
     * @return 获取专栏日报详情列表
     */
    fun getColumnDailyDetailsList(id: String): Flowable<ColumnDailyDetailsBean> {
        return zhiHuService.getColumnDailyDetailsList(id)
    }

}
