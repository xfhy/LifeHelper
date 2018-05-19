package com.xfhy.zhihu.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xfhy.zhihu.R

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.zhihu.activity.ZHDailyDetailsActivity
import com.xfhy.zhihu.adapter.ZHHotAdapter
import com.xfhy.zhihu.data.bean.HotDailyBean
import com.xfhy.zhihu.presenter.ZHHotContract
import com.xfhy.zhihu.presenter.impl.ZHHotPresenter
import kotlinx.android.synthetic.main.layout_refresh_common_list.*

/**
 * @author xfhy
 * create at 2018年3月10日16:45:45
 * description：知乎最热文章fragment
 */
class ZHHotFragment : BaseStateMVPFragment<ZHHotPresenter>(),
        ZHHotContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener<HotDailyBean.RecentBean, BaseViewHolder> {

    companion object {

        fun newInstance(): ZHHotFragment {

            val args = Bundle()

            val fragment = ZHHotFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mHotAdapter: ZHHotAdapter? = null

    override fun getLayoutResId(): Int {
        return R.layout.layout_refresh_common_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun initPresenter() {
        mPresenter = ZHHotPresenter()
        mPresenter?.mView = this
    }

    private fun initView() {
        sendTitleChangeBroadcast("知乎·热门")
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        mHotAdapter = ZHHotAdapter(R.layout.item_zh_common_daily_list, null)
        mHotAdapter?.openLoadAnimation()
        mHotAdapter?.isFirstOnly(false)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mHotAdapter

        mRefreshLayout.setOnRefreshListener(this)
        mHotAdapter?.onItemClickListener = this
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mPresenter?.reqDataFromNet()
    }

    override fun showLoading() {
        mStateView?.showLoading()
    }

    override fun hideLoading() {
        mStateView?.showContent()
    }

    override fun showOffline() {
        closeRefresh()
        if (mHotAdapter?.itemCount ?: 0 == 0) {
            super.showOffline()
        }
    }

    override fun closeRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun loadSuccess(dataBeans: MutableList<HotDailyBean.RecentBean>) {
        closeRefresh()
        mStateView?.showContent()
        mHotAdapter?.replaceData(dataBeans)
    }

    override fun onRefresh() {
        mPresenter?.refreshData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<HotDailyBean.RecentBean, BaseViewHolder>, view: View, position: Int) {
        activity?.let {
            ZHDailyDetailsActivity.enterZHDailyDetailsActi(it, mPresenter?.getDailyId(position) ?: 0)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            //设置标题
            sendTitleChangeBroadcast("知乎·热门")
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

}
