package com.xfhy.joke.presenter

import com.xfhy.joke.data.bean.JokeVideoBean
import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView

/**
 * Created by xfhy on 2018/3/21 8:25
 * Description : 视频
 */
interface JokeVideoContract {
    interface Presenter : BasePresenter {
        /**
         * 请求视频数据
         */
        fun reqVideoData(showLoading: Boolean)

        fun refreshData()

        fun loadMoreData()
    }

    interface View : BaseView {
        /**
         * 展示段子列表
         */
        fun showVideoList(jokeList: MutableList<JokeVideoBean.Item>?)

        fun loadMoreSuccess(jokeList: MutableList<JokeVideoBean.Item>?)

        fun loadMoreFail()

    }
}