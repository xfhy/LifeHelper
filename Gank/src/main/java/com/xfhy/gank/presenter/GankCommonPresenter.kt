package com.xfhy.gank.presenter

import com.xfhy.gank.config.GankConfig
import com.xfhy.gank.data.GankDataManager
import com.xfhy.gank.data.bean.GankCommonBean
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import io.reactivex.functions.BiFunction

/**
 * Created by xfhy on 2018/3/20 14:13
 * Description : gank presenter
 */
class GankCommonPresenter(val mView: GankCommonContract.View) : RxPresenter(), GankCommonContract.Presenter {

    /**
     * 一页加载的数目
     */
    private val loadNum = 15
    /**
     * 页码
     */
    private var page = 1
    private var mGankType: String = ""

    override fun reqGankData(gankType: String, showLoading: Boolean) {
        if (showLoading) {
            mView.showLoading()
        }
        mGankType = gankType
        val girlDataFlowable = GankDataManager.getRandomData(GankConfig.TAB_TITLE[3], loadNum.toString())
        addSubscribe(GankDataManager.getGankData(gankType, loadNum.toString(), page.toString())
                //合并数据
                .zipWith(girlDataFlowable, BiFunction<GankCommonBean, GankCommonBean, GankCommonBean> { gankData, girlData ->
                    gankData.results?.forEachIndexed { index, result ->
                        result.gankBg = girlData.results?.get(index)?.url
                    }
                    gankData
                })
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<GankCommonBean?>(mView) {
                    override fun onNext(t: GankCommonBean?) {
                        super.onNext(t)
                        mView.showGankData(t?.results)
                    }
                }))

    }

    override fun loadMoreData() {
        page += loadNum
        val girlDataFlowable = GankDataManager.getRandomData(GankConfig.TAB_TITLE[3], loadNum.toString())
        addSubscribe(GankDataManager.getGankData(mGankType, loadNum.toString(), page.toString())
                //合并数据
                .zipWith(girlDataFlowable, BiFunction<GankCommonBean, GankCommonBean, GankCommonBean> { gankData, girlData ->
                    gankData.results?.forEachIndexed { index, result ->
                        result.gankBg = girlData.results?.get(index)?.url
                    }
                    gankData
                })
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<GankCommonBean?>(mView) {
                    override fun onNext(t: GankCommonBean?) {
                        super.onNext(t)
                        if (t?.results?.size == 0) {
                            mView.loadMoreFail()
                        } else {
                            mView.loadMoreSuccess(t?.results)
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView.loadMoreFail()
                    }
                }))
    }

    override fun refreshData() {
        page = 1
        reqGankData(mGankType, false)
    }
}