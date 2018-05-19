package com.xfhy.joke.presenter.impl

import com.xfhy.joke.data.JokeDataManager
import com.xfhy.joke.data.bean.JokeBean
import com.xfhy.joke.presenter.JokeConnotaContract
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils

/**
 * Created by xfhy on 2018/3/21 8:27
 * Description :段子
 */
class JokeConnotaPresenter(val mView: JokeConnotaContract.View) : RxPresenter(), JokeConnotaContract.Presenter {

    var isFirstLoad = true
    var mDate: Long = 0

    override fun reqJokeData(showLoading: Boolean) {
        if (showLoading) {
            mView.showLoading()
        }

        if (isFirstLoad) {
            mDate = System.currentTimeMillis()
        }

        addSubscribe(JokeDataManager.getJoke("0").compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<JokeBean>(mView) {
                    override fun onNext(t: JokeBean?) {
                        super.onNext(t)
                        isFirstLoad = false
                        mView.showJokeList(t?.list)
                    }
                }))
    }

    override fun refreshData() {
        reqJokeData(false)
    }

    override fun loadMoreData() {
        mDate -= 86400000
        addSubscribe(JokeDataManager.getJoke(mDate.toString()).compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<JokeBean>(mView) {
                    override fun onNext(t: JokeBean?) {
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