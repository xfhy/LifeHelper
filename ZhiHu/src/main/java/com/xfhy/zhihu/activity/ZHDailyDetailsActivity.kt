package com.xfhy.zhihu.activity


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.*
import com.xfhy.provider.common.afterLoginOperation
import com.xfhy.zhihu.R
import com.xfhy.zhihu.data.bean.DailyContentBean
import com.xfhy.zhihu.presenter.ZHDailyDetailsContract
import com.xfhy.zhihu.presenter.impl.ZHDailyDetailsPresenter
import kotlinx.android.synthetic.main.activity_zh_daily_details.*

/**
 * @author feiyang
 * create 2018年3月10日16:43:20
 * description 知乎最新日报详情页
 */
class ZHDailyDetailsActivity : BaseMvpActivity<ZHDailyDetailsPresenter>(),
        ZHDailyDetailsContract.View, View.OnClickListener {
    companion object {

        private val DAILY_ID = "daily_id"

        /**
         * 进入知乎日报详情 Activity
         *
         * @param context Context
         * @param id      日报id
         */
        fun enterZHDailyDetailsActi(context: Context, id: Int) {
            val intent = Intent(context, ZHDailyDetailsActivity::class.java)
            intent.putExtra(DAILY_ID, id)
            context.startActivity(intent)
        }
    }

    private var isBottomShow = true
    /**
     * 已经点赞
     */
    private var mIsLiked = false
    /**
     * 点赞数
     */
    private var mLikeCount = 0

    /**
     * 当前日报文章id
     */
    private var mDailyId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zh_daily_details)

        initView()
        initViewEvent()
        initIntentData()
        initData()
    }

    override fun initPresenter() {
        mPresenter = ZHDailyDetailsPresenter()
        mPresenter?.mView = this
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        val settings = mContentWebView.settings

        //设置是否应该启用应用程序缓存API。 默认值是false
        settings.setAppCacheEnabled(true)
        //设置是否启用DOM存储API。 默认值是false。
        settings.domStorageEnabled = true
        //设置是否启用数据库存储API。 默认值是false。
        settings.databaseEnabled = true
        if (NetWorkUtils.isNetWorkAvailable(this)) {
            //默认缓存使用模式。
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            //不要使用网络，从缓存中加载。
            settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
        }

        //是否启用JS
        settings.javaScriptEnabled = true
        //设置自适应屏幕  缩小宽度以适合屏幕的内容
        settings.loadWithOverviewMode = true
        //设置布局算法,将所有内容移动到视图宽度的一列中。
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //设置是否允许进行缩放
        settings.setSupportZoom(true)
        //设置在该WebView中加载网页
        mContentWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        mLikeCountTv.setOnClickListener(this)
        mCommentCountTv.setOnClickListener(this)
        mShareTv.setOnClickListener(this)
        mLikeDailyBtn.setOnClickListener(this)
    }

    private fun initViewEvent() {
        //为使底部栏能滑动隐藏,直接监听NestedScrollView的滑动事件  向下滑则隐藏   上滑则显示
        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            //上滑 并且 正在显示底部栏
            if (scrollY - oldScrollY > 0 && isBottomShow) {
                isBottomShow = false
                //将Y属性变为底部栏高度  (相当于隐藏了)
                mBottomView.animate().translationY(mBottomView.height.toFloat())
            } else if (scrollY - oldScrollY < 0 && !isBottomShow) {
                isBottomShow = true
                mBottomView.animate().translationY(0f)
            }
        })
    }

    private fun initIntentData() {
        val intent = intent
        //获取日报id
        if (intent != null) {
            this.mDailyId = intent.getIntExtra(DAILY_ID, -1)
        }
    }

    private fun initData() {
        //从网络请求日报数据
        mPresenter?.reqDailyContentFromNet(mDailyId.toString())
        mPresenter?.reqDailyExtraInfoFromNet(mDailyId.toString())
        //判断当前文章是否被收藏
        mPresenter?.isCollected(mDailyId.toString())
        mPresenter?.addZHCount()
    }

    override fun showErrorMsg(errorMsg: String) {
        SnackbarUtil.showBarLongTime(mContentWebView, errorMsg, SnackbarUtil.ALERT)
    }

    override fun showEmptyView() {
        hideLoading()
    }

    override fun showContent() {
        hideLoading()
    }

    /**
     * 设置toolbar的标题
     * @param title    标题
     */
    private fun setToolBarTitle(title: String) {
        //setSupportActionBar之前设置标题
        mToolbar.title = title
        setSupportActionBar(mToolbar)
        val supportActionBar = supportActionBar
        //让导航按钮显示出来
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //设置导航按钮图标
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun showOffline() {
        setToolBarTitle("...")
        hideLoading()
        SnackbarUtil.showBarLongTime(mContentWebView, resources.getString(R.string.stfOfflineMessage),
                SnackbarUtil.WARNING, resources.getString(R.string.stfButtonSetting),
                View.OnClickListener {
                    //未联网  跳转到设置界面
                    DevicesUtils.goSetting(mContext)
                })
    }

    override fun goToBack() {
        finish()
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun setExtraInfo(likeCount: Int, commentCount: Int) {
        mLikeCount = likeCount
        mLikeCountTv.text = "${likeCount}个赞"
        mCommentCountTv.text = "${commentCount}条评论"
    }

    override fun loadSuccess(dailyContentBean: DailyContentBean?) {
        //标题
        setToolBarTitle(dailyContentBean?.title ?: "...")
        //顶部图片
        GlideUtils.loadCustomImage(mContext, dailyContentBean?.image, ivTopPicture)
        //图片来源
        tvImageSource.text = dailyContentBean?.imageSource
        //加载html
        val htmlData = HtmlUtil.createHtmlData(dailyContentBean?.body ?: "",
                dailyContentBean?.css ?: listOf(), dailyContentBean?.js ?: listOf())
        mContentWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING)
    }

    override fun loadError() {
        setToolBarTitle("...")
        hideLoading()
        SnackbarUtil.showBarLongTime(mContentWebView, resources.getString(R.string.stfErrorMessageNormal), SnackbarUtil
                .ALERT, resources.getString(R.string.empty_view_retry),
                View.OnClickListener {
                    mPresenter?.reqDailyContentFromNet(mDailyId.toString())
                    mPresenter?.reqDailyExtraInfoFromNet(mDailyId.toString())
                })
    }

    override fun setCollectBtnSelState(state: Boolean) {
        mLikeDailyBtn.isSelected = state
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        val data = mPresenter?.data
        when (v?.id) {
            R.id.mLikeCountTv -> {
                addLikeCount(data)
            }
            R.id.mCommentCountTv -> {
                ZHCommentActivity.enterZHCommentActi(mContext, mDailyId)
            }
            R.id.mShareTv -> {
                if (data != null) {
                    ShareUtil.shareText(this, "我正在使用LifeHelper,看到一篇文章很不错,分享给大家: ${data.shareUrl}")
                } else {
                    SnackbarUtil.showBarShortTime(mContentWebView, "数据未加载成功,不能分享", SnackbarUtil.WARNING)
                }
            }
            R.id.mLikeDailyBtn -> {
                //首先判断是否已经登录   再选择是收藏还是取消收藏
                afterLoginOperation {
                    if (mLikeDailyBtn.isSelected) {
                        mPresenter?.cancelCollectArticle(mDailyId.toString())
                    } else {
                        mPresenter?.collectArticle(mDailyId.toString())
                    }
                    //按钮状态取反
                    mLikeDailyBtn.isSelected = !mLikeDailyBtn.isSelected
                }
            }
        }
    }

    private fun addLikeCount(data: DailyContentBean?) {
        afterLoginOperation login@ {
            if (data == null) {
                SnackbarUtil.showBarShortTime(mNestedScrollView, "数据未加载成功不能点赞", SnackbarUtil.CONFIRM)
                return@login
            }
            if (mIsLiked) {
                SnackbarUtil.showBarShortTime(mNestedScrollView, "客官,你已经点过赞了哦", SnackbarUtil.CONFIRM)
            } else {
                mPresenter?.addLikeCount(mDailyId.toString())
                //标记已经点赞
                mIsLiked = true
                //设置图片
                val drawable = resources.getDrawable(R.drawable.icon_daily_like_checked)
                drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                mLikeCountTv.setCompoundDrawables(null, drawable, null, null)
                //增加点赞数量
                mLikeCount++
                mLikeCountTv.text = "${mLikeCount}个赞"
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
        //这个是HomeAsUp按钮的id永远都是android.R.id.home
            android.R.id.home -> goToBack()
            else -> {
            }
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mContentWebView.canGoBack()) {
            mContentWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        if (mContentWebView != null) {
            mContentWebView.onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mContentWebView != null) {
            mContentWebView.destroy()
        }
    }

}
