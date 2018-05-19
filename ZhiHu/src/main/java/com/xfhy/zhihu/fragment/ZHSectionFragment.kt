package com.xfhy.zhihu.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.zhihu.R
import com.xfhy.zhihu.activity.ZHSectionDetailsActivity
import com.xfhy.zhihu.adapter.ZHSectionAdapter
import com.xfhy.zhihu.data.bean.ColumnDailyBean
import com.xfhy.zhihu.presenter.ZHSectionContract
import com.xfhy.zhihu.presenter.impl.ZHSectionPresenter
import kotlinx.android.synthetic.main.layout_refresh_common_list.*

/**
 * @author feiyang
 * time create at 2018年3月10日16:45:53
 * description 知乎专栏fragment
 */
class ZHSectionFragment : BaseStateMVPFragment<ZHSectionPresenter>(),
        ZHSectionContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener<ColumnDailyBean.DataBean, BaseViewHolder> {

    companion object {

        fun newInstance(): ZHSectionFragment {

            val args = Bundle()

            val fragment = ZHSectionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mSectionAdapter: ZHSectionAdapter? = null

    override fun getLayoutResId(): Int {
        return R.layout.layout_refresh_common_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun initPresenter() {
        mPresenter = ZHSectionPresenter()
        mPresenter?.mView = this
    }

    private fun initView() {
        sendTitleChangeBroadcast("知乎·专栏")

        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        activity?.let { mSectionAdapter = ZHSectionAdapter(R.layout.item_zh_section, null, it) }

        //循环 动画
        mSectionAdapter?.openLoadAnimation()
        mSectionAdapter?.isFirstOnly(false)
        val linearLayoutManager = LinearLayoutManager(activity)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = mSectionAdapter

        mRefreshLayout.setOnRefreshListener(this)
        mSectionAdapter?.onItemClickListener = this
    }

    override fun showLoading() {
        mStateView?.showLoading()
    }

    override fun hideLoading() {
        mStateView?.showContent()
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mPresenter?.reqDataFromNet()
    }

    override fun loadSuccess(dataBeans: MutableList<ColumnDailyBean.DataBean>) {
        closeRefresh()
        mStateView?.showContent()
        mSectionAdapter?.replaceData(dataBeans)
    }

    override fun closeRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        mPresenter?.refreshData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<ColumnDailyBean.DataBean, BaseViewHolder>, view: View, position: Int) {
        activity?.let {
            ZHSectionDetailsActivity.enterZHSectionDetailsActi(it, mPresenter?.getSectionId(position) ?: 0,
                    mPresenter?.getSectionTitle(position) ?: "...")
        }

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            sendTitleChangeBroadcast("知乎·专栏")
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

}
