package com.xfhy.video.activity

import android.os.Bundle
import android.view.View
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.SoftKeyboardUtil
import com.xfhy.library.widgets.EditTextWithDelete
import com.xfhy.video.R
import com.xfhy.video.adapter.HotSearchKeyAdapter
import com.xfhy.video.adapter.SearchVideoAdapter
import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.presenter.VideoSearchContract
import com.xfhy.video.presenter.impl.VideoSearchPresenter
import kotlinx.android.synthetic.main.video_activity_search.*
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.video.util.VideoInfoConvert
import org.jetbrains.anko.startActivity

/**
 * Created by xfhy on 2018/3/17 20:56
 * Description : 视频搜索页
 */
class VideoSearchActivity : BaseMvpActivity<VideoSearchPresenter>(),
        VideoSearchContract.View,
        EditTextWithDelete.OnEditTextListener,
        BaseQuickAdapter.OnItemClickListener<HomeDataBean.ItemList, BaseViewHolder>, BaseQuickAdapter.RequestLoadMoreListener {

    private val mHotKeyAdapter by lazy { HotSearchKeyAdapter(null) }
    private val mVideoListAdapter by lazy { SearchVideoAdapter(null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_activity_search)

        initView()
    }

    override fun initPresenter() {
        mPresenter = VideoSearchPresenter(this)
    }

    private fun initView() {
        mHotKeyRv.adapter = mHotKeyAdapter
        mSearchVideoRv.adapter = mVideoListAdapter
        mVideoListAdapter.openLoadAnimation()
        mVideoListAdapter.setEnableLoadMore(true)
        mVideoListAdapter.setOnLoadMoreListener(this, mSearchVideoRv)

        mSearchEtwd.setOnEditTextListener(this)
        mVideoListAdapter.onItemClickListener = this
        //热搜固定关键词列表点击事件
        mHotKeyAdapter.onClick = { key ->
            mPresenter?.searchVideoByKey(key ?: "")
            mSearchEtwd.text = (key ?: "")
        }
        mCancelTv.setOnClickListener { finish() }

        mPresenter?.reqHotSearchKeys()
    }

    override fun showHotSearchKeys(keys: MutableList<String>?) {
        mHotKeyAdapter.replaceData(keys)
    }

    override fun showSearchVideoList(videoList: MutableList<HomeDataBean.ItemList>?) {
        mFixedHotKeyLayout.visibility = View.GONE
        mSearchVideoRv.visibility = View.VISIBLE
        mSearchVideoRv.scrollToPosition(0)
        mVideoListAdapter.replaceData(videoList)
    }

    override fun loadMoreSuccess(videoList: MutableList<HomeDataBean.ItemList>?) {
        mVideoListAdapter.loadMoreComplete()
        mVideoListAdapter.addData(videoList)
    }

    override fun loadMoreFail() {
        mVideoListAdapter.loadMoreFail()
    }

    override fun loadNoMoreData() {
        mVideoListAdapter.loadMoreEnd(true)
        //底布局
        val footerView = layoutInflater.inflate(R.layout.video_footer_type_main, mSearchVideoRv, false)
        footerView.isEnabled = false
        mVideoListAdapter.addFooterView(footerView)
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData()
    }

    override fun onEnterClick(key: String) {
        mPresenter?.searchVideoByKey(key)
        SoftKeyboardUtil.hideSoftInput(mContext, mSearchEtwd)
    }

    override fun onTextChange(textContent: String) {
        if (textContent.isEmpty()) {
            mFixedHotKeyLayout.visibility = View.VISIBLE
            mSearchVideoRv.visibility = View.GONE
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<HomeDataBean.ItemList, BaseViewHolder>, view: View, position: Int) {
        //转一下对象 再进行传递数据
        val itemData = adapter.data?.get(position)
        startActivity<VideoDetailsActivity>("home_data_bean_item_list" to
                VideoInfoConvert.convertV5ItemData(itemData))
    }

}