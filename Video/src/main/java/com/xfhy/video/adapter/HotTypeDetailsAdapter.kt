package com.xfhy.video.adapter

import android.widget.ImageView
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.*
import com.xfhy.video.R
import com.xfhy.video.data.bean.VideoItemV4Bean
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by xfhy on 2018年3月16日08:49:55
 * Description : 分类详情 adapter
 */
class HotTypeDetailsAdapter(sortDataList: MutableList<VideoItemV4Bean.Item>?) :
        BaseQuickAdapter<VideoItemV4Bean.Item, BaseViewHolder>(R.layout.video_item_home_video_card,
                sortDataList) {

    override fun convert(holder: BaseViewHolder, item: VideoItemV4Bean.Item?) {
        GlideUtils.loadCustomImage(holder.itemView, item?.data?.cover?.feed,
                (holder.getView(R.id.mVideoBackgroundIv) as ImageView))
        holder.setText(R.id.mVideoDurationTv,
                DateUtils.formatSecondToMinute(item?.data?.duration ?: 0))
        GlideUtils.loadCustomImageNoPlace(holder.itemView, item?.data?.author?.icon,
                (holder.getView(R.id.mAuthorIconIv) as CircleImageView))
        holder.setText(R.id.mVideoTitleTv, item?.data?.title)
        holder.setText(R.id.mVideoTitleDesc,
                "${item?.data?.author?.name} / #${item?.data?.category}")
        holder.getView(R.id.mShareIv)?.setOnClickListener {
            ShareUtil.shareText(holder.itemView.context,
                    "我刚刚使用LifeHelper看到一个碉堡的片子," +
                            "分享给大家:${item?.data?.webUrl?.raw}")
        }
    }
}