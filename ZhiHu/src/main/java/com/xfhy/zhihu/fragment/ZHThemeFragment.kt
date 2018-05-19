package com.xfhy.zhihu.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.View

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.zhihu.R
import com.xfhy.zhihu.activity.ThemeActivity
import com.xfhy.zhihu.adapter.ZHThemeAdapter
import com.xfhy.zhihu.data.bean.TopicDailyListBean
import com.xfhy.zhihu.presenter.ZHThemeContract
import com.xfhy.zhihu.presenter.impl.ZHThemePresenter
import kotlinx.android.synthetic.main.layout_refresh_common_list.*

/**
 * @author xfhy
 * create at 2018年3月10日16:45:58
 * description：知乎主题列表
 */
class ZHThemeFragment : BaseStateMVPFragment<ZHThemePresenter>(),
        ZHThemeContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener<TopicDailyListBean.OtherBean, BaseViewHolder> {

    companion object {

        /**
         * 主题列表列数
         */
        private val SPAN_COUNT = 2

        fun newInstance(): ZHThemeFragment {

            val args = Bundle()

            val fragment = ZHThemeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mThemeAdapter: ZHThemeAdapter? = null

    override fun getLayoutResId(): Int {
        return R.layout.layout_refresh_common_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun initPresenter() {
        mPresenter = ZHThemePresenter()
        mPresenter?.mView = this
    }

    private fun initView() {
        sendTitleChangeBroadcast("知乎·主题")
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        activity?.let { mThemeAdapter = ZHThemeAdapter(R.layout.item_zh_theme, null, it) }
        mThemeAdapter?.openLoadAnimation()
        mThemeAdapter?.isFirstOnly(false)
        val gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)
        mRecyclerView.layoutManager = gridLayoutManager
        mRecyclerView.adapter = mThemeAdapter

        mRefreshLayout.setOnRefreshListener(this)
        mThemeAdapter?.onItemClickListener = this
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

    override fun closeRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun loadSuccess(othersBeans: MutableList<TopicDailyListBean.OtherBean>) {
        closeRefresh()
        mStateView?.showContent()
        mThemeAdapter?.replaceData(othersBeans)
    }

    override fun onRefresh() {
        mPresenter?.refreshData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<TopicDailyListBean.OtherBean, BaseViewHolder>, view: View, position: Int) {
        val othersBeans = adapter.data
        if (othersBeans != null && othersBeans.size > position) {
            activity?.let { ThemeActivity.enterZHThemeDetailsActi(it, othersBeans[position].id ?: -1) }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            //设置标题
            sendTitleChangeBroadcast("知乎·主题")
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

}
