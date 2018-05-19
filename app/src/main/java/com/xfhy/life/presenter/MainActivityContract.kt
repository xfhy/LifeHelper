package com.xfhy.life.presenter

import android.content.Intent
import com.xfhy.library.basekit.presenter.BasePresenter
import com.xfhy.library.basekit.view.BaseView

/**
 * Created by xfhy on 2018/3/6 20:22
 * Description : 主界面
 */
interface MainActivityContract {

    interface Presenter : BasePresenter {
        /**
         * 处理选择的图片
         */
        fun dealWithChoosePicture(data: Intent?)

        /**
         * 初始化用户信息  用户是否已经登录
         */
        fun initUserInfo()

        /**
         * 查询用户是否已经签到
         */
        fun initUserSigned()

        /**
         * 增加天气阅读次数
         */
        fun addWeatherCount()

        /**
         * 增加笑话阅读次数
         */
        fun addJokeCount()

        /**
         * 获取用户最大兴趣点
         */
        fun getMaxInterest()
    }

    interface View : BaseView {
        /**
         * 显示用户头像
         */
        fun showUserIcon(path: String?)

        /**
         * 用户是否已登录
         */
        fun isUserLogin(isLogin: Boolean)

        /**
         * 用户已经签到
         */
        fun userSigned()
    }

}