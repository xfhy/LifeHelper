package com.xfhy.news.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.orhanobut.logger.Logger
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.news.R
import com.xfhy.news.activity.NewsDetailsActivity
import com.xfhy.news.adapter.NewsItemAdapter
import com.xfhy.news.data.bean.NewsBean
import com.xfhy.news.data.bean.NewsSortBean
import com.xfhy.news.presenter.NewsCommonContract
import com.xfhy.news.presenter.impl.NewsCommonPresenter
import kotlinx.android.synthetic.main.layout_refresh_common_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by xfhy on 2018/3/18 14:00
 * Description : 新闻通用fragment
 */
class NewsCommonFragment : BaseStateMVPFragment<NewsCommonPresenter>(), NewsCommonContract.View, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener<NewsBean.Result.Item, BaseViewHolder>, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance(type: NewsSortBean): NewsCommonFragment {

            val args = Bundle()
            args.putSerializable("news_type", type)

            val fragment = NewsCommonFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mNewsAdapter by lazy {
        NewsItemAdapter(null)
    }
    var mNewsType: NewsSortBean? = null

    override fun getLayoutResId(): Int {
        return R.layout.news_fragment_common
    }

    override fun initPresenter() {
        mPresenter = NewsCommonPresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initIntentData()
    }

    private fun initView() {
        //下拉刷新颜色
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        mNewsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
        mNewsAdapter.setEnableLoadMore(true)
        mNewsAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.adapter = mNewsAdapter

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = linearLayoutManager

        mNewsAdapter.onItemClickListener = this
        mRefreshLayout.setOnRefreshListener(this)
    }

    private fun initIntentData() {
        mNewsType = arguments?.getSerializable("news_type") as? NewsSortBean
    }

    override fun lazyLoad() {
        mStateView = view?.findViewById(R.id.sfl_state_view)
        mNewsType = arguments?.getSerializable("news_type") as? NewsSortBean
        //不是空才去请求数据
        if (!mNewsType?.newsType.isNullOrEmpty()) {
            isLoad = true
            mPresenter?.reqNewsByType(mNewsType?.newsType ?: "")
        } else {
            //未加载过数据
            isLoad = false
        }
    }

    fun setNewsType(newsType: NewsSortBean?) {
        mNewsType = newsType
        //未加载过,才去加载
        if (!isLoad) {
            mPresenter?.reqNewsByType(mNewsType?.newsType ?: "")
        }
    }

    override fun showNewsList(newsList: MutableList<NewsBean.Result.Item>?) {
        closeRefresh()
        mNewsAdapter.replaceData(newsList)
    }

    override fun loadMoreSuccess(newsList: MutableList<NewsBean.Result.Item>?) {
        mNewsAdapter.loadMoreComplete()
        mNewsAdapter.addData(newsList)
    }

    override fun loadMoreFail() {
        mNewsAdapter.loadMoreFail()
    }

    override fun closeRefresh() {
        mRefreshLayout?.isRefreshing = false
    }

    override fun onRefresh() {
        mPresenter?.refreshNewsData()
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<NewsBean.Result.Item, BaseViewHolder>, view: View, position: Int) {
        val itemData = adapter.data?.get(position) ?: NewsBean.Result.Item()
        startActivity<NewsDetailsActivity>("news_data" to itemData)
    }

}