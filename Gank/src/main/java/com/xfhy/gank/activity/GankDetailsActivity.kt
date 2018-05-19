package com.xfhy.gank.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.bmob.v3.BmobUser
import com.orhanobut.logger.Logger
import com.xfhy.gank.R
import com.xfhy.library.basekit.activity.TitleBarActivity
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.data.ProviderDataManager
import kotlinx.android.synthetic.main.gank_activity_details.*

/**
 * Created by xfhy on 2018/3/20 21:00
 * Description : 干货详情
 */
class GankDetailsActivity : TitleBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gank_activity_details)

        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        val webSettings = mWebView.settings
        //设置是否应该启用应用程序缓存API。 默认值是false
        webSettings.setAppCacheEnabled(true)

        //是否启用JS
        webSettings.javaScriptEnabled = true
        //设置自适应屏幕  缩小宽度以适合屏幕的内容
        webSettings.loadWithOverviewMode = true
        //默认字体大小设置为20
        webSettings.defaultFontSize = 20
        //设置布局算法,将所有内容移动到视图宽度的一列中。
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //设置是否允许进行缩放
        webSettings.setSupportZoom(true)
        // 兼容webview加载https与http混合页面时5.0以下系统图片加载不出来的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        //设置在该WebView中加载网页
        mWebView.webViewClient = object : WebViewClient() {
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

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showLoading()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideLoading()
            }

        }

        mWebView.loadUrl(intent.getStringExtra("url"))

        addTechCount()

    }

    private fun addTechCount() {
        val currentUser = BmobUser.getCurrentUser(UserInfo::class.java) ?: return
        ProviderDataManager.addTechCount(currentUser.objectId)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(null) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        Logger.e("增加技术文章阅读次数")
                    }
                })
    }

    override fun getThisTitle(): CharSequence {
        return intent.getStringExtra("title")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        mWebView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebView.destroy()
    }

}