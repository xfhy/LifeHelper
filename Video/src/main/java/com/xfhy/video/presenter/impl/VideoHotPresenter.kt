package com.xfhy.video.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.video.data.VideoDataManager
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.fragment.VideoHotFragment
import com.xfhy.video.presenter.VideoHotContract

/**
 * Created by xfhy on 2018/3/10 22:18
 * Description : 视频 排行 presenter
 */
class VideoHotPresenter : RxPresenter(), VideoHotContract.Presenter {
    var mView: VideoHotFragment? = null

    companion object {
        const val REQ_COUNT = "10"
        const val WEEKLY_SORT_QUERY = "weekly"
        const val MONTHLY_SORT_QUERY = "monthly"
        const val ALL_SORT_QUERY = "historical"
    }

    override fun reqWeeklySortData() {
        mView?.showLoading()
        addSubscribe(VideoDataManager.getSortData(REQ_COUNT, WEEKLY_SORT_QUERY)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<VideoItemV4Bean>(mView) {
                    override fun onNext(t: VideoItemV4Bean?) {
                        super.onNext(t)
                        mView?.showWeeklySortData(t)
                    }
                }))
    }

    override fun reqMonthlySortData() {
        mView?.showLoading()
        addSubscribe(VideoDataManager.getSortData(REQ_COUNT, MONTHLY_SORT_QUERY)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<VideoItemV4Bean>(mView) {
                    override fun onNext(t: VideoItemV4Bean?) {
                        super.onNext(t)
                        mView?.showMonthlySortData(t)
                    }
                }))
    }

    override fun reqAllSortData() {
        mView?.showLoading()
        addSubscribe(VideoDataManager.getSortData(REQ_COUNT, ALL_SORT_QUERY)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<VideoItemV4Bean>(mView) {
                    override fun onNext(t: VideoItemV4Bean?) {
                        super.onNext(t)
                        mView?.showAllSortData(t)
                    }
                }))
    }
}