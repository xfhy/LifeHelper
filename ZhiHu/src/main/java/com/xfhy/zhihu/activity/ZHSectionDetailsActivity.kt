package com.xfhy.zhihu.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.DevicesUtils
import com.xfhy.library.utils.SnackbarUtil
import com.xfhy.zhihu.R
import com.xfhy.zhihu.adapter.ZHSectionDetailsAdapter
import com.xfhy.zhihu.data.bean.ColumnDailyDetailsBean
import com.xfhy.zhihu.presenter.ZHSectionDetailsContract
import com.xfhy.zhihu.presenter.impl.ZHSectionDetailsPresenter
import kotlinx.android.synthetic.main.layout_refresh_common_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * author xfhy
 * time create at 2018年3月10日08:41:32
 * description 知乎 专栏详情列表
 */
class ZHSectionDetailsActivity : BaseMvpActivity<ZHSectionDetailsPresenter>(), ZHSectionDetailsContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener<ColumnDailyDetailsBean.StoriesBean, BaseViewHolder> {

    companion object {

        private val SECTION_ID = "section_id"
        private val SECTION_NAME = "section_name"

        /**
         * 进入知乎专栏详情列表 Activity
         *
         * @param context   Context
         * @param sectionId 日报id
         */
        fun enterZHSectionDetailsActi(context: Context, sectionId: Int, sectionName: String) {
            val intent = Intent(context, ZHSectionDetailsActivity::class.java)
            intent.putExtra(SECTION_NAME, sectionName)
            intent.putExtra(SECTION_ID, sectionId)
            context.startActivity(intent)
        }
    }

    /**
     * 专栏id
     */
    private var sectionId: String? = null

    private var mSectionsAdapter: ZHSectionDetailsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zh_section_details)

        initView()
        initIntentData()
        initData()
    }

    private fun initView() {
        //设置刷新颜色
        mRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        mSectionsAdapter = ZHSectionDetailsAdapter(R.layout
                .item_zh_common_daily_list, null, mContext)
        mSectionsAdapter?.openLoadAnimation()
        // 循环播放动画
        mSectionsAdapter?.isFirstOnly(false)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mSectionsAdapter

        mRefreshLayout.setOnRefreshListener(this)
        mSectionsAdapter?.onItemClickListener = this

    }

    private fun initIntentData() {
        val intent = intent
        if (intent != null) {
            sectionId = intent.getIntExtra(SECTION_ID, -1).toString()
            val sectionName = intent.getStringExtra(SECTION_NAME)
            setToolBarTitle(sectionName)
        } else {
            setToolBarTitle("...")
        }
    }

    override fun initPresenter() {
        mPresenter = ZHSectionDetailsPresenter()
        mPresenter?.mView = this
    }

    private fun initData() {
        mPresenter?.reqDataFromNet(sectionId ?: "")
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
        sfl_state_view.showLoading()
    }

    override fun hideLoading() {
        sfl_state_view.showContent()
    }

    override fun showErrorMsg(errorMsg: String) {
        hideLoading()
        closeRefresh()
        SnackbarUtil.showBarLongTime(mRefreshLayout, errorMsg, SnackbarUtil.ALERT)
    }

    override fun showEmptyView() {
        hideLoading()
        closeRefresh()
        sfl_state_view.showEmpty(R.string.stfEmptyMessage, R.string.stfButtonRetry)
    }

    private fun closeRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun showOffline() {
        closeRefresh()
        hideLoading()
        SnackbarUtil.showBarLongTime(sfl_state_view, resources.getString(R.string.stfOfflineMessage), SnackbarUtil
                .WARNING, resources.getString(R.string.stfButtonSetting),
                View.OnClickListener {
                    //未联网  跳转到设置界面
                    DevicesUtils.goSetting(mContext)
                })
    }

    override fun showContent() {
        closeRefresh()
        sfl_state_view.showContent()
    }

    override fun loadSuccess(dataBeans: MutableList<ColumnDailyDetailsBean.StoriesBean>) {
        closeRefresh()
        sfl_state_view.showContent()
        mSectionsAdapter?.replaceData(dataBeans)
    }

    override fun onRefresh() {
        mPresenter?.refreshData(sectionId ?: "")
    }

    override fun onItemClick(adapter: BaseQuickAdapter<ColumnDailyDetailsBean.StoriesBean, BaseViewHolder>, view: View, position: Int) {
        ZHDailyDetailsActivity.enterZHDailyDetailsActi(mContext, mPresenter?.getDailyId(position) ?: 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
