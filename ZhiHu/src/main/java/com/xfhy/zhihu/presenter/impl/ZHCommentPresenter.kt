package com.xfhy.zhihu.presenter.impl


import android.content.Context
import cn.bmob.v3.BmobUser
import com.orhanobut.logger.Logger

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.library.utils.NetWorkUtils
import com.xfhy.library.utils.StringUtils
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.data.ProviderDataManager
import com.xfhy.provider.data.bean.LHComment
import com.xfhy.zhihu.data.ZHDataManager
import com.xfhy.zhihu.data.bean.DailyCommentBean
import com.xfhy.zhihu.presenter.ZHCommentContract

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * author xfhy
 * time create at 2018年3月10日16:46:03
 * description 知乎评论页Presenter
 */
class ZHCommentPresenter(private val mContext: Context) : RxPresenter(), ZHCommentContract.Presenter {

    var mView: ZHCommentContract.View? = null

    override fun reqLongComFromNet(id: String) {
        mView?.showLoading()
        if (NetWorkUtils.isNetWorkAvailable(mContext)) {
            val longCommentFlowable = ZHDataManager.getDailyLongComments(id)
            val shortCommentFlowable = ZHDataManager.getDailyShortComments(id)
            addSubscribe(Flowable.concat(longCommentFlowable, shortCommentFlowable)   //必须按顺序
                    .map<MutableList<DailyCommentBean.CommentsBean>> { dailyCommentBean ->
                        dailyCommentBean.comments
                    }
                    .compose(SchedulerUtils.ioToMain())
                    .subscribeWith(object : CommonSubscriber<MutableList<DailyCommentBean.CommentsBean>?>
                    (mView, "日报评论信息请求失败") {
                        override fun onNext(t: MutableList<DailyCommentBean.CommentsBean>?) {
                            mView?.loadCommentSuccess(t)
                        }
                    })
            )

            //加载lh上的评论
            addSubscribe(ProviderDataManager.getComments(id)
                    .compose(SchedulerUtils.ioToMain())
                    .subscribeWith(object : CommonSubscriber<BaseResp<MutableList<LHComment>>>(mView) {
                        override fun onNext(t: BaseResp<MutableList<LHComment>>?) {
                            super.onNext(t)
                            val dailyComments = mutableListOf<DailyCommentBean.CommentsBean>()
                            t?.data?.forEach { itemData ->
                                val commentBean = DailyCommentBean.CommentsBean()
                                itemData.userName?.let {
                                    if (StringUtils.isPhone(it)) {
                                        //如果是电话号码,那么则隐藏中间几位
                                        commentBean.author = it.replaceRange(3, 7, "****")
                                    } else {
                                        commentBean.author = it
                                    }
                                }
                                commentBean.avatar = itemData.userAvatarUrl
                                commentBean.time = itemData.commentTime ?: 0
                                commentBean.content = itemData.content
                                dailyComments.add(commentBean)
                            }
                            mView?.loadCommentSuccess(dailyComments)
                        }
                    }))

        } else {
            mView?.showOffline()
        }

    }

    override fun addComment(id: String, comment: String) {
        val user = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        addSubscribe(ProviderDataManager.addComment(user.objectId, id, comment)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(mView, "评论失败") {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)

                        val commentData = DailyCommentBean.CommentsBean()
                        user.username?.let {
                            if (StringUtils.isPhone(it)) {
                                //如果是电话号码,那么则隐藏中间几位
                                commentData.author = it.replaceRange(3, 7, "****")
                            } else {
                                commentData.author = it
                            }
                        }
                        commentData.avatar = user.userAvatarUrl
                        commentData.time = System.currentTimeMillis() / 1000
                        commentData.content = comment
                        val dataList = mutableListOf<DailyCommentBean.CommentsBean>()
                        dataList.add(commentData)
                        mView?.loadCommentSuccess(dataList)
                        mView?.showCommentSuccess()
                    }
                }))
    }
}
