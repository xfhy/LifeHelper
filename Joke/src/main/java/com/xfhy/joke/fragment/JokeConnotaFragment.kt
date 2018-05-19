package com.xfhy.joke.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xfhy.joke.R
import com.xfhy.joke.adapter.JokeAdapter
import com.xfhy.joke.data.bean.JokeBean
import com.xfhy.joke.presenter.JokeConnotaContract
import com.xfhy.joke.presenter.impl.JokeConnotaPresenter
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import kotlinx.android.synthetic.main.layout_refresh_common_list.*

/**
 * Created by xfhy on 2018/3/21 8:24
 * Description : 段子
 */
class JokeConnotaFragment : BaseStateMVPFragment<JokeConnotaPresenter>(),
        JokeConnotaContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    companion object {

        fun newInstance(): JokeConnotaFragment {

            val args = Bundle()

            val fragment = JokeConnotaFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private val mJokeAdapter by lazy { JokeAdapter(null) }

    override fun getLayoutResId(): Int {
        return R.layout.joke_fragment_joke
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
        mJokeAdapter.openLoadAnimation()
        mJokeAdapter.setEnableLoadMore(true)
        mRecyclerView.adapter = mJokeAdapter

        mJokeAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRefreshLayout.setOnRefreshListener { mPresenter?.refreshData() }
    }

    override fun initPresenter() {
        mPresenter = JokeConnotaPresenter(this)
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mPresenter?.reqJokeData(true)
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData()
    }

    override fun showJokeList(jokeList: MutableList<JokeBean.Item>?) {
        closeRefresh()
        mJokeAdapter.replaceData(jokeList)
    }

    override fun loadMoreSuccess(jokeList: MutableList<JokeBean.Item>?) {
        mJokeAdapter.loadMoreComplete()
        mJokeAdapter.addData(jokeList)
    }

    override fun loadMoreFail() {
        mJokeAdapter.loadMoreFail()
    }

    override fun closeRefresh() {
        mRefreshLayout?.isRefreshing = false
    }
}