package com.xfhy.joke.presenter.impl

import com.xfhy.joke.data.JokeDataManager
import com.xfhy.joke.data.bean.JokeVideoBean
import com.xfhy.joke.presenter.JokeVideoContract
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils

/**
 * Created by xfhy on 2018/3/21 8:27
 * Description :视频
 */
class JokeVideoPresenter(val mView: JokeVideoContract.View) : RxPresenter(), JokeVideoContract.Presenter {

    var isFirstLoad = true
    var mDate: Long = 0

    override fun reqVideoData(showLoading: Boolean) {
        if (showLoading) {
            mView.showLoading()
        }
        if (isFirstLoad) {
            mDate = System.currentTimeMillis()
        }
        addSubscribe(JokeDataManager.getFunnyVideo("0")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<JokeVideoBean>(mView) {
                    override fun onNext(t: JokeVideoBean?) {
                        super.onNext(t)

                        //除去播放地址为空的
                        mView.showVideoList(t?.list)
                    }
                }))
    }

    override fun refreshData() {
        reqVideoData(false)
    }

    override fun loadMoreData() {
        mDate -= 86400000
        addSubscribe(JokeDataManager.getFunnyVideo(mDate.toString())
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<JokeVideoBean>(mView) {
                    override fun onNext(t: JokeVideoBean?) {
                        super.onNext(t)
                        mView.loadMoreSuccess(t?.list)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView.loadMoreFail()
                    }
                }))
    }
}