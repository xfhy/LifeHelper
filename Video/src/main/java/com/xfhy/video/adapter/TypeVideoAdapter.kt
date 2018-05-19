package com.xfhy.video.adapter

import android.widget.ImageView
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import com.xfhy.video.R
import com.xfhy.video.data.bean.VideoTypeBean

/**
 * Created by xfhy on 2018/3/11 21:22
 * Description : 视频分类
 */
class TypeVideoAdapter(types: MutableList<VideoTypeBean>?) :
        BaseQuickAdapter<VideoTypeBean, BaseViewHolder>(R.layout.video_item_type_main, types) {

    override fun convert(holder: BaseViewHolder, item: VideoTypeBean?) {
        holder.setText(R.id.mTypeTv, "#${item?.name}")
        GlideUtils.loadCustomImage(holder.itemView, item?.bgPicture, holder.getView(R.id.mTypeIv) as ImageView)
    }

}