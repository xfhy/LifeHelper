package com.xfhy.gank.adapter

import com.xfhy.gank.R
import com.xfhy.gank.data.bean.GankCommonBean
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import kotlinx.android.synthetic.main.gank_item_common_data.view.*

/**
 * Created by xfhy on 2018/3/20 15:00
 * Description :gank common adapter
 */
class GankCommonAdapter(gankDataList: MutableList<GankCommonBean.Result>?) :
        BaseQuickAdapter<GankCommonBean.Result, BaseViewHolder>(R.layout.gank_item_common_data, gankDataList) {
    override fun convert(holder: BaseViewHolder, item: GankCommonBean.Result?) {
        GlideUtils.loadCustomImage(holder.itemView, item?.gankBg, holder.itemView.mGankImgIv)
        holder.itemView.mGankTitleTv.text = item?.desc
        holder.itemView.mGankAuthor.text = item?.who
        holder.itemView.mGankDate.text = item?.publishedAt?.split("T")?.get(0)
    }
}