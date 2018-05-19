package com.xfhy.video.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.video.data.bean.VideoItemV4Bean

/**
 * Created by xfhy on 2018/3/16 9:29
 * Description : 视频详情
 */
interface VideoDetailsContract {

    interface Presenter : BasePresenter {

        /**
         * 获取该视频相关推荐
         */
        fun reqSuggestVideoListData(videoId: String)

        /**
         * 收藏
         */
        fun collectVideo(videoId: String)

        /**
         * 判断该视频是否已经被收藏
         */
        fun isCollected(videoId: String)

        /**
         * 取消收藏该视频
         */
        fun cancelCollectVideo(videoId: String)

        /**
         * 分享
         */
        fun addShareCount(videoId: String)

        /**
         * 增加视频播放次数
         */
        fun addVideoCount()

    }

    interface View : BaseView {

        /**
         * 展示推荐的视频列表
         */
        fun showSuggestionVideoItemList(videoItemV4Bean: VideoItemV4Bean?)

        /**
         * 设置收藏按钮选中状态
         */
        fun setCollectBtnSelState(state: Boolean)

    }

}