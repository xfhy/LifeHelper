package com.xfhy.video.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.video.data.bean.VideoItemV4Bean

/**
 * Created by xfhy on 2018/3/10 22:17
 * Description : 视频 排行
 */
interface VideoHotContract {

    interface Presenter : BasePresenter {
        /**
         * 查询周排行
         */
        fun reqWeeklySortData()

        /**
         * 查询月排行
         */
        fun reqMonthlySortData()

        /**
         * 查询总排行
         */
        fun reqAllSortData()
    }

    interface View : BaseView {
        /**
         * 周排行数据
         */
        fun showWeeklySortData(dataBean: VideoItemV4Bean?)

        /**
         * 月排行数据
         */
        fun showMonthlySortData(dataBean: VideoItemV4Bean?)

        /**
         * 总排行数据
         */
        fun showAllSortData(dataBean: VideoItemV4Bean?)
    }

}