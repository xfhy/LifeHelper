package com.xfhy.video.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.DateUtils
import com.xfhy.library.utils.NetWorkUtils
import com.xfhy.library.utils.ShareUtil
import com.xfhy.library.widgets.VideoPlayerListener
import com.xfhy.provider.common.afterLoginOperation
import com.xfhy.video.R
import com.xfhy.video.adapter.VideoDetailsAdapter
import com.xfhy.video.data.bean.VideoInfoBean
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.presenter.VideoDetailsContract
import com.xfhy.video.presenter.impl.VideoDetailsPresenter
import com.xfhy.video.widget.VideoCommentView
import kotlinx.android.synthetic.main.video_activity_video_details.*
import kotlinx.android.synthetic.main.video_footer_type_main.view.*
import kotlinx.android.synthetic.main.video_header_video_details.view.*
import org.jetbrains.anko.textColor

/**
 * Created by xfhy on 2018年3月16日09:28:13
 * Description : 视频详情
 */
class VideoDetailsActivity : BaseMvpActivity<VideoDetailsPresenter>(), VideoDetailsContract.View {

    private var mOrientationUtils: OrientationUtils? = null
    private var mVideoData: VideoInfoBean? = null
    private val mVideoAdapter by lazy { VideoDetailsAdapter(null) }
    private val mCommentView by lazy { VideoCommentView(this) }
    private val mHeaderView by lazy { layoutInflater.inflate(R.layout.video_header_video_details, null) }
    /**
     * 当前收藏状态  false:未收藏
     */
    private var mCollectState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_activity_video_details)

        initView()
        initIntentData()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun initPresenter() {
        mPresenter = VideoDetailsPresenter()
        mPresenter?.mView = this
    }

    private fun initView() {
        initVideoView()

        initAdapter()

        mVideoDetailsRv.adapter = mVideoAdapter
        mPresenter?.addVideoCount()
    }

    private fun initAdapter() {
        mVideoAdapter.openLoadAnimation()
        //底布局
        val footerView = layoutInflater.inflate(R.layout.video_footer_type_main, mVideoDetailsRv, false)
        footerView.mEndViewTv.textColor = resources.getColor(R.color.color_ffffff)
        footerView.isEnabled = false
        mVideoAdapter.addFooterView(footerView)

        //item点击事件
        mVideoAdapter.onItemClick = itemClick@ { itemData ->
            if (itemData?.type == "textCard") {
                //提前结束lambda
                return@itemClick
            }
            //清除数据
            mVideoAdapter.clearData()
            mVideoAdapter.removeAllHeaderView()

            val headerView = layoutInflater.inflate(R.layout.video_header_video_details, null)
            mVideoAdapter.addHeaderView(headerView)
            headerView.mVideoTitleTv.text = itemData?.data?.title
            headerView.mVideoInfoTv.text = "#${itemData?.data?.category} / ${DateUtils
                    .formatSecondToMinute(itemData?.data?.duration ?: 0)}"

            headerView.mVideoDesTv.text = itemData?.data?.description
            headerView.mCollectTv.text = itemData?.data?.consumption?.collectionCount?.toString()
            headerView.mShareTv.text = itemData?.data?.consumption?.shareCount?.toString()
            headerView.mCommentTv.text = itemData?.data?.consumption?.replyCount?.toString()
            headerView.mShareTv.setOnClickListener {
                ShareUtil.shareText(mContext, "我刚刚使用LifeHelper看到一个碉堡的片子," +
                        "分享给大家:${itemData?.data?.webUrl?.raw}")
            }
            headerView.mCommentTv.setOnClickListener {
                showCommentView()
            }

            //是连接的wifi
            if (NetWorkUtils.isWifiConnected(mContext)) {
                //直接播放高清的
                itemData?.data?.playInfo?.forEach { it ->
                    if ((it.type ?: "") == "high") {
                        //设置播放URL
                        mVideoView.setUp(it.url, false, "")
                        //开始播放
                        mVideoView.startPlayLogic()
                    }
                }
            } else {
                //播放标清的
                itemData?.data?.playInfo?.forEach { it ->
                    if ((it.type ?: "") == "normal") {
                        //设置播放URL
                        mVideoView.setUp(it.url, false, "")
                    }
                }
            }

            //加载相关视频
            mPresenter?.reqSuggestVideoListData(itemData?.data?.id.toString())
            mVideoData?.id = itemData?.data?.id
        }
    }

    private fun initVideoView() {
        //标题
        mVideoView.titleTextView.visibility = View.GONE
        //返回按钮
        mVideoView.backButton.visibility = View.VISIBLE
        mOrientationUtils = OrientationUtils(this, mVideoView)
        //关闭自动旋转
        mVideoView.isRotateViewAuto = false
        //显示全屏动画
        mVideoView.isShowFullAnimation = true
        mVideoView.fullscreenButton.setOnClickListener {
            //横竖屏切换
            mOrientationUtils?.resolveByClick()
            //开始全屏
            mVideoView.startWindowFullscreen(mContext, true, true)
        }
        //监听Video的状态
        mVideoView.setStandardVideoAllCallBack(object : VideoPlayerListener() {

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                //退出全屏
                mOrientationUtils?.backToProtVideo()
            }
        })
        //返回按钮事件
        mVideoView.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun initIntentData() {
        mVideoData = intent.getSerializableExtra("home_data_bean_item_list") as? VideoInfoBean

        mVideoAdapter.addHeaderView(mHeaderView)
        mHeaderView.mVideoTitleTv.text = mVideoData?.title
        mHeaderView.mVideoInfoTv.text = "#${mVideoData?.category} / ${DateUtils
                .formatSecondToMinute(mVideoData?.duration ?: 0)}"

        mHeaderView.mVideoDesTv.text = mVideoData?.description
        mHeaderView.mCollectTv.text = mVideoData?.collectionCount?.toString()
        mHeaderView.mShareTv.text = mVideoData?.shareCount?.toString()
        mHeaderView.mCommentTv.text = mVideoData?.replyCount?.toString()
        mHeaderView.mCommentTv.setOnClickListener {
            showCommentView()
        }
        //点击收藏
        mHeaderView.mCollectTv.setOnClickListener {
            afterLoginOperation {
                //根据状态  去收藏  或者  取消收藏
                if (mCollectState) {
                    mVideoData?.collectionCount = (mVideoData?.collectionCount ?: 0) - 1
                    mPresenter?.cancelCollectVideo(mVideoData?.id.toString())
                } else {
                    mPresenter?.collectVideo(mVideoData?.id.toString())
                    mVideoData?.collectionCount = (mVideoData?.collectionCount ?: 0) + 1
                }
                mHeaderView.mCollectTv.text = mVideoData?.collectionCount?.toString()
                mCollectState = !mCollectState
                //更新图标状态
                setCollectBtnSelState(mCollectState)
            }
        }
        //点击分享
        mHeaderView.mShareTv.setOnClickListener {
            ShareUtil.shareText(mContext, "我刚刚使用LifeHelper看到一个碉堡的片子," +
                    "分享给大家:${mVideoData?.raw}")
            mPresenter?.addShareCount(mVideoData?.id.toString())
        }

        //是连接的wifi
        if (NetWorkUtils.isWifiConnected(mContext)) {
            //直接播放高清的
            //设置播放URL
            mVideoView.setUp(mVideoData?.highUrl, false, "")
            //开始播放
            mVideoView.startPlayLogic()
        } else {
            //播放标清的
            mVideoView.setUp(mVideoData?.normalUrl, false, "")
        }

        //加载相关视频
        mPresenter?.reqSuggestVideoListData(mVideoData?.id.toString())
        mPresenter?.isCollected(mVideoData?.id.toString())
    }

    private fun showCommentView() {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.BELOW, mVideoView.id)
        mCommentView.layoutParams = layoutParams
        mVideoDetailsRootView.addView(mCommentView)
        mCommentView.setVideoId(mVideoData?.id.toString())
        mCommentView.onCloseClick = {
            mVideoDetailsRootView.removeView(mCommentView)
        }
    }

    override fun showSuggestionVideoItemList(videoItemV4Bean: VideoItemV4Bean?) {
        mVideoAdapter.replaceData(videoItemV4Bean?.itemList)
    }

    override fun setCollectBtnSelState(state: Boolean) {
        mCollectState = state
        val drawable = if (state) {
            resources.getDrawable(R.drawable.ic_toolbar_like_solid)
        } else {
            resources.getDrawable(R.drawable.ic_toolbar_like_h)
        }
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        mHeaderView.mCollectTv.setCompoundDrawables(drawable, null, null, null)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            //未全屏->则开始全屏
            if (!mVideoView.isIfCurrentIsFullscreen) {
                mVideoView.startWindowFullscreen(mContext, true, true)
            }
        } else {
            //判断是否是全屏
            if (mVideoView.isIfCurrentIsFullscreen) {
                StandardGSYVideoPlayer.backFromWindowFull(this)
            }
        }
    }

    override fun onBackPressed() {
        //判断是否评论View正在被显示  显示则移除
        if (mVideoDetailsRootView.indexOfChild(mCommentView) != -1) {
            mVideoDetailsRootView.removeView(mCommentView)
            return
        }

        //首先返回竖屏
        mOrientationUtils?.backToProtVideo()
        //是否正在全屏
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }

        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        mCommentView.onDestroy()
    }

}
