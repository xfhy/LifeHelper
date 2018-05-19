package com.xfhy.video.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.fragment.BaseStateMVPFragment
import com.xfhy.video.R
import com.xfhy.video.activity.TypeDetailsListActivity
import com.xfhy.video.adapter.TypeVideoAdapter
import com.xfhy.video.data.bean.VideoTypeBean
import com.xfhy.video.presenter.VideoTypeContract
import com.xfhy.video.presenter.impl.VideoTypePresenter
import com.xfhy.video.widget.VideoTypeDecoration
import kotlinx.android.synthetic.main.video_fragment_type.*

/**
 * Created by xfhy on 2018/3/10 22:12
 * Description : 视频 分类
 */
class VideoTypeFragment : BaseStateMVPFragment<VideoTypePresenter>(), VideoTypeContract.View, BaseQuickAdapter.OnItemClickListener<VideoTypeBean, BaseViewHolder> {

    companion object {

        fun newInstance(): VideoTypeFragment {

            val args = Bundle()

            val fragment = VideoTypeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var mTypeAdapter: TypeVideoAdapter? = null

    override fun getLayoutResId(): Int {
        return R.layout.video_fragment_type
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun initPresenter() {
        mPresenter = VideoTypePresenter()
        mPresenter?.mView = this
    }

    private fun initView() {
        mTypeAdapter = TypeVideoAdapter(null)
        //底布局
        val footerView = layoutInflater.inflate(R.layout.video_footer_type_main, mStateView, false)
        footerView.isEnabled = false
        mTypeAdapter?.addFooterView(footerView)

        mTypeAdapter?.onItemClickListener = this

        mVideoTypeRv.layoutManager = GridLayoutManager(activity, 2)
        mVideoTypeRv.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        mVideoTypeRv.adapter = mTypeAdapter
        //分割线
        mVideoTypeRv.addItemDecoration(VideoTypeDecoration())
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mStateView = view?.findViewById(R.id.sfl_video_type)
        mPresenter?.reqVideoTypesFromNet()
    }

    override fun closeRefresh() {
    }

    override fun showTypes(types: MutableList<VideoTypeBean>?) {
        mTypeAdapter?.replaceData(types)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<VideoTypeBean, BaseViewHolder>, view: View, position: Int) {
        context?.let {
            TypeDetailsListActivity.enterTypeDetailsActivity(it, adapter.data?.get(position))
        }
    }

}