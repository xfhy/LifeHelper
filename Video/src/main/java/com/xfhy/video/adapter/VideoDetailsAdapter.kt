package com.xfhy.video.adapter

import android.widget.ImageView
import com.xfhy.library.adapter.BaseSectionQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DateUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.video.R
import com.xfhy.video.data.bean.VideoItemV4Bean

/**
 * Created by xfhy on 2018/3/16 19:09
 * Description : 视频详情列表adapter
 */
class VideoDetailsAdapter(itemData: MutableList<VideoItemV4Bean.Item>?) :
        BaseSectionQuickAdapter<VideoItemV4Bean.Item, BaseViewHolder>(R.layout.video_item_video_details,
                R.layout.video_item_video_details_group, itemData) {

    //((VideoItemV4Bean.Item?) -> Unit)?类型的属性
    var onItemClick: ((VideoItemV4Bean.Item?) -> Unit)? = {}

    override fun convertHead(helper: BaseViewHolder, item: VideoItemV4Bean.Item?) {
        helper.setText(R.id.tv_video_item_group_title, item?.data?.text)
    }

    override fun convert(holder: BaseViewHolder, item: VideoItemV4Bean.Item?) {
        GlideUtils.loadCustomImage(holder.itemView, item?.data?.cover?.feed,
                holder.getView(R.id.iv_video_cover) as ImageView)
        holder.setText(R.id.tv_video_title, item?.data?.title)
        holder.setText(R.id.tv_video_duration,
                "#${item?.data?.category} / ${DateUtils.formatSecondToMinute(item?.data?.duration ?: 0)}")

        holder.itemView.setOnClickListener { onItemClick?.invoke(item) }
    }
}