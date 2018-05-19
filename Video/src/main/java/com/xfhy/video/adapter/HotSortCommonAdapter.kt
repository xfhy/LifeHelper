package com.xfhy.video.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.*
import com.xfhy.video.R
import com.xfhy.video.data.bean.VideoItemV4Bean
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by xfhy on 2018/3/12 13:06
 * Description : 排行榜 adapter
 */
class HotSortCommonAdapter(sortDataList: MutableList<VideoItemV4Bean.Item>?) :
        BaseQuickAdapter<VideoItemV4Bean.Item, BaseViewHolder>(R.layout.video_item_home_video_card,
                sortDataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder? {
        val baseViewHolder = super.onCreateViewHolder(parent, viewType)
        baseViewHolder?.itemView?.let {
            val layoutParams = it.layoutParams as RecyclerView.LayoutParams
            //item宽度设置窄一点(稍微比屏幕宽度窄一点,这样用户才知道那里是可以进行左右滑动的)
            layoutParams.width = DevicesUtils.devicesSize.widthPixels -
                    DensityUtil.dip2px(it.context, 20f)

            //设置paddingEnd 为5dp
            it.setPadding(0, 0, DensityUtil.dip2px(it.context, 5f), 0)

            it.layoutParams = layoutParams
        }
        return baseViewHolder
    }

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