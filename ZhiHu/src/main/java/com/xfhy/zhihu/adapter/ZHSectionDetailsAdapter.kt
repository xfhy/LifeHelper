package com.xfhy.zhihu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DensityUtil
import com.xfhy.library.utils.GlideUtils
import com.xfhy.zhihu.R
import com.xfhy.zhihu.data.bean.ColumnDailyDetailsBean

/**
 * @author xfhy
 * create at 2018年3月10日16:44:06
 * description：知乎专栏详情列表adapter
 */
class ZHSectionDetailsAdapter(layoutResId: Int,
                              data: MutableList<ColumnDailyDetailsBean.StoriesBean>?,
                              override var mContext: Context) :
        BaseQuickAdapter<ColumnDailyDetailsBean.StoriesBean, BaseViewHolder>(layoutResId, data) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        //第一项需要离顶部一段10dp的距离
        if (position == 0) {
            val layoutParams = holder.itemView
                    .layoutParams as RecyclerView.LayoutParams
            layoutParams.setMargins(layoutParams.marginStart,
                    DensityUtil.dip2px(mContext, 10f),
                    layoutParams.marginEnd,
                    DensityUtil.dip2px(mContext, 10f))
            holder.itemView.layoutParams = layoutParams
        }
    }

    override fun convert(holder: BaseViewHolder, item: ColumnDailyDetailsBean.StoriesBean?) {
        val itemImages = item?.images
        GlideUtils.loadCustomImage(mContext, itemImages?.get(0), (holder.getView(R
                .id.iv_news_image_latest) as ImageView))
        holder.setText(R.id.tv_news_title_latest, item?.title)
    }
}
