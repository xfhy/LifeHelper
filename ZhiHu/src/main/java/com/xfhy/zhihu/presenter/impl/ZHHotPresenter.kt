package com.xfhy.zhihu.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.common.ViewState
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.HotDailyBean
import com.xfhy.zhihu.presenter.ZHHotContract

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author xfhy
 * create at 2018年3月10日16:46:17
 * description：知乎热门文章Presenter
 */
class ZHHotPresenter : RxPresenter(), ZHHotContract.Presenter {

    var mView: ZHHotContract.View? = null

    private var mData: List<HotDailyBean.RecentBean>? = null
    private var mStep: Int = 0

    override var data: MutableList<HotDailyBean.RecentBean>? = null

    override fun reqDataFromNet() {
        mView?.showLoading()
        mStep = ViewState.STATE_LOADING
        addSubscribe(ZHDataManager.hotDailyList
                .map<MutableList<HotDailyBean.RecentBean>> { (recent) -> recent }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CommonSubscriber<MutableList<HotDailyBean.RecentBean>>(mView) {
                    override fun onNext(t: MutableList<HotDailyBean.RecentBean>?) {
                        if (t != null) {
                            mView?.loadSuccess(t)
                            mData = t
                        } else {
                            mView?.showErrorMsg("热门列表加载失败....")
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

    override fun getDailyId(position: Int): Int {
        return mData?.get(position)?.newsId ?: 0
    }
}
