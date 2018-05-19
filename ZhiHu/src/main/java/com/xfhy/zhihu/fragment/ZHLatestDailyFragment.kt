package com.xfhy.zhihu.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout


import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.library.utils.DensityUtil
import com.xfhy.library.utils.GlideUtils
import com.xfhy.library.widgets.EasyBanner
import com.xfhy.zhihu.R
import com.xfhy.zhihu.activity.ZHDailyDetailsActivity
import com.xfhy.zhihu.adapter.ZHLatestDailyAdapter
import com.xfhy.zhihu.data.bean.LatestDailyListBean
import com.xfhy.zhihu.data.bean.PastNewsBean
import com.xfhy.zhihu.presenter.ZHDailyLatestContract
import com.xfhy.zhihu.presenter.impl.ZHDailyLatestPresenter
import kotlinx.android.synthetic.main.layout_refresh_common_list.*

import java.util.ArrayList

/**
 * author xfhy
 * create at 2017/9/30 16:38
 * description：知乎最新日报fragment
 */
class ZHLatestDailyFragment : BaseStateMVPFragment<ZHDailyLatestPresenter>(),
        ZHDailyLatestContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener<LatestDailyListBean.StoriesBean, BaseViewHolder>,
        EasyBanner.OnItemClickListener,
        ZHLatestDailyAdapter.HeaderChangeListener {

    companion object {
        /**
         * banner所占高度
         */
        private val BANNER_HEIGHT = 200f

        fun newInstance(): ZHLatestDailyFragment {

            val args = Bundle()

            val fragment = ZHLatestDailyFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mDailyAdapter: ZHLatestDailyAdapter? = null
    /**
     * 过去的天数
     */
    private var pastDays = 1
    /**
     * 顶部轮播图
     */
    private var mBanner: EasyBanner? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewEvent()
        initBanner()
    }

    override fun initPresenter() {
        mPresenter = ZHDailyLatestPresenter()
        mPresenter?.mView = this
    }

    private fun initViewEvent() {
        mRefreshLayout?.setOnRefreshListener(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_zh_latest_daily
    }

    private fun initView() {
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        activity?.let {
            mDailyAdapter = ZHLatestDailyAdapter(it, null)
        }
        // 开启RecyclerView动画
        mDailyAdapter?.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        // 循环播放动画
        mDailyAdapter?.isFirstOnly(false)
        // 可加载更多
        mDailyAdapter?.setEnableLoadMore(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = mDailyAdapter
        //设置加载更多监听器
        mDailyAdapter?.setOnLoadMoreListener(this, mRecyclerView)

        // 当未满一屏幕时不刷新
        mDailyAdapter?.disableLoadMoreIfNotFullPage()
        // 设置RecyclerView的item监听
        mDailyAdapter?.onItemClickListener = this

        initBanner()

        //设置标题改变的listener
        mDailyAdapter?.setOnHeaderChangeListener(this)
    }

    override fun showLoading() {
        mStateView?.showLoading()
    }

    override fun hideLoading() {
        mStateView?.showContent()
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        // 动态生成banner
        activity?.let { mBanner = EasyBanner(it) }

        // 设置banner的大小
        var bannerLayoutParams: LinearLayout.LayoutParams? = null
        activity?.let {
            bannerLayoutParams = LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT, DensityUtil.dip2px(it, BANNER_HEIGHT))
        }

        mBanner?.layoutParams = bannerLayoutParams
        //设置banner图片加载器
        mBanner?.setImageLoader(object : EasyBanner.ImageLoader {
            override fun loadImage(imageView: ImageView, url: String?) {
                GlideUtils.loadCustomImage(activity, url, imageView)
            }
        })
        // 设置bannerItem监听事件
        mBanner?.setOnItemClickListener(this)
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mPresenter?.reqDailyDataFromNet()
    }

    override fun showLatestData(latestDailyListBean: LatestDailyListBean?) {
        if (latestDailyListBean == null) {
            mStateView?.showEmpty(R.string.stfEmptyMessage, R.string.stfButtonRetry)
            return
        }

        //提取数据源中的image地址和title
        val topStories = latestDailyListBean.topStories
        val topImageUrls = ArrayList<String>()
        val topContentData = ArrayList<String>()

        topStories?.forEach {
            topImageUrls.add(it.image ?: "")
            topContentData.add(it.title ?: "")
        }

        if (mDailyAdapter?.headerLayoutCount == 0) {
            // 添加banner
            mBanner?.let { mDailyAdapter?.addHeaderView(it) }
            //设置banner图片url和图片标题
            mBanner?.initBanner(topImageUrls, topContentData)
        } else {
            //设置banner图片url和图片标题
            mBanner?.resetData(topImageUrls, topContentData)
        }
        mBanner?.start()

        mDailyAdapter?.setNewData(latestDailyListBean.stories)

    }

    override fun loadMoreSuccess(groupTitle: String, pastNewsBean: PastNewsBean?) {
        mDailyAdapter?.loadMoreComplete()
        pastNewsBean?.stories?.let { mDailyAdapter?.addData(it) }
    }

    override fun loadMoreFailed() {
        mDailyAdapter?.loadMoreFail()

    }

    override fun onRefresh() {
        mPresenter?.reqDailyDataFromNet()
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData(pastDays++)
    }

    override fun closeRefresh() {
        mRefreshLayout?.isRefreshing = false
    }

    // RecyclerView的item点击事件
    override fun onItemClick(adapter: BaseQuickAdapter<LatestDailyListBean.StoriesBean, BaseViewHolder>, view: View, position: Int) {
        activity?.let {
            ZHDailyDetailsActivity.enterZHDailyDetailsActi(it, mPresenter?.getClickItemId(position) ?: 0)
        }
    }

    // mBanner的点击事件
    override fun onItemClick(position: Int, title: String) {
        activity?.let {
            ZHDailyDetailsActivity.enterZHDailyDetailsActi(it, mPresenter?.getHeaderClickItemId(position)
                    ?: 0)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            //设置标题
            sendTitleChangeBroadcast("知乎")
        }
        super.setUserVisibleHint(isVisibleToUser)
        if (!isCreated) {
            return
        }
        if (mBanner == null) {
            return
        }
        if (isVisibleToUser) {
            mBanner?.start()
        } else {
            mBanner?.stop()
        }
    }

    override fun onHeaderChanged(title: String?) {
        sendTitleChangeBroadcast(title ?: "知乎")
    }

}
