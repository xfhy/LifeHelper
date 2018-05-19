package com.xfhy.video.presenter.impl

import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.video.data.VideoDataManager
import com.xfhy.video.data.bean.VideoInfoBean
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.presenter.TypeDetailsListContract

/**
 * Created by xfhy on 2018/3/12 15:21
 * Description :  分类详情列表
 */
class TypeDetailsListPresenter : RxPresenter(), TypeDetailsListContract.Presenter {

    var mView: TypeDetailsListContract.View? = null
    var mNextPageUrl: String? = null

    override fun reqTypeDetailsList(videoId: String) {
        mView?.showLoading()
        addSubscribe(VideoDataManager.getTypeDetailsList(videoId, "0", "10")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<VideoItemV4Bean?>(mView) {
                    override fun onNext(t: VideoItemV4Bean?) {
                        super.onNext(t)
                        mView?.showTypeDetailsListData(t)
                        mNextPageUrl = t?.nextPageUrl
                    }
                })
        )

    }

    override fun loadMoreData() {
        //http://baobab.kaiyanapp.com/api/v4/categories/videoList?start=10&num=10&strategy=date&id=36
        val lastIndexOfQuestMark = mNextPageUrl?.lastIndexOf("?")
        if (((lastIndexOfQuestMark ?: 0) + 1) >= mNextPageUrl?.length ?: 0) {
            mView?.loadMoreFailure()
            return
        }
        mNextPageUrl = mNextPageUrl?.substring((lastIndexOfQuestMark ?: 0) + 1)
        Logger.e("$mNextPageUrl")
        val paramGroup = mNextPageUrl?.split("&")
        val paramMap = HashMap<String, String>()
        paramGroup?.forEach { it ->
            val param = it.split("=")
            if (param.size == 2) {
                paramMap.put(param[0], param[1])
            }
        }
        addSubscribe(VideoDataManager.getTypeDetailsList(paramMap["id"] ?: "",
                paramMap["start"] ?: "",
                paramMap["num"] ?: "")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<VideoItemV4Bean?>(mView) {
                    override fun onNext(t: VideoItemV4Bean?) {
                        super.onNext(t)
                        mView?.loadMoreSuccess(t)
                        mNextPageUrl = t?.nextPageUrl
                    }
                })
        )
    }

}