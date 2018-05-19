package com.xfhy.zhihu.presenter.impl

import android.support.annotation.IntRange


import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.common.ViewState
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.utils.DateUtils
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.LatestDailyListBean
import com.xfhy.zhihu.data.bean.PastNewsBean
import com.xfhy.zhihu.presenter.ZHDailyLatestContract

import java.util.Date

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * author xfhy
 * create at 2018年3月10日16:46:10
 * description：知乎最新日报的presenter
 */
class ZHDailyLatestPresenter : RxPresenter(), ZHDailyLatestContract.Presenter {

    var mView: ZHDailyLatestContract.View? = null

    /**
     * 当前界面的显示的数据
     */
    override var data: LatestDailyListBean? = null

    /**
     * 当前所进行到的步骤
     */
    private var mStep: Int = 0

    override fun onRefresh() {
        if (mStep == ViewState.STATE_LOAD_MORE || mStep == ViewState.STATE_LOADING) {
            return
        }
        mStep = ViewState.STATE_REFRESH
        reqDailyDataFromNet()
    }

    override fun reqDailyDataFromNet() {
        mStep = ViewState.STATE_LOADING
        mView?.showLoading()

        addSubscribe(ZHDataManager.latestDailyList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CommonSubscriber<LatestDailyListBean>(mView) {
                    override fun onNext(t: LatestDailyListBean?) {
                        data = t
                        //显示最新数据
                        mView?.showLatestData(data)

                        val header = LatestDailyListBean.StoriesBean(true, "今日热闻")
                        data?.stories?.add(0, header)

                        //显示内容区域
                        mView?.showContent()
                        mStep = ViewState.STATE_NORMAL
                    }
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mStep = ViewState.STATE_ERROR
                    }
                })
        )

    }

    override fun loadMoreData(@IntRange(from = 1) pastDays: Int) {
        //需要写一个Util方法  计算今天的日期-x天 表示的日期   格式:yyyyMMdd
        //再计算该日期需要显示的文字  比如: 20171010 : 10月10日 星期二
        //-2天显示:10月09日 星期一
        /*
         * RecyclerView上拉时需要加载更多的数据
         * pastDays 这里传入RecyclerView的分组个数,代表离今天过去了多少天  至少过去了1天
         * 比如:今天是2017年10月11日,则显示今日热闻
         * 那么-1天是20171010,显示为:10月10日 星期二
         * -2是20171009,显示为:10月09日 星期一
         *
         * 1.根据格式化的日期(eg:20171010)去加载往期日报
         * 2.显示到view上
         */

        if (mStep == ViewState.STATE_REFRESH) {
            return
        }

        val pastDate = DateUtils.getPastDate(Date(System.currentTimeMillis()),
                pastDays)
        val groupTitle = DateUtils.getDateFormatText(pastDate, "MM月dd日 E")
        addSubscribe(
                ZHDataManager.getPastNews(DateUtils.getDateFormatText(pastDate, "yyyyMMdd"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : CommonSubscriber<PastNewsBean>(mView) {
                            override fun onNext(t: PastNewsBean?) {
                                if (t != null) {

                                    val header = LatestDailyListBean.StoriesBean(true, groupTitle)
                                    data?.stories?.add(header)

                                    mView?.loadMoreSuccess(groupTitle, t)
                                } else {
                                    mView?.showErrorMsg("无更多数据~")
                                    mView?.loadMoreFailed()
                                }
                                mStep = ViewState.STATE_NORMAL
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView?.loadMoreFailed()
                                mStep = ViewState.STATE_NORMAL
                            }
                        })
        )
    }

    override fun getClickItemId(position: Int): Int {
        val stories = data?.stories
        if (stories != null && position < stories.size) {
            return stories[position].id
        }
        return 0
    }

    override fun getHeaderClickItemId(position: Int): Int {
        val stories = data?.topStories
        if (stories != null && position < stories.size) {
            return stories[position].id
        }
        return 0
    }
}
