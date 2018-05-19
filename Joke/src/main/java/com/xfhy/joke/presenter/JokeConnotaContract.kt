package com.xfhy.joke.presenter

import com.xfhy.joke.data.bean.JokeBean
import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView

/**
 * Created by xfhy on 2018/3/21 8:25
 * Description : 段子
 */
interface JokeConnotaContract {
    interface Presenter : BasePresenter {
        /**
         * 请求段子数据
         */
        fun reqJokeData(showLoading: Boolean)

        fun refreshData()

        fun loadMoreData()

    }

    interface View : BaseView {
        /**
         * 展示段子列表
         */
        fun showJokeList(jokeList: MutableList<JokeBean.Item>?)

        fun loadMoreSuccess(jokeList: MutableList<JokeBean.Item>?)

        fun loadMoreFail()
    }
}