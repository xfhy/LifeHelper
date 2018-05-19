package com.xfhy.joke.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.shuyu.gsyvideoplayer.video.GSYBaseVideoPlayer
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.xfhy.joke.R
import kotlinx.android.synthetic.main.joke__layout_video_layout_cover.view.*


/**
 * Created by xfhy on 2018/3/21 21:01
 * Description : 带封面的StandardGSYVideoPlayer
 */
class SampleCoverVideo : StandardGSYVideoPlayer {

    private var mCoverOriginUrl: String = ""

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun init(context: Context) {
        super.init(context)
        mCoverImageView = findViewById(R.id.thumbImage)

        if (mThumbImageViewLayout != null && (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.visibility = View.VISIBLE
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.joke__layout_video_layout_cover
    }

    fun loadCoverImage(url: String) {
        mCoverOriginUrl = url
        Glide.with(context.applicationContext)
                .setDefaultRequestOptions(
                        RequestOptions()
                                .frame(1000000)
                                .centerCrop())
                .load(url)
                .into(thumbImage)
    }

    /**
     * 设置左侧文本是否可见
     */
    fun setLeftTextViewVisible(visible: Boolean) {
        mPlayCountTv.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * 设置左侧文本
     */
    fun setLeftTextViewText(text: String) {
        mPlayCountTv.text = text
    }

    override fun startPlayLogic() {
        setLeftTextViewVisible(false)
        super.startPlayLogic()
    }

    override fun resolveNormalVideoShow(oldF: View?, vp: ViewGroup?, gsyVideoPlayer: GSYVideoPlayer?) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer)
    }

    override fun startWindowFullscreen(context: Context, actionBar: Boolean, statusBar: Boolean):
            GSYBaseVideoPlayer {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val sampleCoverVideo = gsyBaseVideoPlayer as SampleCoverVideo
        sampleCoverVideo.loadCoverImage(mCoverOriginUrl)
        return gsyBaseVideoPlayer
    }
}