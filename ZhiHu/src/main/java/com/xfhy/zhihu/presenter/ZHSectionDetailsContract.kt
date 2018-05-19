package com.xfhy.zhihu.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.ColumnDailyDetailsBean

/**
 * @author xfhy
 * create at 2018年3月10日16:47:01
 * description：知乎通用list的规范
 */
interface ZHSectionDetailsContract {

    interface Presenter : BasePresenter {

        /**
         * 从网络请求主题列表
         */
        fun reqDataFromNet(sectionId: String)

        /**
         * 刷新数据
         */
        fun refreshData(sectionId: String)

        /**
         * 获取点击item位置的文章id
         */
        fun getDailyId(position: Int): Int

    }

    interface View : BaseView {

        /**
         * 请求专栏列表数据成功
         */
        fun loadSuccess(dataBeans: MutableList<ColumnDailyDetailsBean.StoriesBean>)

    }

}
