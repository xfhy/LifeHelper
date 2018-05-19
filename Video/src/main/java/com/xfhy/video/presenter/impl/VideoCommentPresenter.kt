package com.xfhy.video.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.video.data.VideoDataManager
import com.xfhy.video.data.bean.CommentBean
import com.xfhy.video.presenter.VideoCommentContract

/**
 * Created by xfhy on 2018/3/16 21:04
 * Description :评论列表View
 */
class VideoCommentPresenter(val mView: VideoCommentContract.View) : RxPresenter(),
        VideoCommentContract.Presenter {

    /**
     * 下一页视频地址
     */
    private var mNextPageUrl: String? = ""

    override fun reqCommentData(videoId: String) {
        addSubscribe(VideoDataManager.getCommentData(videoId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<CommentBean?>(mView) {
                    override fun onNext(t: CommentBean?) {
                        super.onNext(t)
                        t?.itemList?.forEach { it ->
                            it.isHeader = (it.type == "leftAlignTextHeader")
                        }
                        mView.showCommentData(t)
                        mNextPageUrl = t?.nextPageUrl
                    }
                })
        )
    }

    override fun loadMoreData() {
        //http://baobab.kaiyanapp.com/api/v2/replies/video? lastId=30&videoId=18882&num=10&type=video
        val lastIndexOfQuestMark = mNextPageUrl?.lastIndexOf("?")
        if (((lastIndexOfQuestMark ?: 0) + 1) >= mNextPageUrl?.length ?: 0) {
            mView.loadMoreFail()
            return
        }
        mNextPageUrl = mNextPageUrl?.substring((lastIndexOfQuestMark ?: 0) + 1)
        val paramGroup = mNextPageUrl?.split("&")
        val paramMap = HashMap<String, String>()
        paramGroup?.forEach { it ->
            val param = it.split("=")
            if (param.size == 2) {
                paramMap.put(param[0], param[1])
            }
        }
        addSubscribe(VideoDataManager.getCommentData(paramMap["lastId"] ?: "",
                paramMap["videoId"] ?: "",
                paramMap["num"] ?: "",
                paramMap["type"] ?: "")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<CommentBean?>(mView) {
                    override fun onNext(t: CommentBean?) {
                        super.onNext(t)
                        mView.loadMoreSuccess(t)
                        mNextPageUrl = t?.nextPageUrl
                    }
                })
        )
    }
}