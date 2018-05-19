package com.xfhy.zhihu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DensityUtil
import com.xfhy.library.utils.GlideUtils
import com.xfhy.zhihu.R
import com.xfhy.zhihu.data.bean.ColumnDailyBean

/**
 * @author xfhy
 * time create at 2018年3月10日16:44:02
 * description 知乎专栏列表adapter
 */
class ZHSectionAdapter(layoutResId: Int, data: MutableList<ColumnDailyBean.DataBean>?,
                       override var mContext: Context) :
        BaseQuickAdapter<ColumnDailyBean.DataBean, BaseViewHolder>(layoutResId, data) {

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

    override fun convert(holder: BaseViewHolder, item: ColumnDailyBean.DataBean?) {
        GlideUtils.loadCustomImage(mContext, item?.thumbnail, (holder.getView(R.id
                .iv_sections_thumbnail) as ImageView))
        holder.setText(R.id.tv_sections_name, item?.name)
        holder.setText(R.id.tv_sections_description, item?.description)
    }
}
