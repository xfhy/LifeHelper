package com.xfhy.zhihu.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.DailyContentBean

/**
 * @author xfhy
 * @time create at 2018年3月10日16:46:45
 * @description 知乎最新日报 详情规范
 */
interface ZHDailyDetailsContract {

    interface Presenter : BasePresenter {

        /**
         * 获取数据
         *
         * @return
         */
        var data: DailyContentBean?

        /**
         * 获取评论数量
         */
        val commentCount: Int

        /**
         * 从网络请求日报详情数据
         *
         * @param id 日报id
         */
        fun reqDailyContentFromNet(id: String)

        /**
         * 请求额外信息
         *
         * @param id 日报id
         */
        fun reqDailyExtraInfoFromNet(id: String)

        /**
         * 收藏当前文章
         *
         * @param id 日报id
         */
        fun collectArticle(id: String)

        /**
         * 判断该文章是否已经被收藏
         *
         * @param id id
         */
        fun isCollected(id: String)

        /**
         * 取消收藏该文章
         */
        fun cancelCollectArticle(id: String)

        /**
         * 点赞
         */
        fun addLikeCount(id: String)

        /**
         * 增加知乎阅读次数
         */
        fun addZHCount()

    }

    interface View : BaseView {

        /**
         * 用户按下返回键 处理
         */
        fun goToBack()

        /**
         * 加载成功
         *
         * @param dailyContentBean 服务器返回的数据
         */
        fun loadSuccess(dailyContentBean: DailyContentBean?)

        /**
         * 加载失败
         */
        fun loadError()

        /**
         * 控制收藏按钮的选择状态
         *
         * @param state true:被选择了(收藏了的)  false:未被选择
         */
        fun setCollectBtnSelState(state: Boolean)

        /**
         * 设置底部数据
         */
        fun setExtraInfo(likeCount: Int, commentCount: Int)
    }

}
