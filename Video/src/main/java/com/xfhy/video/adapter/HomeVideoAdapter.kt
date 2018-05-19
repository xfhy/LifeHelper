package com.xfhy.video.adapter

import android.content.Context
import android.widget.ImageView
import com.xfhy.library.adapter.BaseSectionQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DateUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.library.utils.ShareUtil
import com.xfhy.video.R
import com.xfhy.video.data.bean.HomeDataBean
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by xfhy on 2018/3/11 16:16
 * Description :  视频精选 adapter
 */
class HomeVideoAdapter(private val context: Context, itemData: MutableList<HomeDataBean.ItemList>?) :
        BaseSectionQuickAdapter<HomeDataBean.ItemList, BaseViewHolder>(R.layout.video_item_home_video_card,
                R.layout.video_header_item_home_text_card, itemData) {
    override fun convertHead(helper: BaseViewHolder, item: HomeDataBean.ItemList?) {
        helper.setText(R.id.mVideoHeaderTv, item?.data?.text)
    }

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
        holder.setVisible(R.id.mVideoLabelIv, true)
        holder.getView(R.id.mShareIv)?.setOnClickListener {
            ShareUtil.shareText(context,
                    "我刚刚使用LifeHelper看到一个碉堡的片子," +
                            "分享给大家:${item?.data?.content?.data?.webUrl?.raw}")
        }
    }
}