package com.xfhy.zhihu.presenter.impl

import cn.bmob.v3.BmobUser
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.data.ProviderDataManager
import com.xfhy.provider.data.bean.LHProductInfo
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.DailyContentBean
import com.xfhy.zhihu.data.bean.DailyExtraInfoBean
import com.xfhy.zhihu.presenter.ZHDailyDetailsContract

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * @author xfhy
 * @time create at 2018年3月10日16:46:06
 * @description 知乎最新日报详情
 */
class ZHDailyDetailsPresenter : RxPresenter(), ZHDailyDetailsContract.Presenter {

    var mView: ZHDailyDetailsContract.View? = null

    /**
     * 日报数据
     */
    override var data: DailyContentBean? = null

    /**
     * 日报额外信息
     */
    private var mDailyExtraInfoBean: DailyExtraInfoBean? = null

    override val commentCount: Int
        get() = mDailyExtraInfoBean?.comments ?: 0

    override fun reqDailyContentFromNet(id: String) {
        mView?.showLoading()
        addSubscribe(ZHDataManager.getDailyContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CommonSubscriber<DailyContentBean>(mView) {
                    override fun onNext(t: DailyContentBean?) {
                        data = t
                        if (data != null) {
                            mView?.showContent()
                            mView?.loadSuccess(data)
                        } else {
                            mView?.showEmptyView()
                        }
                    }

                    override fun onError(e: Throwable) {
                        mView?.loadError()
                    }
                })
        )
    }

    override fun reqDailyExtraInfoFromNet(id: String) {
        val lhDataFlowable = ProviderDataManager.getProductInfo(id)
        addSubscribe(ZHDataManager.getDailyExtraInfo(id)
                //合并数据   将2个服务器的点赞数量和评论数量合并在一起
                .zipWith(lhDataFlowable, BiFunction<DailyExtraInfoBean, BaseResp<LHProductInfo>,
                        DailyExtraInfoBean> { dailyData, lhData ->
                    dailyData.popularity = (dailyData.popularity ?: 0) + (lhData.data?.likeCount ?: 0)
                    dailyData.comments = (dailyData.comments ?: 0) + (lhData.data?.commentCount ?: 0)
                    dailyData
                })
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<DailyExtraInfoBean?>(mView, "") {
                    override fun onNext(t: DailyExtraInfoBean?) {
                        mDailyExtraInfoBean = t
                        if (mDailyExtraInfoBean != null) {
                            mView?.setExtraInfo(mDailyExtraInfoBean?.popularity ?: 0,
                                    mDailyExtraInfoBean?.comments ?: 0)
                        } else {
                            mView?.showErrorMsg("日报评论信息请求失败")
                            Logger.e("mDailyContentBean == null")
                        }
                    }

                    override fun onError(e: Throwable) {}
                })
        )
    }

    override fun collectArticle(id: String) {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.collectProduct(currentUser.objectId, id, "1")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView?.setCollectBtnSelState(false)
                    }
                }))
    }

    override fun isCollected(id: String) {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.isCollect(currentUser.objectId, id)
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

    override fun cancelCollectArticle(id: String) {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.cancelCollect(currentUser.objectId, id)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        //取消失败 那么就是原来的选中状态
                        mView?.setCollectBtnSelState(true)
                    }
                }))
    }

    override fun addLikeCount(id: String) {
        //点赞成功与否都不需要展示给用户
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.addLikeCount(currentUser.objectId, id)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        Logger.e("点赞失败")
                    }
                }))
    }

    override fun addZHCount() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.addZhCount(currentUser.objectId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        Logger.e("增加知乎阅读次数")
                    }
                }))
    }
}
