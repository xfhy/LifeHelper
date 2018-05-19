package com.xfhy.gank.presenter

import com.xfhy.gank.data.bean.GankCommonBean
import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView

/**
 * Created by xfhy on 2018/3/20 14:11
 * Description : gank contract
 */
interface GankCommonContract {

    interface Presenter : BasePresenter {
        /**
         * 请求数据
         */
        fun reqGankData(gankType: String, showLoading: Boolean)

        /**
         * 加载更多
         */
        fun loadMoreData()

        /**
         * 刷新数据
         */
        fun refreshData()

    }

    interface View : BaseView {
        /**
         * 展示数据
         */
        fun showGankData(gankDataList: MutableList<GankCommonBean.Result>?)

        fun loadMoreSuccess(gankDataList: MutableList<GankCommonBean.Result>?)
        fun loadMoreFail()
    }

}