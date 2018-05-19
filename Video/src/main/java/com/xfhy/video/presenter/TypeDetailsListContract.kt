package com.xfhy.video.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.video.data.bean.VideoInfoBean
import com.xfhy.video.data.bean.VideoItemV4Bean

/**
 * Created by xfhy on 2018/3/12 15:20
 * Description : 分类详情列表
 */
interface TypeDetailsListContract {

    interface Presenter : BasePresenter {
        /**
         * 获取分类详情列表
         */
        fun reqTypeDetailsList(videoId: String)

        /**
         * 加载更多
         */
        fun loadMoreData()

    }

    interface View : BaseView {
        /**
         * 展示分类详情列表
         */
        fun showTypeDetailsListData(videoItemV4Bean: VideoItemV4Bean?)

        /**
         * 加载更多成功
         */
        fun loadMoreSuccess(videoItemV4Bean: VideoItemV4Bean?)

        /**
         * 加载更多失败
         */
        fun loadMoreFailure()
    }

}