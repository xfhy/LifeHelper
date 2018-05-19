package com.xfhy.life.service

import android.app.IntentService
import android.content.Intent
import cn.bmob.v3.Bmob
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.tencent.bugly.Bugly
import com.xfhy.library.BuildConfig
import com.xfhy.library.data.net.OkHttpUtils

/**
 * Created by xfhy on 2018/4/1 16:33
 * Description : 初始化第三方库的Service
 *
 * 将初始化第三方库的操作全部放到IntentService中进行,所有的在onHandleIntent()执行的操作都是在工作线程中执行
 * 不会影响主线程,以使APP冷启动时加速.
 */
class InitService : IntentService("Init") {
    override fun onHandleIntent(intent: Intent?) {
        OkHttpUtils.initOkHttp(applicationContext)

        //日志打印
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        //Bmob
        Bmob.initialize(applicationContext, com.xfhy.provider.BuildConfig.BMOB_APP_KEY, "bmob")

        //ARouter
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application)   // 尽可能早，推荐在Application中初始化

        //控制StandardGSYVideoPlayer打印日志
        Debuger.disable()

        //初始化bugly
        Bugly.init(applicationContext, com.xfhy.provider.BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG)

        //初始化jpush
        JPushInterface.setDebugMode(BuildConfig.DEBUG)
        JPushInterface.init(application)
    }
}