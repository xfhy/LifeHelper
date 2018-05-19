package com.xfhy.news.presenter.impl

import cn.bmob.v3.BmobUser
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.news.presenter.NewsDetailsContract
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.data.ProviderDataManager

/**
 * Created by xfhy on 2018/3/18 21:24
 * Description : 新闻详情
 */
class NewsDetailsPresenter(val mView: NewsDetailsContract.View) : RxPresenter(), NewsDetailsContract.Presenter {
    override fun addNewsCount() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.addNewsCount(currentUser.objectId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        Logger.e("增加新闻阅读次数")
                    }
                }))
    }
}