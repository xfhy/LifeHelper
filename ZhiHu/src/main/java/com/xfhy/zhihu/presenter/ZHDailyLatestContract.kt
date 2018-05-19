package com.xfhy.zhihu.presenter

import android.support.annotation.IntRange

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.LatestDailyListBean
import com.xfhy.zhihu.data.bean.PastNewsBean

/**
 * author xfhy
 * create at 2018年3月10日16:46:49
 * description：知乎 日报 板块的 规范
 */
interface ZHDailyLatestContract {
    /**
     * 知乎 日报 板块的 presenter
     */
    interface Presenter : BasePresenter {

        /**
         * 获取数据源
         */
        var data: LatestDailyListBean?

        /**
         * 刷新数据
         */
        fun onRefresh()

        /**
         * 从网络请求日报数据
         */
        fun reqDailyDataFromNet()

        /**
         * 加载更多数据
         *
         * @param pastDays 这里传入RecyclerView的分组个数,代表离今天过去了多少天  至少过去了1天
         */
        fun loadMoreData(@IntRange(from = 1) pastDays: Int)

        /**
         * 获取点击item的id
         *
         * @param position position
         * @return 获取失败则返回0
         */
        fun getClickItemId(position: Int): Int

        /**
         * 获取点击header 的item的id
         *
         * @param position position
         * @return 获取失败则返回0
         */
        fun getHeaderClickItemId(position: Int): Int
    }

    /**
     * 知乎 日报 板块的 View接口
     */
    interface View : BaseView {
        /**
         * 获取最新的数据成功  传入最新的数据  显示
         *
         * @param latestDailyListBean 最新的数据
         */
        fun showLatestData(latestDailyListBean: LatestDailyListBean?)

        /**
         * 显示加载成功的往期数据(当用户上拉列表时,如果把今天的日报刷完了,那么再往下拉时需要加载昨天的数据,
         * 依次类推,不断加载前一天的数据,然后加到RecyclerView中)
         *
         * @param groupTitle   组头标题(每一天的日报是一个RecyclerView分组)
         * @param pastNewsBean 需要加到RecyclerView末尾的数据
         */
        fun loadMoreSuccess(groupTitle: String, pastNewsBean: PastNewsBean?)

        /**
         * 加载更多失败
         */
        fun loadMoreFailed()

    }
}
