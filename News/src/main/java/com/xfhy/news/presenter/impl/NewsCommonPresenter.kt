package com.xfhy.news.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.news.data.NewsDataManager
import com.xfhy.news.data.bean.NewsBean
import com.xfhy.news.presenter.NewsCommonContract

/**
 * Created by xfhy on 2018/3/18 14:02
 * Description : 新闻通用
 */
class NewsCommonPresenter(val mView: NewsCommonContract.View) : RxPresenter(),
        NewsCommonContract.Presenter {

    companion object {
        private val reqNum = 15
    }

    private var reqStartNum = 0
    private var mNewsType = ""

    override fun reqNewsByType(type: String) {
        mView.showLoading()
        mNewsType = type
        addSubscribe(NewsDataManager
                .getNewsByChannel(type, reqNum, reqStartNum)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<NewsBean>(mView) {
                    override fun onNext(t: NewsBean?) {
                        super.onNext(t)
                        mView.showNewsList(t?.result?.list)
                    }
                }))
    }

    override fun loadMoreData() {
        reqStartNum += reqNum
        addSubscribe(NewsDataManager
                .getNewsByChannel(mNewsType, reqNum, reqStartNum)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<NewsBean>(mView) {
                    override fun onNext(t: NewsBean?) {
                        super.onNext(t)
                        mView.loadMoreSuccess(t?.result?.list)
                    }
                }))
    }

    override fun refreshNewsData() {
        reqStartNum = 0
        addSubscribe(NewsDataManager
                .getNewsByChannel(mNewsType, reqNum, reqStartNum)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<NewsBean>(mView) {
                    override fun onNext(t: NewsBean?) {
                        super.onNext(t)
                        mView.showNewsList(t?.result?.list)
                    }
                }))
    }
}