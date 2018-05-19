package com.xfhy.video.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.video.R
import com.xfhy.video.activity.VideoDetailsActivity
import com.xfhy.video.adapter.HomeVideoAdapter
import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.presenter.VideoHomeContract
import com.xfhy.video.presenter.impl.VideoHomePresenter
import com.xfhy.video.util.VideoInfoConvert
import kotlinx.android.synthetic.main.video_fragment_home.*
import org.jetbrains.anko.startActivity

/**
 * Created by xfhy on 2018/3/10 22:12
 * Description : 视频 首页
 */
class VideoHomeFragment : BaseStateMVPFragment<VideoHomePresenter>(), VideoHomeContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener<HomeDataBean.ItemList, BaseViewHolder>, BaseQuickAdapter.RequestLoadMoreListener {

    companion object {

        fun newInstance(): VideoHomeFragment {

            val args = Bundle()

            val fragment = VideoHomeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var mVideoAdapter: HomeVideoAdapter? = null

    override fun initPresenter() {
        mPresenter = VideoHomePresenter()
        mPresenter?.mView = this
    }

    override fun getLayoutResId(): Int {
        return R.layout.video_fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        activity?.let { mVideoAdapter = HomeVideoAdapter(it, null) }
        mVideoAdapter?.openLoadAnimation()
        mVideoAdapter?.isFirstOnly(false)
        mVideoAdapter?.setEnableLoadMore(true)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mVideoAdapter

        mRefreshLayout.setOnRefreshListener(this)
        mVideoAdapter?.onItemClickListener = this
        mVideoAdapter?.setOnLoadMoreListener(this, mRecyclerView)
    }

    override fun closeRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mPresenter?.reqVideoDailyDataFromNet()
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData()
    }

    override fun onRefresh() {
        mPresenter?.reqVideoDailyDataFromNet()
    }

    override fun showVideoItemList(homeDataBean: HomeDataBean?) {
        closeRefresh()
        mVideoAdapter?.setNewData(homeDataBean?.itemList)
    }

    override fun loadMoreSuccess(homeDataBean: HomeDataBean?) {
        mVideoAdapter?.loadMoreComplete()
        mVideoAdapter?.addData(homeDataBean?.itemList)
    }

    override fun loadMoreFailed() {
        mVideoAdapter?.loadMoreFail()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<HomeDataBean.ItemList, BaseViewHolder>, view: View, position: Int) {
        val itemData = adapter.data?.get(position)
        activity?.startActivity<VideoDetailsActivity>("home_data_bean_item_list" to
                VideoInfoConvert.convertV5ItemData(itemData))
    }

}