package com.xfhy.library.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import cn.bmob.v3.Bmob
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.xfhy.library.BuildConfig


/**
 * @author xfhy
 * time create at 2018/2/1 20:24
 * description Application基类
 */
open class BaseApplication(delegateClassName: String = "") :
        TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, delegateClassName,
                "com.tencent.tinker.loader.TinkerLoader", false) {

    companion object {
        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()

        context = this

        registerActivityLifecycleCallbacks(ActivityLifecycle)
    }

    object ActivityLifecycle : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            AppManager.instance.addActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            AppManager.instance.finishActivity(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
        }

    }

}