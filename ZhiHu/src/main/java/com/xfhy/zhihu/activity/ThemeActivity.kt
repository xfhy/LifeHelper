package com.xfhy.zhihu.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.DevicesUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.library.utils.SnackbarUtil
import com.xfhy.zhihu.R
import com.xfhy.zhihu.adapter.ZHThemeDetailsAdapter
import com.xfhy.zhihu.data.bean.ThemeDailyDetailsBean
import com.xfhy.zhihu.presenter.ZHThemeDetailsContract
import com.xfhy.zhihu.presenter.impl.ZHThemeDetailsPresenter
import kotlinx.android.synthetic.main.activity_theme.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 2018年3月10日08:41:50
 * 主题详情列表页
 */
class ThemeActivity : BaseMvpActivity<ZHThemeDetailsPresenter>(),
        ZHThemeDetailsContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener<ThemeDailyDetailsBean.Story, BaseViewHolder> {

    companion object {

        private val THEME_ID = "theme_id"

        /**
         * 进入知乎主题详情 Activity
         *
         * @param context Context
         * @param id      日报id
         */
        fun enterZHThemeDetailsActi(context: Context, id: Int) {
            val intent = Intent(context, ThemeActivity::class.java)
            intent.putExtra(THEME_ID, id)
            context.startActivity(intent)
        }
    }

    /**
     * 当前日报类别的id
     */
    private var mThemeId = -1
    private var mThemeDetailsAdapter: ZHThemeDetailsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        initView()
        initIntentData()
        initData()
    }

    override fun initPresenter() {
        mPresenter = ZHThemeDetailsPresenter()
        mPresenter?.mView = this
    }

    private fun initView() {
        //设置默认标题栏文字
        setToolBarTitle("...")
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        mThemeDetailsAdapter = ZHThemeDetailsAdapter(R.layout
                .item_zh_theme_details, null, mContext)
        rvThDetailsList.layoutManager = LinearLayoutManager(this)
        rvThDetailsList.adapter = mThemeDetailsAdapter

        //动画
        mThemeDetailsAdapter?.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        // 循环播放动画
        mThemeDetailsAdapter?.isFirstOnly(false)

        mRefreshLayout.setOnRefreshListener(this)
        mThemeDetailsAdapter?.onItemClickListener = this

        //为解决SwipeRefreshLayout和RecyclerView冲突  详情见项目doc文档
        mDetailsAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            /*
                * 如果竖直的偏移大于0,则设置SwipeRefreshLayout为可用.
                    当我在AppBarLayout可见时,手机由下往上滑动时,verticalOffset<0;
                    当手指由上往下,滑动到顶部,这时手指再由上往下滑动时,verticalOffset=0,
                    这时可以设置SwipeRefreshLayout为可用了.
                * */
            mRefreshLayout.isEnabled = (verticalOffset >= 0)
        })

    }

    private fun initIntentData() {
        val intent = intent
        mThemeId = intent.getIntExtra(THEME_ID, -1)
    }

    private fun initData() {
        mPresenter?.reqDataFromNet(mThemeId.toString())
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

    override fun showLoading() {
        mDetailsStateView.showLoading()
    }

    private fun closeLoading() {
        mDetailsStateView.showContent()
    }

    override fun showErrorMsg(errorMsg: String) {
        closeLoading()
        SnackbarUtil.showBarLongTime(mDetailsStateView, errorMsg, SnackbarUtil.ALERT)
    }

    override fun showEmptyView() {
        closeRefresh()
        mDetailsStateView.showEmpty(R.string.stfEmptyMessage, R.string.stfButtonRetry)
    }

    private fun closeRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun showOffline() {
        closeRefresh()
        closeLoading()
        SnackbarUtil.showBarLongTime(mDetailsStateView, resources.getString(R.string.stfOfflineMessage), SnackbarUtil
                .WARNING, resources.getString(R.string.stfButtonSetting),
                View.OnClickListener {
                    //未联网  跳转到设置界面
                    DevicesUtils.goSetting(mContext)
                })
    }

    override fun showContent() {
        closeRefresh()
        mDetailsStateView.showContent()
    }

    override fun loadSuccess(themeDailyDetailsBean: ThemeDailyDetailsBean) {
        closeRefresh()
        mDetailsStateView.showContent()
        setToolBarTitle(themeDailyDetailsBean.name ?: "...")
        mThemeDetailsAdapter?.replaceData(themeDailyDetailsBean.stories)
        GlideUtils.loadCustomImage(mContext, themeDailyDetailsBean.background, mThemeBgIv)
        mThemeDescriptionTv.text = themeDailyDetailsBean.description
    }

    override fun getThemeId(): Int {
        return mThemeId
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        mPresenter?.reqDataFromNet(mThemeId.toString())
    }

    override fun onItemClick(adapter: BaseQuickAdapter<ThemeDailyDetailsBean.Story, BaseViewHolder>, view: View, position: Int) {
        //进入日报详情页
        val themeDailyDetailsBean = mPresenter?.data
        if (themeDailyDetailsBean != null) {
            val stories = themeDailyDetailsBean.stories
            if (stories != null && stories.size > position) {
                ZHDailyDetailsActivity.enterZHDailyDetailsActi(mContext, stories[position].id ?: 0)
            }
        }
    }

}
