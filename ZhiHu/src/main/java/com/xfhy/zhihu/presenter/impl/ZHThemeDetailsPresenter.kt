package com.xfhy.zhihu.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.common.ViewState
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.ThemeDailyDetailsBean
import com.xfhy.zhihu.presenter.ZHThemeDetailsContract

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author xfhy
 * create at 2018年3月10日16:46:30
 * description：知乎主题详情页presenter
 */
class ZHThemeDetailsPresenter : RxPresenter(), ZHThemeDetailsContract.Presenter {

    var mView: ZHThemeDetailsContract.View? = null

    override var data: ThemeDailyDetailsBean? = null
    /**
     * 当前所处的状态
     */
    private var mStep: Int = 0

    override fun reqDataFromNet(number: String) {
        mView?.showLoading()
        mStep = ViewState.STATE_LOADING
        addSubscribe(ZHDataManager.getThemeDailyDetails(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CommonSubscriber<ThemeDailyDetailsBean>(mView) {
                    override fun onNext(t: ThemeDailyDetailsBean?) {
                        if (t != null) {
                            mView?.loadSuccess(t)
                            data = t
                        } else {
                            mView?.showErrorMsg("主题列表加载失败....")
                            mView?.showEmptyView()
                        }
                        mStep = ViewState.STATE_NORMAL
                    }
                })
        )
    }

    override fun refreshData(number: String) {
        if (mStep == ViewState.STATE_LOADING) {
            return
        }
        reqDataFromNet(number)
    }
}
