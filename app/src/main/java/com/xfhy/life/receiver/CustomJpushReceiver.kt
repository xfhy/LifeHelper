package com.xfhy.life.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import cn.jpush.android.api.JPushInterface
import com.orhanobut.logger.Logger
import com.xfhy.library.common.BroadcastConstant
import org.json.JSONObject


/**
 * Created by xfhy on 2018/3/27 12:41
 * Description : 自定义的jpush广播接收器
 */
class CustomJpushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        /*
        * <!--Required 用户注册SDK的intent-->
                <action android:name="cn.jpush.android.intent.REGISTRATION"/>
                <!--Required 用户接收SDK消息的intent-->
                <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED"/>
                <!--Required 用户接收SDK通知栏信息的intent-->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED"/>
                <!--Required 用户打开自定义通知栏的intent-->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED"/>
                <!-- 接收网络变化 连接/断开 since 1.6.3 -->
                <action android:name="cn.jpush.android.intent.CONNECTION"/>
        * */
        //用户点击通知消息
        if (intent?.action == JPushInterface.ACTION_NOTIFICATION_OPENED) {
            //取出通知里面附带的扩展信息
            val bundle = intent.extras
            val extraData = bundle.getString(JPushInterface.EXTRA_EXTRA)
            val jsonObject = JSONObject(extraData)
            val maxInterest = jsonObject.getString("maxInterest")

            //给主界面发送本地广播  切换界面
            val intentMain = Intent()
            intentMain.action = BroadcastConstant.MAIN_BROADCAST
            intentMain.putExtra(BroadcastConstant.SWITCH_DRAWER, maxInterest)
            context?.let {
                LocalBroadcastManager.getInstance(it).sendBroadcast(intentMain)
            }

            /*val intentMain = Intent(context, MainActivity::class.java)
            intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intentMain)*/
        }
    }
}