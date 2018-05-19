package com.xfhy.video.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.video.data.VideoDataManager
import com.xfhy.video.data.bean.VideoTypeBean
import com.xfhy.video.presenter.VideoTypeContract

/**
 * Created by xfhy on 2018/3/10 22:18
 * Description : 视频 分类 presenter
 */
class VideoTypePresenter : RxPresenter(), VideoTypeContract.Presenter {

    var mView: VideoTypeContract.View? = null

    override fun reqVideoTypesFromNet() {
        mView?.showLoading()
        addSubscribe(VideoDataManager.getVideoTypes()
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<MutableList<VideoTypeBean>>(mView) {
                    override fun onNext(t: MutableList<VideoTypeBean>?) {
                        super.onNext(t)
                        mView?.showTypes(t)
                    }
                })
        )
    }
}