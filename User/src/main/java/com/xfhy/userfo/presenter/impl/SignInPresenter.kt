package com.xfhy.userfo.presenter.impl

import android.annotation.SuppressLint
import android.text.TextUtils
import cn.bmob.v3.BmobUser
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.library.utils.DateUtils
import com.xfhy.provider.bean.UserInfo
import com.xfhy.userfo.data.UserDataManager
import com.xfhy.userfo.data.bean.DayStateBean
import com.xfhy.userfo.presenter.SignInContract
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by feiyang on 2018/4/4 15:24
 * Description : 签到  每一一句
 */
class SignInPresenter(val mView: SignInContract.View) : RxPresenter(), SignInContract.Presenter {

    override fun reqSignIn() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(UserDataManager.signIn(currentUser.objectId).compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        mView.showSignInResult(t?.code == 0)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        mView.showSignInResult(false)
                    }
                }))
    }

    override fun reqDayStateData() {
        mView.showLoading()
        addSubscribe(UserDataManager.getDayState().compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<DayStateBean>(mView) {
                    @SuppressLint("SimpleDateFormat")
                    override fun onNext(t: DayStateBean?) {
                        super.onNext(t)
                        t?.let {
                            mView.showDayStateData(it)
                            val date = DateUtils.getDateByText(it.dateline ?: "", "yyyy-MM-dd")
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = date.time
                            //展示号数
                            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                            mView.showDayOnMonthText("${if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth}")
                            //展示月份
                            mView.showMonthText(DateUtils.getMonthAbridge(calendar.get(Calendar.MONTH) + 1))
                            //展示星期
                            mView.showDayOfWeekText(DateUtils.getWeekText(calendar.get(Calendar.DAY_OF_WEEK)))
                        }
                    }
                }))
    }
}