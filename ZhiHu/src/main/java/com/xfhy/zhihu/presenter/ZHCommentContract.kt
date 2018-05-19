package com.xfhy.zhihu.presenter


import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.zhihu.data.bean.DailyCommentBean

/**
 * author xfhy
 * time create at 2018年3月10日16:46:40
 * description 知乎评论 规范
 */
interface ZHCommentContract {

    interface Presenter : BasePresenter {

        /**
         * 从网络请求日报评论
         *
         * @param id 日报id
         */
        fun reqLongComFromNet(id: String)

        /**
         * 添加评论
         */
        fun addComment(id: String,comment: String)

    }

    interface View : BaseView {
        /**
         * 加载评论成功
         *
         * @param commentsBean 服务器返回的数据
         */
        fun loadCommentSuccess(commentsBean: MutableList<DailyCommentBean.CommentsBean>?)

        /**
         * 加载评论失败
         *
         * @param errorMsg 错误信息
         */
        fun loadCommentError(errorMsg: String)

        /**
         * 评论成功
         */
        fun showCommentSuccess()
    }

}
