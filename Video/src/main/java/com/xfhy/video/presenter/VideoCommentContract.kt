package com.xfhy.video.presenter

import com.xfhy.library.basekit.view.BaseView
import com.xfhy.video.data.bean.CommentBean

/**
 * Created by xfhy on 2018/3/16 21:03
 * Description : 评论列表View
 */
interface VideoCommentContract {

    interface Presenter {
        /**
         * 获取视频的评论数据
         */
        fun reqCommentData(videoId: String)

        fun loadMoreData()

        fun onDestroy()
    }

    interface View : BaseView {
        /**
         * 展示评论数据
         */
        fun showCommentData(commentData: CommentBean?)

        fun loadMoreSuccess(commentData: CommentBean?)

        fun loadMoreFail()

        fun onDestroy()
    }

}