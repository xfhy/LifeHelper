package com.xfhy.video.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.data.bean.VideoInfoBean

/**
 * Created by xfhy on 2018/3/10 22:17
 * Description : 视频 首页
 */
interface VideoHomeContract {

    interface Presenter : BasePresenter {

        /**
         * 从网络请求视频日报数据
         */
        fun reqVideoDailyDataFromNet()

        /**
         * 加载更多数据
         *
         */
        fun loadMoreData()

    }

    interface View : BaseView {

        /**
         * 展示加载的视频列表
         */
        fun showVideoItemList(homeDataBean: HomeDataBean?)

        /**
         * 加载更多成功
         */
        fun loadMoreSuccess(homeDataBean: HomeDataBean?)

        /**
         * 加载更多失败
         */
        fun loadMoreFailed()

    }

}