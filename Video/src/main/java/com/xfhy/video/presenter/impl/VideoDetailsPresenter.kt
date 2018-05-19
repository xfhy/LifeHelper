package com.xfhy.video.presenter.impl

import cn.bmob.v3.BmobUser
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.data.ProviderDataManager
import com.xfhy.video.data.VideoDataManager
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.presenter.VideoDetailsContract

/**
 * Created by xfhy on 2018/3/16 9:30
 * Description : 视频详情
 */
class VideoDetailsPresenter : RxPresenter(), VideoDetailsContract.Presenter {

    var mView: VideoDetailsContract.View? = null

    override fun reqSuggestVideoListData(videoId: String) {
        addSubscribe(VideoDataManager.getVideoRelatedSuggestion(videoId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<VideoItemV4Bean>(mView) {
                    override fun onNext(t: VideoItemV4Bean?) {
                        super.onNext(t)
                        //判断是是否是文字 组头
                        t?.itemList?.forEach { it ->
                            it.isHeader = (it.type == "textCard")
                        }
                        mView?.showSuggestionVideoItemList(t)
                    }
                })
        )
    }

    override fun collectVideo(videoId: String) {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.collectProduct(currentUser.objectId, videoId, "2")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView?.setCollectBtnSelState(false)
                    }
                }))
    }

    override fun isCollected(videoId: String) {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.isCollect(currentUser.objectId, videoId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        mView?.setCollectBtnSelState(t?.code == 0)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView?.setCollectBtnSelState(false)
                    }
                }))
    }

    override fun cancelCollectVideo(videoId: String) {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.cancelCollect(currentUser.objectId, videoId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        //取消失败 那么就是原来的选中状态
                        mView?.setCollectBtnSelState(true)
                    }
                }))
    }

    override fun addShareCount(videoId: String) {
        addSubscribe(ProviderDataManager.addShareCount(videoId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        Logger.e("分享失败")
                    }
                }))
    }

    override fun addVideoCount() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.addVideoCount(currentUser.objectId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        Logger.e("增加视频阅读次数")
                    }
                }))
    }
}