package com.xfhy.video.presenter

import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView
import com.xfhy.video.data.bean.VideoTypeBean

/**
 * Created by xfhy on 2018/3/10 22:17
 * Description : 视频 分类
 */
interface VideoTypeContract {

    interface Presenter : BasePresenter {
        /**
         * 请求视频分类
         */
        fun reqVideoTypesFromNet()
    }

    interface View : BaseView {
        /**
         * 展示视频分类
         */
        fun showTypes(types: MutableList<VideoTypeBean>?)
    }

}