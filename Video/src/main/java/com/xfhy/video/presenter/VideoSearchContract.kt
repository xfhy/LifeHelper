package com.xfhy.video.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.video.data.bean.HomeDataBean

/**
 * Created by xfhy on 2018/3/17 20:59
 * Description : 视频搜索
 */
interface VideoSearchContract {

    interface Presenter : BasePresenter {
        /**
         * 请求热搜关键词
         */
        fun reqHotSearchKeys()

        /**
         * 通过关键词搜索视频
         */
        fun searchVideoByKey(key: String)

        fun loadMoreData()
    }

    interface View : BaseView {
        /**
         * 显示热搜关键词
         */
        fun showHotSearchKeys(keys: MutableList<String>?)

        /**
         * 显示搜索出来的视频列表
         */
        fun showSearchVideoList(videoList: MutableList<HomeDataBean.ItemList>?)

        fun loadMoreSuccess(videoList: MutableList<HomeDataBean.ItemList>?)

        fun loadMoreFail()

        fun loadNoMoreData()

    }

}