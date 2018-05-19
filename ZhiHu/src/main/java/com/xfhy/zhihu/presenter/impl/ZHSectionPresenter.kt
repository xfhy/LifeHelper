package com.xfhy.zhihu.presenter.impl


import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.common.ViewState
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.ColumnDailyBean
import com.xfhy.zhihu.presenter.ZHSectionContract

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author xfhy
 * time create at 2018年3月10日16:46:26
 * description
 */
class ZHSectionPresenter : RxPresenter(), ZHSectionContract.Presenter {

    var mView: ZHSectionContract.View? = null

    /**
     * 当前view所处的状态
     */
    private var mStep: Int = 0

    override var data: MutableList<ColumnDailyBean.DataBean>? = null

    override fun reqDataFromNet() {
        mView?.showLoading()
        mStep = ViewState.STATE_LOADING
        addSubscribe(
                ZHDataManager.columnDailyList
                        .map<MutableList<ColumnDailyBean.DataBean>> { (data) -> data }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : CommonSubscriber<MutableList<ColumnDailyBean.DataBean>>(mView) {
                            override fun onNext(t: MutableList<ColumnDailyBean.DataBean>?) {
                                if (t != null) {
                                    mView?.loadSuccess(t)
                                    data = t
                                } else {
                                    mView?.showErrorMsg("专栏列表加载失败....")
                                    mView?.showEmptyView()
                                }
                                mStep = ViewState.STATE_NORMAL
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mStep = ViewState.STATE_NORMAL
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

    override fun getSectionId(position: Int): Int {
        return data?.get(position)?.id ?: 0
    }

    override fun getSectionTitle(position: Int): String {
        return data?.get(position)?.name ?: ""
    }
}
