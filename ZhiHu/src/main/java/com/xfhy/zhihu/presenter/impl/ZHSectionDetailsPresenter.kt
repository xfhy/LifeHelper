package com.xfhy.zhihu.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.common.ViewState
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.ColumnDailyDetailsBean
import com.xfhy.zhihu.presenter.ZHSectionDetailsContract

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author xfhy
 * create at 2018年3月10日16:46:22
 * description：知乎专栏详情列表presenter
 */
class ZHSectionDetailsPresenter : RxPresenter(), ZHSectionDetailsContract.Presenter {

    var mView: ZHSectionDetailsContract.View? = null

    private var mData: List<ColumnDailyDetailsBean.StoriesBean>? = null
    /**
     * 当前view所处的状态
     */
    private var mStep: Int = 0

    override fun reqDataFromNet(sectionId: String) {
        mView?.showLoading()
        mStep = ViewState.STATE_LOADING
        addSubscribe(ZHDataManager.getColumnDailyDetailsList(sectionId)
                .map<MutableList<ColumnDailyDetailsBean.StoriesBean>> { (stories) -> stories }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CommonSubscriber<MutableList<ColumnDailyDetailsBean.StoriesBean>?>(mView,
                        "") {
                    override fun onNext(t: MutableList<ColumnDailyDetailsBean.StoriesBean>?) {
                        if (t != null) {
                            mView?.loadSuccess(t)
                            mData = t
                        } else {
                            mView?.showErrorMsg("专栏列表详情加载失败....")
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

    override fun refreshData(sectionId: String) {
        if (mStep == ViewState.STATE_LOADING) {
            return
        }
        reqDataFromNet(sectionId)
    }

    override fun getDailyId(position: Int): Int {
        return mData?.get(position)?.id ?: 0
    }
}
