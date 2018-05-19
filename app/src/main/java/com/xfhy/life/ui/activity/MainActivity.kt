package com.xfhy.life.ui.activity

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import com.xfhy.gank.fragment.GankMainFragment
import com.xfhy.joke.fragment.JokeMainFragment
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.common.AppManager
import com.xfhy.library.common.CustomGlideEngine
import com.xfhy.library.utils.GlideUtils
import de.hdodenhof.circleimageview.CircleImageView
import com.xfhy.life.R
import com.xfhy.life.presenter.MainActivityContract
import com.xfhy.life.presenter.impl.MainActivityPresenter
import com.xfhy.library.common.BroadcastConstant
import com.xfhy.life.ui.AboutFragment
import com.xfhy.news.fragment.NewsMainFragment
import com.xfhy.provider.common.afterLoginOperation
import com.xfhy.userfo.activity.SignInActivity
import com.xfhy.video.fragment.VideoMainFragment
import com.xfhy.weather.fragment.WeatherFragment
import com.xfhy.zhihu.fragment.ZHMainFragment
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.toast

/**
 * 2018年3月3日22:10:49
 * 主界面
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *    ┃　　　┃   神兽保佑
 *    ┃　　　┃   代码无BUG！
 *    ┃　　　┗━━━┓
 *    ┃　　　　　　　┣┓
 *    ┃　　　　　　　┏┛
 *    ┗┓┓┏━┳┓┏┛
 *      ┃┫┫　┃┫┫
 *      ┗┻┛　┗┻┛
 */
class MainActivity : BaseMvpActivity<MainActivityContract.Presenter>(),
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, MainActivityContract.View {

    private val mRxPermissions: RxPermissions by lazy { RxPermissions(this) }

    companion object {
        private const val REQUEST_CODE_CHOOSE = 1000
        private const val REQUEST_CODE_SIGN_IN = 1001
        /**
         * 再按一次退出   中间的时间间隔
         */
        private const val BACK_TO_EXIT_TIME = 3000
    }

    /**
     * 用户头像
     */
    private lateinit var mUserIconIv: CircleImageView
    /**
     * 签到
     */
    private lateinit var mSignInTv: TextView
    //标题广播
    private val mMainBroadcast: MainLocalBroadcast by lazy { MainLocalBroadcast() }
    /**
     * 本地广播管理
     */
    private val mLocalBroadcastManager: LocalBroadcastManager by lazy { LocalBroadcastManager.getInstance(this) }

    /**
     * 上次点击返回按键时间
     */
    private var lastClickTime = 0L
    private var mIsSigned = false

    private val mDailyMainFragment by lazy { ZHMainFragment.newInstance() }
    private val mVideoMainFragment by lazy { VideoMainFragment.newInstance() }
    private val mNewsMainFragment by lazy { NewsMainFragment.newInstance() }
    private val mGankMainFragment by lazy { GankMainFragment.newInstance() }
    private val mJokeMainFragment by lazy { JokeMainFragment.newInstance() }
    private val mWeatherMainFragment by lazy { WeatherFragment.newInstance() }
    private val mAboutFragment by lazy { AboutFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initTitleBroadcast()
    }

    override fun onRestart() {
        super.onRestart()
        //当用户登录成功时会回调这个方法  初始化用户信息
        mPresenter?.initUserInfo()
        mPresenter?.initUserSigned()
    }

    override fun initPresenter() {
        mPresenter = MainActivityPresenter(this, mContext)
    }

    private fun initView() {
        //setSupportActionBar之前设置标题
        mToolbar.title = title
        setSupportActionBar(mToolbar)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            //让导航按钮显示出来
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            //设置导航按钮图标
            supportActionBar.setDisplayShowHomeEnabled(true)
        }

        //导航按钮有旋转特效
        val toggle = ActionBarDrawerToggle(
                this, mMainDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mMainDrawer.addDrawerListener(toggle)
        toggle.syncState()

        //动态加载drawer header  监听头像点击事件
        val headerView = mMainNavigaView.inflateHeaderView(R.layout.layout_drawer_header)
        mSignInTv = headerView.findViewById(R.id.mSignInTv)
        mUserIconIv = headerView.findViewById(R.id.mUserIconIv)
        mSignInTv.setOnClickListener(this)
        mUserIconIv.setOnClickListener(this)

        //菜单
        mMainNavigaView.setCheckedItem(R.id.nav_menu_zhihu)
        mMainNavigaView.setNavigationItemSelectedListener(this)

        //初始化用户信息
        mPresenter?.initUserInfo()
        mPresenter?.getMaxInterest()
        mPresenter?.initUserSigned()

        addFragment(supportFragmentManager, mDailyMainFragment, "ZHLatestDailyFragment")
    }

    private fun initTitleBroadcast() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BroadcastConstant.MAIN_BROADCAST)
        //注册本地广播
        mLocalBroadcastManager.registerReceiver(mMainBroadcast, intentFilter)
    }

    /**
     * 设置toolbar的标题
     * @param title    标题
     */
    fun setToolBarTitle(title: String) {
        //setSupportActionBar之前设置标题
        mToolbar.title = title
        setSupportActionBar(mToolbar)
        val supportActionBar = supportActionBar
        //让导航按钮显示出来
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //设置导航按钮图标
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        hideAllFragment(supportFragmentManager)
        mToolbar.visibility = View.VISIBLE
        when (item.itemId) {
            R.id.nav_menu_zhihu -> {
                addFragment(supportFragmentManager, mDailyMainFragment, "ZHLatestDailyFragment")
                showFragment(supportFragmentManager, mDailyMainFragment)
                setToolBarTitle("知乎")
            }
            R.id.nav_menu_video -> {
                addFragment(supportFragmentManager, mVideoMainFragment, "VideoMainFragment")
                showFragment(supportFragmentManager, mVideoMainFragment)
                mToolbar.visibility = View.GONE
            }
            R.id.nav_menu_news -> {
                addFragment(supportFragmentManager, mNewsMainFragment, "NewsMainFragment")
                showFragment(supportFragmentManager, mNewsMainFragment)
                setToolBarTitle("新闻")
            }
            R.id.nav_menu_weather -> {
                addFragment(supportFragmentManager, mWeatherMainFragment, "WeatherFragment")
                showFragment(supportFragmentManager, mWeatherMainFragment)
                setToolBarTitle("天气")
                mPresenter?.addWeatherCount()
            }
            R.id.nav_menu_joke -> {
                addFragment(supportFragmentManager, mJokeMainFragment, "JokeMainFragment")
                showFragment(supportFragmentManager, mJokeMainFragment)
                setToolBarTitle("搞笑")
                mPresenter?.addJokeCount()
            }
            R.id.nav_menu_gank -> {
                addFragment(supportFragmentManager, mGankMainFragment, "GankMainFragment")
                showFragment(supportFragmentManager, mGankMainFragment)
                setToolBarTitle("技术")
            }
            R.id.nav_menu_about -> {
                addFragment(supportFragmentManager, mAboutFragment, "AboutFragment")
                showFragment(supportFragmentManager, mAboutFragment)
                setToolBarTitle("关于")
            }
        }
        mMainDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                //将滑动菜单显示出来//这个是HomeAsUp按钮的id永远都是android.R.id.home
                mMainDrawer.openDrawer(GravityCompat.START)
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mUserIconIv -> {
                //顶级函数   先判断用户是否已经登录  再进行更新头像
                afterLoginOperation {
                    updateUserIcon()
                }
            }
            R.id.mSignInTv -> {
                afterLoginOperation {
                    val intent = Intent(this, SignInActivity::class.java)
                    intent.putExtra("isSigned", mIsSigned)
                    //放大 :转场动画
                    val activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(mSignInTv,
                            mSignInTv.width / 2, mSignInTv.height / 2, 0, 0)
                    ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SIGN_IN,
                            activityOptionsCompat.toBundle())
                }
            }
            else -> {
            }
        }
    }

    override fun showUserIcon(path: String?) {
        if (path?.isEmpty() == false) {
            GlideUtils.loadImageView(mContext, path, mUserIconIv)
        }
    }

    override fun isUserLogin(isLogin: Boolean) {
        mSignInTv.visibility = if (isLogin) View.VISIBLE else View.GONE
    }

    /**
     * 更新用户头像
     */
    private fun updateUserIcon() {
        //请求权限
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { granted ->
            if (granted) {   //6.0之前的总是true?   小米的不是,vivo的不是
                //获取到权限
                Logger.e("获取到权限")
                Matisse.from(mContext as Activity)
                        .choose(MimeType.ofImage())    //展示资源类型  只展示图片
                        .countable(false)    //是否显示数字
                        .maxSelectable(1)  //最大选择资源数量
                        .capture(true)  //是否可以拍照
                        .captureStrategy(CaptureStrategy(true, "com.xfhy.lifehelper.fileprovider"))
                        .gridExpectedSize(displayMetrics.widthPixels / 3)   //设置列宽  这里为屏幕宽的1/3
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)  //设置屏幕方向
                        .thumbnailScale(0.85f)  //图片缩放比例
                        .imageEngine(CustomGlideEngine()) //图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE)  //请求码
            } else {
                //未获取到权限
                toast("未获取到权限")
            }
        }
    }

    override fun userSigned() {
        mSignInTv.text = "已签到"
        mIsSigned = true
    }

    private fun hideAllFragment(fragmentManager: FragmentManager) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (!mDailyMainFragment.isHidden) {
            fragmentTransaction.hide(mDailyMainFragment)
        }
        if (!mVideoMainFragment.isHidden) {
            fragmentTransaction.hide(mVideoMainFragment)
        }
        if (!mNewsMainFragment.isHidden) {
            fragmentTransaction.hide(mNewsMainFragment)
        }
        if (!mGankMainFragment.isHidden) {
            fragmentTransaction.hide(mGankMainFragment)
        }
        if (!mJokeMainFragment.isHidden) {
            fragmentTransaction.hide(mJokeMainFragment)
        }
        if (!mWeatherMainFragment.isHidden) {
            fragmentTransaction.hide(mWeatherMainFragment)
        }
        if (!mAboutFragment.isHidden) {
            fragmentTransaction.hide(mAboutFragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, tag: String) {
        if (!fragment.isAdded && fragmentManager.findFragmentByTag(tag) == null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.mMainContentFl, fragment, tag)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    private fun showFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.show(fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_CHOOSE -> {
                mPresenter?.dealWithChoosePicture(data)
            }
            REQUEST_CODE_SIGN_IN -> {
                val signSuccess = data?.getBooleanExtra(SignInActivity.EXTRA_IS_SIGN_SUCCESS, false) ?: false
                mSignInTv.text = if (signSuccess) "已签到" else "签到"
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {

            if (mMainDrawer.isDrawerOpen(GravityCompat.START)) {
                mMainDrawer.closeDrawer(GravityCompat.START)
                return true
            }

            //内涵段子(可能在全屏播放视频)那边判断一下  是否已经处理了返回事件
            return if (mJokeMainFragment.onBackPress()) {
                true
            } else {
                if (System.currentTimeMillis() - lastClickTime < BACK_TO_EXIT_TIME) {
                    AppManager.instance.exitApp(mContext)
                } else {
                    toast("再按一次退出")
                    lastClickTime = System.currentTimeMillis()
                }
                true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        //当用户按返回键时,首先关闭抽屉布局
        if (mMainDrawer.isDrawerOpen(GravityCompat.START)) {
            mMainDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * 广播 监听标题
     */
    inner class MainLocalBroadcast : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //标题
            val title = intent?.getStringExtra(BroadcastConstant.TITLE_CHANGE_TITLE)
            //隐藏标题栏?
            val hideTitleBar = intent?.getBooleanExtra(BroadcastConstant.TITLE_HIDE_TITLE, false) ?: false
            //打开抽屉菜单
            val openDrawer = intent?.getBooleanExtra(BroadcastConstant.OPEN_DRAWER, false) ?: false
            //切换界面 根据用户喜好
            val switchDrawer = intent?.getStringExtra(BroadcastConstant.SWITCH_DRAWER)
            switchDrawer?.let {
                hideAllFragment(supportFragmentManager)
                mToolbar.visibility = View.VISIBLE
                when (it) {
                    "知乎" -> {
                        addFragment(supportFragmentManager, mDailyMainFragment, "ZHLatestDailyFragment")
                        showFragment(supportFragmentManager, mDailyMainFragment)
                        mMainNavigaView.setCheckedItem(R.id.nav_menu_zhihu)
                    }
                    "视频" -> {
                        addFragment(supportFragmentManager, mVideoMainFragment, "VideoMainFragment")
                        showFragment(supportFragmentManager, mVideoMainFragment)
                        mToolbar.visibility = View.GONE
                        mMainNavigaView.setCheckedItem(R.id.nav_menu_video)
                    }
                    "新闻" -> {
                        addFragment(supportFragmentManager, mNewsMainFragment, "NewsMainFragment")
                        showFragment(supportFragmentManager, mNewsMainFragment)
                        setToolBarTitle("新闻")
                        mMainNavigaView.setCheckedItem(R.id.nav_menu_news)
                    }
                    "天气" -> {
                        addFragment(supportFragmentManager, mWeatherMainFragment, "WeatherFragment")
                        showFragment(supportFragmentManager, mWeatherMainFragment)
                        setToolBarTitle("天气")
                        mPresenter?.addWeatherCount()
                        mMainNavigaView.setCheckedItem(R.id.nav_menu_weather)
                    }
                    "笑话" -> {
                        addFragment(supportFragmentManager, mJokeMainFragment, "JokeMainFragment")
                        showFragment(supportFragmentManager, mJokeMainFragment)
                        setToolBarTitle("搞笑")
                        mPresenter?.addJokeCount()
                        mMainNavigaView.setCheckedItem(R.id.nav_menu_joke)
                    }
                    "技术" -> {
                        addFragment(supportFragmentManager, mGankMainFragment, "GankMainFragment")
                        showFragment(supportFragmentManager, mGankMainFragment)
                        setToolBarTitle("技术")
                        mMainNavigaView.setCheckedItem(R.id.nav_menu_gank)
                    }
                    else -> {
                        addFragment(supportFragmentManager, mDailyMainFragment, "ZHLatestDailyFragment")
                        showFragment(supportFragmentManager, mDailyMainFragment)
                        mMainNavigaView.setCheckedItem(R.id.nav_menu_zhihu)
                    }
                }
            }

            title?.let {
                setToolBarTitle(title)
            }
            mToolbar.visibility = if (hideTitleBar) View.GONE else View.VISIBLE
            if (openDrawer) {
                mMainDrawer.openDrawer(Gravity.START)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //取消注册
        mLocalBroadcastManager.unregisterReceiver(mMainBroadcast)
    }

}
