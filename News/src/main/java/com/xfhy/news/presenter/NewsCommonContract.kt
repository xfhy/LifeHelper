package com.xfhy.news.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.news.data.bean.NewsBean

/**
 * Created by xfhy on 2018/3/18 14:01
 * Description : 新闻通用
 */
interface NewsCommonContract {

    interface Presenter : BasePresenter {
        /**
         * 请求新闻通过type
         */
        fun reqNewsByType(type: String)

        /**
         * 加载更多数据
         */
        fun loadMoreData()

        /**
         * 刷新数据
         */
        fun refreshNewsData()
    }

    interface View : BaseView {
        /**
         * 展示新闻
         */
        fun showNewsList(newsList: MutableList<NewsBean.Result.Item>?)

        /**
         * 加载更多成功
         */
        fun loadMoreSuccess(newsList: MutableList<NewsBean.Result.Item>?)

        /**
         * 加载更多失败
         */
        fun loadMoreFail()
    }

}