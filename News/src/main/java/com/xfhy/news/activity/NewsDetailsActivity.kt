package com.xfhy.news.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.news.R
import com.xfhy.news.data.bean.NewsBean
import com.xfhy.news.presenter.NewsDetailsContract
import com.xfhy.news.presenter.impl.NewsDetailsPresenter
import kotlinx.android.synthetic.main.news_activity_news_details.*
import android.support.v7.view.menu.MenuBuilder
import android.view.View
import com.xfhy.library.utils.HtmlUtil
import com.xfhy.library.utils.NetWorkUtils
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.view.KeyEvent
import android.webkit.*
import com.orhanobut.logger.Logger
import com.xfhy.library.utils.ShareUtil
import android.webkit.WebView
import com.tbruyelle.rxpermissions2.RxPermissions


/**
 * Created by xfhy on 2018/3/18 21:21
 * Description : 新闻详情
 */
class NewsDetailsActivity : BaseMvpActivity<NewsDetailsPresenter>(), NewsDetailsContract.View, Toolbar.OnMenuItemClickListener {

    companion object {
        private val SMALL_FONT_SIZE = 18
        private val MIDDLE_FONT_SIZE = 25
        private val BIG_FONT_SIZE = 30
    }

    private var mNewsBeanData: NewsBean.Result.Item? = null
    private var mWebSettings: WebSettings? = null
    private var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity_news_details)

        initView()
        initIntentData()
    }

    override fun initPresenter() {
        mPresenter = NewsDetailsPresenter(this)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        mWebSettings = mNewsContentWv.settings
        //设置是否应该启用应用程序缓存API。 默认值是false
        mWebSettings?.setAppCacheEnabled(true)
        //设置是否启用DOM存储API。 默认值是false。
        //mWebSettings?.domStorageEnabled = true
        //设置是否启用数据库存储API。 默认值是false。
        //mWebSettings?.databaseEnabled = true
        if (NetWorkUtils.isNetWorkAvailable(this)) {
            //默认缓存使用模式。
            mWebSettings?.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            //不要使用网络，从缓存中加载。
            mWebSettings?.cacheMode = WebSettings.LOAD_CACHE_ONLY
        }

        //是否启用JS
        mWebSettings?.javaScriptEnabled = true
        //设置自适应屏幕  缩小宽度以适合屏幕的内容
        mWebSettings?.loadWithOverviewMode = true
        //默认字体大小设置为20
        mWebSettings?.defaultFontSize = SMALL_FONT_SIZE
        //设置布局算法,将所有内容移动到视图宽度的一列中。
        mWebSettings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //设置是否允许进行缩放
        mWebSettings?.setSupportZoom(true)
        // 兼容webview加载https与http混合页面时5.0以下系统图片加载不出来的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        //设置在该WebView中加载网页
        mNewsContentWv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.isEmpty()) {
                    return false
                }
                return try {
                    //http、https开头的网址 则自己加载
                    if (url.startsWith("http") || url.startsWith("https")) {
                        view.loadUrl(url)
                        true
                    } else {
                        //自定义的 则调用第三方加载
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        true
                    }
                } catch (e: Exception) {
                    //防止崩溃
                    e.printStackTrace()
                    false
                }

            }

            //页面加载完成才显示原文链接
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mSeeSrcNewsTv.visibility = if (firstLoad) View.VISIBLE else View.GONE
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }

        }

        mSeeSrcNewsTv.setOnClickListener {
            firstLoad = false
            mSeeSrcNewsTv.visibility = View.GONE
            Logger.e("开始加载 ${mNewsBeanData?.weburl}")
            mNewsContentWv.loadUrl(mNewsBeanData?.weburl)
        }

        mPresenter?.addNewsCount()
    }

    @SuppressLint("SetTextI18n")
    private fun initIntentData() {
        mNewsBeanData = intent.getSerializableExtra("news_data") as? NewsBean.Result.Item
        setToolBarTitle(mNewsBeanData?.title ?: "...")
        mPostdateSrcTv.text = "${mNewsBeanData?.time}     ${mNewsBeanData?.src}"
        //段落首行缩进
        mNewsBeanData?.content = mNewsBeanData?.content?.replace(" class=\"art_p\"", " style=\"text-indent:2em;\"")

        //加载html
        val htmlData = HtmlUtil.createHtmlData(mNewsBeanData?.content ?: "", listOf(), listOf())
        mNewsContentWv.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING)
    }

    /**
     * 设置toolbar的标题
     * @param title    标题
     */
    private fun setToolBarTitle(title: String) {
        //setSupportActionBar之前设置标题
        mNewsToolbar.title = title
        setSupportActionBar(mNewsToolbar)
        mNewsToolbar.setOnMenuItemClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_news_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
        //这个是HomeAsUp按钮的id永远都是android.R.id.home
            android.R.id.home -> finish()
            else -> {
            }
        }
        return true
    }

    @SuppressLint("RestrictedApi")
    override fun onPrepareOptionsPanel(view: View?, menu: Menu?): Boolean {
        val menuBuilder = menu as? MenuBuilder
        //设置toolbar menu图标可见
        menuBuilder?.setOptionalIconsVisible(true)
        return super.onPrepareOptionsPanel(view, menu)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_browser -> {
                openDefaultBrowser()
            }
            R.id.action_font_size -> {
                showChangeFontSizeDialog()
            }
            R.id.action_share -> {
                shareThisNews()
            }
            else -> {
            }
        }
        return true
    }

    private fun shareThisNews() {
        ShareUtil.shareText(mContext, "刚刚看到一条雷人的新闻,分享给大家: ${mNewsBeanData?.weburl}")
    }

    private fun showChangeFontSizeDialog() {
        val builder = AlertDialog.Builder(mContext)
        val alertDialog = builder.create()
        builder.setTitle("字体大小")

        val chooseFontSize = when (mWebSettings?.defaultFontSize ?: SMALL_FONT_SIZE) {
            SMALL_FONT_SIZE -> 0
            MIDDLE_FONT_SIZE -> 1
            BIG_FONT_SIZE -> 2
            else -> 0
        }
        builder.setSingleChoiceItems(arrayOf("小", "中", "大"), chooseFontSize, { _, which ->
            when (which) {
                0 -> {
                    mWebSettings?.defaultFontSize = SMALL_FONT_SIZE
                }
                1 -> {
                    mWebSettings?.defaultFontSize = MIDDLE_FONT_SIZE
                }
                2 -> {
                    mWebSettings?.defaultFontSize = BIG_FONT_SIZE
                }
                else -> {
                    mWebSettings?.defaultFontSize = SMALL_FONT_SIZE
                }
            }
        })
        builder.setPositiveButton("确定", { _, _ ->
            alertDialog.dismiss()
        })
        builder.show()
    }

    private fun openDefaultBrowser() {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.data = Uri.parse(mNewsBeanData?.weburl)
        startActivity(intent)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mNewsContentWv.canGoBack()) {
            mNewsContentWv.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        mNewsContentWv.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mNewsContentWv.destroy()
    }

}