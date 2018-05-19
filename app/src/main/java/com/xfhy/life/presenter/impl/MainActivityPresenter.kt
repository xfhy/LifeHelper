package com.xfhy.life.presenter.impl

import android.content.Context
import android.content.Intent
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import com.xfhy.life.presenter.MainActivityContract
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.zhihu.matisse.Matisse
import com.xfhy.library.utils.ImageUtil
import com.xfhy.library.utils.MD5Util
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.data.ProviderDataManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import cn.bmob.v3.Bmob.getApplicationContext
import cn.jpush.android.api.JPushInterface
import org.json.JSONObject
import cn.jpush.android.data.JPushLocalNotification
import com.xfhy.library.common.BaseApplication
import com.xfhy.life.LifeApplication


/**
 * Created by xfhy on 2018/3/6 20:28
 * Description : 主界面
 */
class MainActivityPresenter(val mView: MainActivityContract.View, private val context: Context) :
        RxPresenter(), MainActivityContract.Presenter {

    override fun initUserInfo() {
        val currentUser: UserInfo? = BmobUser.getCurrentUser(UserInfo::class.java)
        if (currentUser != null) {
            //展示用户头像
            mView.showUserIcon(currentUser.userAvatarUrl)
            mView.isUserLogin(true)
        } else {
            //缓存用户对象为空时  则用户未登录 展示默认图像
            mView.showUserIcon("")
            mView.isUserLogin(false)
        }
    }

    override fun dealWithChoosePicture(data: Intent?) {
        //真实路径列表
        val imagesPathResultList = Matisse.obtainPathResult(data)
        if (imagesPathResultList != null && imagesPathResultList.size > 0) {
            //获取图片真实路径
            val imagePath = imagesPathResultList[0]

            mView.showLoading()
            val userInfo: UserInfo? = BmobUser.getCurrentUser(UserInfo::class.java)

            addSubscribe(Flowable.create<String>({ emitter ->
                val compressedBitmap = ImageUtil.compressImage(context, imagePath)
                val compressedFile = ImageUtil.saveImageToLocal(compressedBitmap, imagePath)
                val bmobFile = BmobFile(compressedFile)

                //bmob上传文件
                bmobFile.uploadblock(object : UploadFileListener() {
                    override fun done(e: BmobException?) {
                        mView.hideLoading()
                        if (e == null) {
                            Logger.e("上传头像成功")

                            //设置用户头像
                            userInfo?.userAvatarUrl = bmobFile.url
                            updateUserInfo(userInfo)
                        } else {
                            mView.showErrorMsg("上传头像错误: ${e.message}")
                            emitter.onError(e)
                        }
                        compressedFile?.path?.let { emitter.onNext(it) }
                    }
                })
            }, BackpressureStrategy.BUFFER)
                    .compose(SchedulerUtils.ioToMain())
                    .subscribeWith(object : CommonSubscriber<String>(mView, "") {
                        override fun onNext(t: String?) {
                            super.onNext(t)
                            mView.showUserIcon(t)
                        }

                        override fun onError(e: Throwable) {
                            mView.showErrorMsg("错误: ${e.message}")
                        }
                    }))

        }
    }

    override fun initUserSigned() {
        val currentUser: UserInfo? = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.isSigned(currentUser?.objectId ?: "")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        //用户已经签到了,今天
                        if (t?.code != 0) {
                            mView.userSigned()
                        }
                    }
                }))
    }

    /**
     * 更新用户信息
     */
    private fun updateUserInfo(userInfo: UserInfo?) {
        userInfo?.update(userInfo.objectId, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Logger.e("头像更新成功")
                } else {
                    Logger.e("头像更新失败")
                }
            }
        })

        //把数据同步到LH服务器上面去呗
        userInfo?.let {
            addSubscribe(ProviderDataManager.updateAvatar(userInfo.objectId, userInfo.userAvatarUrl)
                    .compose(SchedulerUtils.ioToMain())
                    .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                        override fun onNext(t: BaseResp<String>?) {
                            super.onNext(t)
                            if (t?.code == 0) {
                                Logger.e("LH:头像更新成功")
                            }
                        }

                        override fun onError(e: Throwable) {
                            super.onError(e)
                            Logger.e("LH:头像更新失败")
                        }
                    }))
        }
    }

    override fun addWeatherCount() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.addWeatherCount(currentUser.objectId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        Logger.e("增加天气阅读次数")
                    }
                }))
    }

    override fun addJokeCount() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.addJokeCount(currentUser.objectId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        Logger.e("增加笑话阅读次数")
                    }
                }))
    }

    override fun getMaxInterest() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.getMaxInterest(currentUser.objectId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        if (t?.code == 0) {
                            Logger.e("${t.data}")

                            //发送通知
                            val jPushLocalNotification = JPushLocalNotification()
                            //通知的样式
                            jPushLocalNotification.builderId = 0
                            jPushLocalNotification.content = "你最爱看的${t.data}又有新更新了,快来瞅瞅"
                            jPushLocalNotification.title = "猜你喜欢"
                            //通知的id
                            jPushLocalNotification.notificationId = 1000
                            //通知发送时间
                            jPushLocalNotification.broadcastTime = System.currentTimeMillis() + 1
                            val map = HashMap<String, Any>()
                            map["maxInterest"] = t.data ?: ""
                            val json = JSONObject(map)
                            jPushLocalNotification.extras = json.toString()
                            JPushInterface.addLocalNotification(BaseApplication.context, jPushLocalNotification)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }
                }))
    }
}