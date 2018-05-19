package com.xfhy.video.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.GlideUtils
import com.xfhy.library.utils.SnackbarUtil
import com.xfhy.video.R
import com.xfhy.video.adapter.HotTypeDetailsAdapter
import com.xfhy.video.data.bean.VideoItemV4Bean
import com.xfhy.video.data.bean.VideoTypeBean
import com.xfhy.video.presenter.TypeDetailsListContract
import com.xfhy.video.presenter.impl.TypeDetailsListPresenter
import com.xfhy.video.util.VideoInfoConvert
import kotlinx.android.synthetic.main.video_activity_type_details_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by xfhy on 2018/3/12 15:20
 * Description : 分类详情
 */
class TypeDetailsListActivity : BaseMvpActivity<TypeDetailsListPresenter>(),
        TypeDetailsListContract.View,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener<VideoItemV4Bean.Item, BaseViewHolder> {

    companion object {

        private const val VIDEO_TYPE_BEAN = "videoTypeBean"

        fun enterTypeDetailsActivity(context: Context, videoTypeBean: VideoTypeBean?) {
            context.startActivity<TypeDetailsListActivity>(VIDEO_TYPE_BEAN to videoTypeBean)
        }
    }

    private var mVideoAdapter: HotTypeDetailsAdapter? = null

    override fun initPresenter() {
        mPresenter = TypeDetailsListPresenter()
        mPresenter?.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_activity_type_details_list)

        initView()
        initIntentData()
    }

    private fun initView() {
        mVideoAdapter = HotTypeDetailsAdapter(null)
        mVideoAdapter?.openLoadAnimation()
        mVideoAdapter?.isFirstOnly(false)
        mVideoAdapter?.setEnableLoadMore(true)
        mVideoListRv.adapter = mVideoAdapter

        mVideoAdapter?.onItemClickListener = this
        mVideoAdapter?.setOnLoadMoreListener(this, mVideoListRv)
    }

    private fun initIntentData() {
        val videoTypeData = intent.getSerializableExtra(VIDEO_TYPE_BEAN) as? VideoTypeBean
        setToolBarTitle(videoTypeData?.name ?: "...")
        GlideUtils.loadCustomImage(mContext, videoTypeData?.headerImage, mTypeBgIv)
        mPresenter?.reqTypeDetailsList(videoTypeData?.id?.toString() ?: "")
    }

    override fun showTypeDetailsListData(videoItemV4Bean: VideoItemV4Bean?) {
        mVideoAdapter?.replaceData(videoItemV4Bean?.itemList)
    }

    override fun loadMoreSuccess(videoItemV4Bean: VideoItemV4Bean?) {
        mVideoAdapter?.loadMoreComplete()
        mVideoAdapter?.addData(videoItemV4Bean?.itemList)
    }

    override fun loadMoreFailure() {
        mVideoAdapter?.loadMoreFail()
        SnackbarUtil.showBarShortTime(mVideoListRv, "加载更多失败")
    }

    /**
     * 设置toolbar的标题
     * @param title    标题
     */
    private fun setToolBarTitle(title: String) {
        //setSupportActionBar之前设置标题
        mToolbar.title = title
        setSupportActionBar(mToolbar)
        val supportActionBar = supportActionBar
        //让导航按钮显示出来
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //设置导航按钮图标
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onLoadMoreRequested() {
        mPresenter?.loadMoreData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<VideoItemV4Bean.Item, BaseViewHolder>, view: View, position: Int) {
        //转一下对象 再进行传递数据
        val itemData = adapter.data?.get(position)
        startActivity<VideoDetailsActivity>("home_data_bean_item_list" to
                VideoInfoConvert.convertV4ItemData(itemData))
    }

}