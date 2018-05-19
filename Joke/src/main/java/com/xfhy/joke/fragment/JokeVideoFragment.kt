package com.xfhy.joke.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xfhy.joke.R
import com.xfhy.joke.adapter.JokeVideoAdapter
import com.xfhy.joke.data.bean.JokeVideoBean
import com.xfhy.joke.presenter.JokeVideoContract
import com.xfhy.joke.presenter.impl.JokeVideoPresenter
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import kotlinx.android.synthetic.main.layout_refresh_common_list.*

/**
 * Created by xfhy on 2018/3/21 8:24
 * Description : 视频
 */
class JokeVideoFragment : BaseStateMVPFragment<JokeVideoPresenter>(), JokeVideoContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    companion object {

        fun newInstance(): JokeVideoFragment {

            val args = Bundle()

            val fragment = JokeVideoFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private val mVideoAdapter by lazy { JokeVideoAdapter(null) }

    override fun getLayoutResId(): Int {
        return R.layout.joke_fragment_video
    }

    override fun initPresenter() {
        mPresenter = JokeVideoPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        mVideoAdapter.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)
        mVideoAdapter.openLoadAnimation()
        mVideoAdapter.setEnableLoadMore(true)
        mRecyclerView.adapter = mVideoAdapter

        mVideoAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRefreshLayout.setOnRefreshListener { mPresenter?.refreshData() }
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mPresenter?.reqVideoData(true)
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData()
    }

    override fun showVideoList(jokeList: MutableList<JokeVideoBean.Item>?) {
        closeRefresh()
        mVideoAdapter.replaceData(jokeList)
    }

    override fun loadMoreSuccess(jokeList: MutableList<JokeVideoBean.Item>?) {
        mVideoAdapter.loadMoreComplete()
        mVideoAdapter.addData(jokeList)
    }

    override fun loadMoreFail() {
        mVideoAdapter.loadMoreFail()
    }

    override fun closeRefresh() {
        mRefreshLayout?.isRefreshing = false
    }

    override fun onBackPress(): Boolean {
        return mVideoAdapter.backToProtVideo()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser) {
            mVideoAdapter.onPause()
        }
    }

    override fun onPause() {
        super.onPause()
        mVideoAdapter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mVideoAdapter.onDestroy()
    }

}