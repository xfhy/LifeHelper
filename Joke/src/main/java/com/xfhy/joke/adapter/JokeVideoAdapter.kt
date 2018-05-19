package com.xfhy.joke.adapter

import android.view.View
import android.view.ViewGroup
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.xfhy.joke.R
import com.xfhy.joke.data.bean.JokeVideoBean
import com.xfhy.joke.widget.SampleCoverVideo
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import com.xfhy.library.utils.ShareUtil
import com.xfhy.library.widgets.VideoPlayerListener
import kotlinx.android.synthetic.main.joke_item_video.view.*

/**
 * Created by xfhy on 2018/3/21 16:57
 * Description : 视频adapter
 *
 * 及时跟进fragment生命周期 及时清理掉资源
 *
 */
class JokeVideoAdapter(dataList: MutableList<JokeVideoBean.Item>?) :
        BaseQuickAdapter<JokeVideoBean.Item, BaseViewHolder>(R.layout.joke_item_video, dataList) {

    /**
     * 暂存的视频播放器   全屏的时候保存当前正在播放的SampleCoverVideo  退出全屏则置null
     */
    private var mTempVideoView: SampleCoverVideo? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        val onCreateViewHolder = super.onCreateViewHolder(parent, viewType)
        //初始化UI
        val videoView = onCreateViewHolder?.itemView?.mVideoView
        videoView?.initUIState()
        //有封面
        videoView?.setThumbPlay(true)
        videoView?.fullscreenButton?.setOnClickListener {
            //开始全屏
            onCreateViewHolder.itemView?.mVideoView?.startWindowFullscreen(parent.context, true, true)
            //全屏时暂存当前SampleCoverVideo  用于取消全屏播放
            mTempVideoView = onCreateViewHolder.itemView?.mVideoView
        }
        //监听Video的状态
        onCreateViewHolder?.itemView?.mVideoView?.setStandardVideoAllCallBack(object : VideoPlayerListener() {

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                //退出全屏
                mTempVideoView = null
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)
                //播放完了之后将播放次数隐藏
                onCreateViewHolder.itemView?.mVideoView?.setLeftTextViewVisible(false)
            }
        })

        return onCreateViewHolder
    }

    override fun convert(holder: BaseViewHolder, item: JokeVideoBean.Item?) {
        item?.u?.header?.let {
            if (it.size > 0) {
                GlideUtils.loadCustomImageNoPlace(holder.itemView, it[0], holder.itemView.mUserIcon)
            }
        }
        holder.itemView.mUserNameTv.text = item?.u?.name

        //播放次数大于5000 就是上面比较火的了
        holder.itemView.iv_joke_is_hot.visibility = if (item?.video?.playcount ?: 0 > 5000) View.GONE else
            View.VISIBLE

        holder.itemView.tv_joke_content.text = item?.text?.replace("，", ",")
                ?.replace("（", "(")?.replace("）", ")")
        item?.video?.video?.let {
            holder.itemView?.mVideoView?.setUp(it[0], false, "")
        }
        //封面图
        item?.video?.thumbnail?.let {
            holder.itemView.mVideoView?.loadCoverImage(it[0])
        }

        item?.video?.let {
            //播放次数
            holder.itemView.mVideoView?.setLeftTextViewText(
                    "${getPlayCount(it.playcount)}次播放")
        }
        holder.itemView.mVideoView?.setLeftTextViewVisible(true)
        holder.itemView?.mVideoView?.clearFullscreenLayout()
        holder.itemView.iv_share_video?.setOnClickListener {
            ShareUtil.shareText(holder.itemView.context,
                    "刚刚在LifeHelper上看到一个搞笑的视频,分享给大家${item?.shareUrl}")
        }
    }

    /**
     * 计算播放次数
     */
    private fun getPlayCount(playCount: Int?): String {
        if (playCount == null) {
            return ""
        }
        return when (playCount) {
            in 0..9999 -> {
                playCount.toString()
            }
            else -> {
                "${if (playCount % 10000 == 0) playCount.toString() else String.format("%.2f", playCount / 10000.toFloat())}万"
            }
        }
    }

    /**
     * 当此适配器创建的视图已附加到窗口时调用。
     */
    override fun onViewAttachedToWindow(holder: BaseViewHolder?) {
        super.onViewAttachedToWindow(holder)
        holder?.itemView?.mVideoView?.initUIState()
    }

    /**
     * 当此适配器创建的视图已从其窗口分离时调用。
     */
    override fun onViewDetachedFromWindow(holder: BaseViewHolder?) {
        super.onViewDetachedFromWindow(holder)
        holder?.itemView?.mVideoView?.onVideoPause()
    }

    /**
     * 当此适配器创建的视图已被回收时调用。
     */
    override fun onViewRecycled(holder: BaseViewHolder?) {
        super.onViewRecycled(holder)
        holder?.itemView?.mVideoView?.onVideoPause()
    }

    /**
     * 退出全屏
     */
    fun backToProtVideo(): Boolean {
        //退出全屏
        return if (mTempVideoView == null) {
            false
        } else {
            mTempVideoView?.clearFullscreenLayout()
            true
        }
    }

    fun onResume() {
        GSYVideoManager.onResume()
    }

    /**
     * 暂停视频
     */
    fun onPause() {
        GSYVideoManager.onPause()
    }

    /**
     * 干掉所有
     */
    fun onDestroy() {
        GSYVideoPlayer.releaseAllVideos()
    }


}