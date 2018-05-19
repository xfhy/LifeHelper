package com.xfhy.news.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView

/**
 * Created by xfhy on 2018/3/18 21:23
 * Description : 新闻详情
 */
interface NewsDetailsContract {
    interface Presenter : BasePresenter {
        /**
         * 增加新闻阅读次数
         */
        fun addNewsCount()
    }
    interface View : BaseView {}

}