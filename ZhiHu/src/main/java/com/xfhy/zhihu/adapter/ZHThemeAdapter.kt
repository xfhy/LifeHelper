package com.xfhy.zhihu.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DevicesUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.zhihu.R
import com.xfhy.zhihu.data.bean.TopicDailyListBean

/**
 * @author xfhy
 * create at 2018年3月10日16:44:17
 * description：知乎主题列表adapter
 */
class ZHThemeAdapter(layoutResId: Int, data: MutableList<TopicDailyListBean.OtherBean>?,
                     private val context: Context) : BaseQuickAdapter<TopicDailyListBean.OtherBean, BaseViewHolder>(layoutResId, data) {
    /**
     * 屏幕的宽度/2   把item的宽度设置为屏幕宽度的一半
     */
    private val mWidthPixels: Int

    init {
        val devicesSize = DevicesUtils.devicesSize
        mWidthPixels = devicesSize.widthPixels / 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        val baseViewHolder = super.onCreateViewHolder(parent, viewType)
        //设置item的宽高相等 设置为屏幕宽度的一半
        val view = baseViewHolder?.getView(R.id.fl_theme_root_view)
        if (view is FrameLayout) {
            val rootView = view as? FrameLayout
            val rootViewLayoutParams = rootView?.layoutParams
            rootViewLayoutParams?.width = mWidthPixels
            rootViewLayoutParams?.height = rootViewLayoutParams?.width
            rootView?.layoutParams = rootViewLayoutParams
        }

        return baseViewHolder
    }

    override fun convert(holder: BaseViewHolder, item: TopicDailyListBean.OtherBean?) {
        GlideUtils.loadCustomImage(context, item?.thumbnail, (holder.getView(R.id
                .iv_theme_item) as ImageView))
        holder.setText(R.id.tv_theme_item, item?.name)
    }
}
