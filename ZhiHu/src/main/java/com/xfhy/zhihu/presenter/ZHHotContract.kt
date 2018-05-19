package com.xfhy.zhihu.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.HotDailyBean

/**
 * @author xfhy
 * create at 2018年3月10日16:46:52
 * description：知乎热门文章 该API可能在不久的将来被干掉,最新的知乎日报已经没有采用此API了
 */
interface ZHHotContract {

    interface Presenter : BasePresenter {

        /**
         * 获取专栏详情列表数据
         */
        var data: MutableList<HotDailyBean.RecentBean>?

        /**
         * 从网络请求主题列表
         */
        fun reqDataFromNet()

        /**
         * 刷新数据
         */
        fun refreshData()

        /**
         * 获取点击item位置的文章id
         */
        fun getDailyId(position: Int): Int

    }

    interface View : BaseView {
        /**
         * 请求专栏列表数据成功
         */
        fun loadSuccess(dataBeans: MutableList<HotDailyBean.RecentBean>)
    }


}
