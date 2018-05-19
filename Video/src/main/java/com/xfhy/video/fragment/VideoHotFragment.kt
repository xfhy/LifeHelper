package com.xfhy.video.fragment

import android.os.Bundle
import android.view.View
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.video.R
import com.xfhy.video.activity.VideoDetailsActivity
import com.xfhy.video.adapter.HotSortCommonAdapter
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.presenter.VideoHotContract
import com.xfhy.video.presenter.impl.VideoHotPresenter
import com.xfhy.video.util.VideoInfoConvert
import kotlinx.android.synthetic.main.video_fragment_hot.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by xfhy on 2018/3/10 22:12
 * Description : 视频 排行
 */
class VideoHotFragment : BaseStateMVPFragment<VideoHotPresenter>(), VideoHotContract.View, BaseQuickAdapter.OnItemClickListener<VideoItemV4Bean.Item, BaseViewHolder> {

    companion object {
        fun newInstance(): VideoHotFragment {

            val args = Bundle()

            val fragment = VideoHotFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private val mWeeklyAdapter: HotSortCommonAdapter by lazy {
        HotSortCommonAdapter(null)
    }
    private val mMonthlyAdapter: HotSortCommonAdapter by lazy {
        HotSortCommonAdapter(null)
    }
    private val mAllAdapter: HotSortCommonAdapter by lazy {
        HotSortCommonAdapter(null)
    }

    override fun getLayoutResId(): Int {
        return R.layout.video_fragment_hot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initPresenter() {
        mPresenter = VideoHotPresenter()
        mPresenter?.mView = this
    }

    private fun initView() {
        mWeekSortRv.adapter = mWeeklyAdapter
        mMonthSortRv.adapter = mMonthlyAdapter
        mAllSortRv.adapter = mAllAdapter
        mWeeklyAdapter.onItemClickListener = this
        mMonthlyAdapter.onItemClickListener = this
        mAllAdapter.onItemClickListener = this
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_sort_view)
        mPresenter?.reqWeeklySortData()
        mPresenter?.reqMonthlySortData()
        mPresenter?.reqAllSortData()
    }

    override fun closeRefresh() {
    }

    override fun showWeeklySortData(dataBean: VideoItemV4Bean?) {
        mWeeklyAdapter.replaceData(dataBean?.itemList)
    }

    override fun showMonthlySortData(dataBean: VideoItemV4Bean?) {
        mMonthlyAdapter.replaceData(dataBean?.itemList)
    }

    override fun showAllSortData(dataBean: VideoItemV4Bean?) {
        mAllAdapter.replaceData(dataBean?.itemList)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<VideoItemV4Bean.Item, BaseViewHolder>, view: View, position: Int) {
        //转一下对象 再进行传递数据
        val itemData = adapter.data?.get(position)
        startActivity<VideoDetailsActivity>("home_data_bean_item_list" to
                VideoInfoConvert.convertV4ItemData(itemData))
    }

}