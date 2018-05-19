package com.xfhy.video.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.video.data.VideoDataManager
import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.data.bean.VideoInfoBean
import com.xfhy.video.presenter.VideoHomeContract

/**
 * Created by xfhy on 2018/3/10 22:18
 * Description : 视频 首页 presenter
 */
class VideoHomePresenter : RxPresenter(), VideoHomeContract.Presenter {

    /**
     * 下一页视频地址
     */
    private var mNextPageUrl: String = ""
    var mView: VideoHomeContract.View? = null

    override fun reqVideoDailyDataFromNet() {
        mView?.showLoading()
        addSubscribe(VideoDataManager.getHomeDataBeanList()
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<HomeDataBean>(mView) {
                    override fun onNext(t: HomeDataBean?) {
                        super.onNext(t)
                        //将header标记出来
                        t?.itemList?.forEach { it ->
                            if (it.type == "textCard") {
                                it.isHeader = true
                                it.header = it.data?.text
                            }
                        }
                        mView?.showVideoItemList(t)
                        //记录加载下一页的地址
                        t?.nextPageUrl?.let { mNextPageUrl = it }
                    }
                }))

    }

    override fun loadMoreData() {
        //http://baobab.kaiyanapp.com/api/v5/index/tab/feed?date= 1520557200000 &num= 2
        val split = mNextPageUrl.split("date=")
        if (split.size == 2) {
            val realData = split[1].split("&num=")
            if (realData.size == 2) {
                addSubscribe(VideoDataManager.getHomeDataBeanList(realData[0], realData[1])
                        .compose(SchedulerUtils.ioToMain())
                        .subscribeWith(object : CommonSubscriber<HomeDataBean>(mView) {
                            override fun onNext(t: HomeDataBean?) {
                                super.onNext(t)
                                //将header标记出来
                                t?.itemList?.forEach { it ->
                                    if (it.type == "textCard") {
                                        it.isHeader = true
                                        it.header = it.data?.text
                                    }
                                }
                                //记录加载下一页的地址
                                t?.nextPageUrl?.let { mNextPageUrl = it }
                                //加载成功
                                mView?.loadMoreSuccess(t)
                            }
                        }))
                return
            }
        }
        //展示加载更多失败
        mView?.loadMoreFailed()
    }

}