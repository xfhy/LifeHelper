package com.xfhy.userfo.data

import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.data.net.RetrofitFactory
import com.xfhy.provider.config.ApiConstants
import com.xfhy.provider.data.ProviderDataManager
import com.xfhy.provider.data.api.LHService
import com.xfhy.userfo.data.api.UserService
import com.xfhy.userfo.data.bean.DayStateBean
import io.reactivex.Flowable

/**
 * Created by feiyang on 2018/4/4 15:26
 * Description : 用户中心数据管理
 */
object UserDataManager {

    private val mRetrofitFactory: RetrofitFactory by lazy { RetrofitFactory.instance }

    /**
     * 获取用户 Service  实现
     */
    private val userService: UserService
        get() = mRetrofitFactory.create(UserService::class.java, ApiConstants.KINGSOFT_BASE_URL)

    /**
     * 获取lifeHelper Service  实现
     */
    private val lhService: LHService
        get() = mRetrofitFactory.create(LHService::class.java, ApiConstants.LIFE_HELPER_BASE_URL)

    /**
     * 签到
     */
    fun signIn(bmobId: String): Flowable<BaseResp<String>> {
        return lhService.signIn(bmobId)
    }

    /**
     * 金山词霸 每日一句
     */
    fun getDayState(): Flowable<DayStateBean> {
        return userService.getDayState()
    }

}