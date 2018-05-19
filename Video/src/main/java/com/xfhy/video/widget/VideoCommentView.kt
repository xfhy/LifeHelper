package com.xfhy.video.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.video.R
import com.xfhy.video.adapter.VideoCommentAdapter
import com.xfhy.video.data.bean.CommentBean
import com.xfhy.video.presenter.VideoCommentContract
import com.xfhy.video.presenter.impl.VideoCommentPresenter
import kotlinx.android.synthetic.main.video_footer_type_main.view.*
import kotlinx.android.synthetic.main.video_layout_video_comment.view.*
import org.jetbrains.anko.textColor

/**
 * Created by xfhy on 2018/3/16 20:55
 * Description : 评论列表View
 */
class VideoCommentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr), VideoCommentContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    private val mPresenter: VideoCommentPresenter by lazy { VideoCommentPresenter(this) }
    private val mCommentAdapter: VideoCommentAdapter by lazy { VideoCommentAdapter(null) }

    init {
        View.inflate(context, R.layout.video_layout_video_comment, this)
        initView()
    }

    private fun initView() {
        //关闭按钮点击事件  回调
        mCloseViewIv.setOnClickListener { onCloseClick?.invoke() }
        mCommentAdapter.openLoadAnimation()
        mCommentAdapter.setEnableLoadMore(true)
        mCommentAdapter.setOnLoadMoreListener(this, mCommentRv)

        mCommentRv.mCommentRv.adapter = mCommentAdapter
    }

    fun setVideoId(id: String) {
        mCommentAdapter.clearData()
        mCommentAdapter.removeAllFooterView()
        mPresenter.reqCommentData(id)
    }

    override fun onLoadMoreRequested() {
        mPresenter.loadMoreData()
    }

    override fun showCommentData(commentData: CommentBean?) {
        //无下一页数据  则关闭可加载下一页
        if (commentData?.nextPageUrl == null) {
            addFooterView()
            mCommentAdapter.setEnableLoadMore(false)
        } else {
            mCommentAdapter.setEnableLoadMore(true)
        }
        mCommentAdapter.replaceData(commentData?.itemList)
    }

    override fun loadMoreSuccess(commentData: CommentBean?) {
        if (commentData?.nextPageUrl == null) {
            mCommentAdapter.loadMoreEnd(true)
            addFooterView()
        } else {
            mCommentAdapter.loadMoreComplete()
        }
        mCommentAdapter.addData(commentData?.itemList)
    }

    override fun loadMoreFail() {
        mCommentAdapter.loadMoreEnd(true)
        addFooterView()
    }

    private fun addFooterView() {
        //底布局
        val footerView = View.inflate(context, R.layout.video_footer_type_main, null)
        footerView.mEndViewTv.textColor = resources.getColor(R.color.color_ffffff)
        footerView.isEnabled = false
        mCommentAdapter.addFooterView(footerView)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showErrorMsg(errorMsg: String) {
    }

    override fun showEmptyView() {
    }

    override fun showOffline() {
    }

    override fun showContent() {
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
    }

    // 声明了一个函数变量
    // Lambda表达式 无参数  返回值为Unit   (() -> Unit)?是onCloseClick属性的类型
    var onCloseClick: (() -> Unit)? = {}

}