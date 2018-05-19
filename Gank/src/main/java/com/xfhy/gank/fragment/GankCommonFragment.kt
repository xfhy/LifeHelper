package com.xfhy.gank.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xfhy.gank.R
import com.xfhy.gank.activity.GankDetailsActivity
import com.xfhy.gank.adapter.GankCommonAdapter
import com.xfhy.gank.data.bean.GankCommonBean
import com.xfhy.gank.presenter.GankCommonContract
import com.xfhy.gank.presenter.GankCommonPresenter
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import kotlinx.android.synthetic.main.layout_refresh_common_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by xfhy on 2018/3/20 13:19
 * Description : Gank Android,IOS,前端通用fragment
 */
class GankCommonFragment : BaseStateMVPFragment<GankCommonPresenter>(), GankCommonContract.View, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener<GankCommonBean.Result, BaseViewHolder> {

    companion object {

        fun newInstance(gankType: String): GankCommonFragment {

            val args = Bundle()
            args.putString("gank_type", gankType)

            val fragment = GankCommonFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var mGankType: String = ""
    private val mGankAdapter: GankCommonAdapter by lazy {
        GankCommonAdapter(null)
    }

    override fun getLayoutResId(): Int {
        return R.layout.gank_common_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun initPresenter() {
        mPresenter = GankCommonPresenter(this)
    }

    private fun initView() {
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
        mGankAdapter.openLoadAnimation()
        mGankAdapter.setEnableLoadMore(true)
        mRecyclerView.adapter = mGankAdapter

        mGankAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRefreshLayout.setOnRefreshListener { mPresenter?.refreshData() }
        mGankAdapter.onItemClickListener = this
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mGankType = arguments?.getString("gank_type") ?: ""
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mPresenter?.reqGankData(mGankType, true)
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData()
    }

    override fun loadMoreSuccess(gankDataList: MutableList<GankCommonBean.Result>?) {
        mGankAdapter.loadMoreComplete()
        mGankAdapter.addData(gankDataList)
    }

    override fun loadMoreFail() {
        mGankAdapter.loadMoreFail()
    }

    override fun showGankData(gankDataList: MutableList<GankCommonBean.Result>?) {
        closeRefresh()
        mGankAdapter.replaceData(gankDataList)
    }

    override fun closeRefresh() {
        mRefreshLayout?.isRefreshing = false
    }

    override fun onItemClick(adapter: BaseQuickAdapter<GankCommonBean.Result, BaseViewHolder>, view: View, position: Int) {
        adapter.data?.get(position)?.let {
            val intent = Intent(activity, GankDetailsActivity::class.java)
            intent.putExtra("title", it.desc)
            intent.putExtra("url", it.url)
            startActivity(intent)
        }
    }

}