package com.xfhy.life

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.content.Intent
import com.tencent.bugly.beta.Beta
import com.tencent.tinker.loader.app.DefaultApplicationLike
import com.xfhy.life.service.InitService


/**
 * Created by xfhy on 2018/4/1 19:28
 * Description : 自定义ApplicationLike
 */
class SampleApplicationLike(application: Application, tinkerFlags: Int,
                            tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long,
                            applicationStartMillisTime: Long, tinkerResultIntent: Intent) :
        DefaultApplicationLike(application, tinkerFlags,
                tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent) {


    override fun onCreate() {
        super.onCreate()
        initThirdLibrary()
    }

    /**
     * 初始化第三方库
     */
    private fun initThirdLibrary() {
        val intent = Intent(application, InitService::class.java)
        application.startService(intent)
    }

    override fun onBaseContextAttached(base: Context) {
        super.onBaseContextAttached(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this)
    }

    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }

    companion object {

        val TAG = "Tinker.SampleApplicationLike"
    }

}