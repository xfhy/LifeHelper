package com.xfhy.video.adapter

import android.widget.ImageView
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DateUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.library.utils.ShareUtil
import com.xfhy.video.R
import com.xfhy.video.data.bean.HomeDataBean
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by xfhy on 2018/3/18 9:02
 * Description : 搜索出来的视频列表adapter
 */
class SearchVideoAdapter(videoList: MutableList<HomeDataBean.ItemList>?) :
        BaseQuickAdapter<HomeDataBean.ItemList, BaseViewHolder>(R.layout.video_item_home_video_card, videoList) {
    override fun convert(holder: BaseViewHolder, item: HomeDataBean.ItemList?) {
        GlideUtils.loadCustomImage(holder.itemView, item?.data?.content?.data?.cover?.feed,
                (holder.getView(R.id.mVideoBackgroundIv) as ImageView))
        holder.setText(R.id.mVideoDurationTv,
                DateUtils.formatSecondToMinute(item?.data?.content?.data?.duration ?: 0))
        GlideUtils.loadCustomImageNoPlace(holder.itemView, item?.data?.header?.icon,
                (holder.getView(R.id.mAuthorIconIv) as CircleImageView))
        holder.setText(R.id.mVideoTitleTv, item?.data?.content?.data?.title)
        holder.setText(R.id.mVideoTitleDesc,
                "${item?.data?.header?.title} / #${item?.data?.content?.data?.category}")
        holder.getView(R.id.mShareIv)?.setOnClickListener {
            ShareUtil.shareText(holder.itemView.context,
                    "我刚刚使用LifeHelper看到一个碉堡的片子," +
                            "分享给大家:${item?.data?.content?.data?.webUrl?.raw}")
        }
    }
}