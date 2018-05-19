package com.xfhy.provider.common

import cn.bmob.v3.BmobUser
import com.alibaba.android.arouter.launcher.ARouter
import com.xfhy.provider.arouter.ARouterPath
import com.xfhy.provider.bean.UserInfo

/**
 * Created by xfhy on 2018/3/8 9:17
 * Description : 顶层方法  可以在代码中直接使用
 */

/**
 * 判断用户是否已经登录过
 * 登录成功则本地UserInfo缓存为null
 */
fun isLoginEd(): Boolean {
    val userInfo = BmobUser.getCurrentUser(UserInfo::class.java)
    return userInfo != null
}

/**
 * 登录之后,需要进行的操作   method是传入的函数
 */
fun afterLoginOperation(method: () -> Unit) {
    /**
     * 登录过:则只需method方法
     * 未登录则跳转到登录界面
     */
    if (isLoginEd()) {
        method()
    } else {
        ARouter.getInstance().build(ARouterPath.USER_CENTER_LOGIN_PATH).navigation()
    }
}