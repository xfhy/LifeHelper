package com.xfhy.zhihu.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.ColumnDailyBean

/**
 * @author xfhy
 * time create at 2018年3月10日16:46:56
 * description 知乎专栏首页规范
 */
interface ZHSectionContract {

    interface Presenter : BasePresenter {

        /**
         * 获取专栏列表数据
         */
        var data: MutableList<ColumnDailyBean.DataBean>?

        /**
         * 从网络请求主题列表
         */
        fun reqDataFromNet()

        /**
         * 刷新数据
         */
        fun refreshData()

        /**
         * 根据RecyclerView的position获取专题id
         */
        fun getSectionId(position: Int): Int

        /**
         * 根据RecyclerView的position获取专栏标题
         */
        fun getSectionTitle(position: Int): String

    }

    interface View : BaseView {
        /**
         * 请求专栏列表数据成功
         */
        fun loadSuccess(dataBeans: MutableList<ColumnDailyBean.DataBean>)
    }

}
