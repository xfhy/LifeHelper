package com.xfhy.zhihu.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import cn.bmob.v3.BmobUser

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.Clipboard
import com.xfhy.library.utils.DevicesUtils
import com.xfhy.library.utils.ShareUtil
import com.xfhy.library.utils.SnackbarUtil
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.common.afterLoginOperation
import com.xfhy.zhihu.R
import com.xfhy.zhihu.adapter.ZHCommentAdapter
import com.xfhy.zhihu.data.bean.DailyCommentBean
import com.xfhy.zhihu.presenter.ZHCommentContract
import com.xfhy.zhihu.presenter.impl.ZHCommentPresenter
import kotlinx.android.synthetic.main.activity_zh_comment.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 2018年3月10日17:28:43
 * 知乎评论页
 */
class ZHCommentActivity : BaseMvpActivity<ZHCommentPresenter>(),
        ZHCommentContract.View,
        BaseQuickAdapter.OnItemClickListener<DailyCommentBean.CommentsBean, BaseViewHolder> {

    companion object {

        private val DAILY_ID = "daily_id"

        /**
         * 点击的评论的对话框选项
         */
        private val COMMENT_DIALOG_ITEMS = arrayOf("复制", "分享")

        /**
         * 进入知乎日报评论页 Activity
         *
         * @param context      Context
         * @param id           日报id
         * @param commentCount 评论总数
         */
        fun enterZHCommentActi(context: Context, id: Int) {
            val intent = Intent(context, ZHCommentActivity::class.java)
            intent.putExtra(DAILY_ID, id)
            context.startActivity(intent)
        }
    }

    /**
     * 当前日报文章id
     */
    private var mDailyId = -1
    private var mCommentAdapter: ZHCommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zh_comment)

        initView()
        initViewEvent()
        initIntentData()
        initData()
    }

    override fun initPresenter() {
        mPresenter = ZHCommentPresenter(this)
        mPresenter?.mView = this
    }

    private fun initView() {
        //初始化adapter
        mCommentAdapter = ZHCommentAdapter(mContext, mutableListOf())
        val linearLayoutManager = LinearLayoutManager(this)
        rvDailyComment.layoutManager = linearLayoutManager
        rvDailyComment.adapter = mCommentAdapter

        //设置RecyclerView分割线
        val dividerItemDecoration = DividerItemDecoration(rvDailyComment.context,
                linearLayoutManager.orientation)
        rvDailyComment.addItemDecoration(dividerItemDecoration)

        // 设置RecyclerView的item监听
        mCommentAdapter?.onItemClickListener = this

    }

    private fun initData() {
        mPresenter?.reqLongComFromNet(mDailyId.toString())
    }

    private fun initViewEvent() {
        mCommentView.onSendClick = { comment ->
            afterLoginOperation {
                mPresenter?.addComment(mDailyId.toString(), comment)
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun initIntentData() {
        val intent = intent
        //获取日报id
        if (intent != null) {
            this.mDailyId = intent.getIntExtra(DAILY_ID, -1)
        }
        setToolBarTitle("评论")
    }

    /**
     * 设置toolbar的标题
     * @param title    标题
     */
    private fun setToolBarTitle(title: String) {
        //setSupportActionBar之前设置标题
        mToolbar?.title = title
        setSupportActionBar(mToolbar)
        val supportActionBar = supportActionBar
        //让导航按钮显示出来
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //设置导航按钮图标
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun showLoading() {
        mStateView.showLoading()
    }

    override fun showErrorMsg(errorMsg: String) {
        mStateView.showContent()
        SnackbarUtil.showBarShortTime(rvDailyComment, errorMsg, SnackbarUtil.WARNING)
    }

    override fun showEmptyView() {}

    override fun showOffline() {
        setToolBarTitle("...")
        mStateView.showOffline(resources.getString(R.string.stfOfflineMessage),
                resources.getString(R.string.stfButtonSetting), View.OnClickListener {
            //未联网  跳转到设置界面
            DevicesUtils.goSetting(mContext)
        })
        SnackbarUtil.showBarLongTime(rvDailyComment, resources.getString(R.string.stfOfflineMessage),
                SnackbarUtil.WARNING, resources.getString(R.string.stfButtonSetting),
                View.OnClickListener {
                    //未联网  跳转到设置界面
                    DevicesUtils.goSetting(mContext)
                })
    }

    override fun showContent() {}

    override fun loadCommentSuccess(commentsBean: MutableList<DailyCommentBean.CommentsBean>?) {
        mStateView.showContent()
        mCommentAdapter?.addData(commentsBean)
    }

    override fun loadCommentError(errorMsg: String) {
        SnackbarUtil.showBarShortTime(rvDailyComment, errorMsg, SnackbarUtil.WARNING)
    }

    override fun showCommentSuccess() {
        SnackbarUtil.showBarShortTime(rvDailyComment, "评论成功", SnackbarUtil.INFO)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<DailyCommentBean.CommentsBean, BaseViewHolder>,
                             view: View, position: Int) {
        val adapterItem = adapter.getItem(position)
        val commentDialog = AlertDialog.Builder(mContext)
        commentDialog.setItems(COMMENT_DIALOG_ITEMS) { _, which ->
            when (which) {
                0 -> {
                    //复制内容到系统剪贴板
                    Clipboard.copyText(mContext, adapterItem?.content ?: "")
                    SnackbarUtil.showBarShortTime(mStateView, "复制成功", SnackbarUtil.INFO)
                }
                1 -> {
                    //调用系统分享 text
                    ShareUtil.shareText(mContext, "刚刚在知乎上看到一条评论很不错,内容如下: ${adapterItem?.content}")
                }
            }
        }
        commentDialog.setCancelable(true)
        commentDialog.show()
    }

}
