package com.xfhy.video.adapter

import android.view.View
import com.xfhy.library.adapter.BaseSectionQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DateUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.video.R
import com.xfhy.video.data.bean.CommentBean
import kotlinx.android.synthetic.main.video_item_comment.view.*
import kotlinx.android.synthetic.main.video_item_video_details_group.view.*

/**
 * Created by xfhy on 2018/3/17 8:24
 * Description : 视频评论adapter
 */
class VideoCommentAdapter(val itemList: MutableList<CommentBean.ItemData>?) :
        BaseSectionQuickAdapter<CommentBean.ItemData, BaseViewHolder>(R.layout.video_item_comment,
                R.layout.video_item_video_details_group, itemList) {
    override fun convertHead(helper: BaseViewHolder, item: CommentBean.ItemData?) {
        helper.itemView.tv_video_item_group_title.text = item?.data?.text
    }

    override fun convert(holder: BaseViewHolder, item: CommentBean.ItemData?) {
        GlideUtils.loadCustomImageNoPlace(holder.itemView, item?.data?.user?.avatar,
                holder.itemView.mReplyUserIconCIV)
        holder.itemView.tv_user_name.text = item?.data?.user?.nickname
        holder.itemView.tv_like_count.text = item?.data?.likeCount.toString()
        holder.itemView.tv_comment_message.text = item?.data?.message
        holder.itemView.tv_comment_date.text = DateUtils.getDateFormatText(item?.data?.createTime ?: 0,
                "yyyy/MM/dd")

        //下面是回复别人评论时需要展示的内容
        if (item?.data?.parentReply == null) {
            holder.itemView.tv_reply_user_name_info.visibility = View.GONE
            holder.itemView.rl_parent_reply_layout.visibility = View.GONE
        } else {
            holder.itemView.tv_reply_user_name_info.visibility = View.VISIBLE
            holder.itemView.tv_reply_user_name_info.text = "回复: ${item?.data?.parentReply?.user?.nickname}"
            holder.itemView.rl_parent_reply_layout.visibility = View.VISIBLE
            GlideUtils.loadCustomImageNoPlace(holder.itemView, item.data?.parentReply?.user?.avatar,
                    holder.itemView.iv_reply_user_icon)
            holder.itemView.tv_reply_user_name.text = item.data?.parentReply?.user?.nickname
            holder.itemView.tv_reply_user_message.text = item.data?.parentReply?.message
        }
    }
}