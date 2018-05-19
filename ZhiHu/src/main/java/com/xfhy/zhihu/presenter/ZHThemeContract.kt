package com.xfhy.zhihu.presenter


import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.TopicDailyListBean

/**
 * @author xfhy
 * @create at 2018年3月10日16:47:37
 * description：知乎主题fragment 规范
 */
interface ZHThemeContract {

    interface Presenter : BasePresenter {

        /**
         * 从网络请求主题列表
         */
        fun reqDataFromNet()

        /**
         * 刷新数据
         */
        fun refreshData()

    }

    interface View : BaseView {
        /**
         * 请求主题列表数据成功
         */
        fun loadSuccess(othersBeans: MutableList<TopicDailyListBean.OtherBean>)
    }
}
