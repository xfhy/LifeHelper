package com.xfhy.zhihu.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.common.ViewState
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.TopicDailyListBean
import com.xfhy.zhihu.presenter.ZHThemeContract

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author xfhy
 * create at 2018年3月10日16:46:35
 * description：知乎主题presenter
 */
class ZHThemePresenter : RxPresenter(), ZHThemeContract.Presenter {

    var mView: ZHThemeContract.View? = null

    private var mStep: Int = 0

    override fun reqDataFromNet() {
        mView?.showLoading()
        mStep = ViewState.STATE_LOADING
        addSubscribe(
                ZHDataManager.topicDailyList
                        .map<MutableList<TopicDailyListBean.OtherBean>> { (_, _, others) -> others }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : CommonSubscriber<MutableList<TopicDailyListBean.OtherBean>>(mView, "") {
                            override fun onNext(t: MutableList<TopicDailyListBean.OtherBean>?) {
                                if (t != null) {
                                    mView?.loadSuccess(t)
                                } else {
                                    mView?.showErrorMsg("主题列表加载失败....")
                                    mView?.showEmptyView()
                                }
                                mStep = ViewState.STATE_NORMAL
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mStep = ViewState.STATE_ERROR
                            }
                        })
        )
    }

    override fun refreshData() {
        if (mStep == ViewState.STATE_LOADING) {
            return
        }
        reqDataFromNet()
    }
}
