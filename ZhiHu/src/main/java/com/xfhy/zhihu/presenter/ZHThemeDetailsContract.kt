package com.xfhy.zhihu.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.ThemeDailyDetailsBean

/**
 * @author xfhy
 * create at 2018年3月10日16:47:41
 * description：知乎主题详情页规范
 */
interface ZHThemeDetailsContract {

    interface Presenter : BasePresenter {

        /**
         * 获取主题列表数据
         */
        var data: ThemeDailyDetailsBean?

        /**
         * 从网络请求主题列表
         */
        fun reqDataFromNet(number: String)

        /**
         * 刷新数据
         *
         * @param number 主题编号
         */
        fun refreshData(number: String)
    }

    interface View : BaseView {
        /**
         * 请求主题列表数据成功
         */
        fun loadSuccess(themeDailyDetailsBean: ThemeDailyDetailsBean)

        /**
         * 获取当前主题id
         */
        fun getThemeId(): Int
    }
}
