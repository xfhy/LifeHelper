package com.xfhy.userfo.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.userfo.data.bean.DayStateBean

/**
 * Created by feiyang on 2018/4/4 15:15
 * Description : 签到   每日一句
 */
interface SignInContract {

    interface Presenter : BasePresenter {
        /**
         * 签到
         */
        fun reqSignIn()

        /**
         * 请求词霸每日一句
         */
        fun reqDayStateData()
    }

    interface View : BaseView {
        /**
         * 展示签到结果
         */
        fun showSignInResult(isSuccess: Boolean)

        /**
         * 展示词霸每日一句数据
         */
        fun showDayStateData(dayStateBean: DayStateBean)

        fun showDayOnMonthText(str: String)
        fun showMonthText(str: String)
        fun showDayOfWeekText(str: String)

    }

}