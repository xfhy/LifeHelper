package com.xfhy.userfo.data.api

import com.xfhy.userfo.data.bean.DayStateBean
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * Created by feiyang on 2018/4/4 15:47
 * Description : 用户 api
 */
interface UserService {

    /**
     * 金山词霸 每日一句
     */
    @GET("dsapi")
    fun getDayState(): Flowable<DayStateBean>

}