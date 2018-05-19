package com.xfhy.zhihu.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xfhy.library.adapter.BaseQuickAdapter

import com.xfhy.library.adapter.BaseSectionQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DateUtils
import com.xfhy.library.utils.GlideUtils
import de.hdodenhof.circleimageview.CircleImageView
import com.xfhy.zhihu.R
import com.xfhy.zhihu.data.bean.DailyCommentBean

import java.util.Date

/**
 * author xfhy
 * time create at 2018年3月10日16:43:46
 * description 知乎评论页RecyclerView adapter
 */
class ZHCommentAdapter(private var context: Context, data: MutableList<DailyCommentBean.CommentsBean>) :
        BaseQuickAdapter<DailyCommentBean.CommentsBean, BaseViewHolder>(R.layout.item_zh_comment, data) {

    override fun convert(holder: BaseViewHolder, item: DailyCommentBean.CommentsBean?) {
        //头像
        if (item?.avatar == null || "" == item.avatar) {
            Glide.with(context).load(R.drawable.default_author)
                    .into((holder.getView(R.id.civ_comment_avatar) as CircleImageView))
        } else {
            GlideUtils.loadCustomImageNoPlace(holder.itemView, item.avatar,
                    (holder.getView(R.id.civ_comment_avatar) as CircleImageView))
        }
        //昵称
        holder.setText(R.id.tv_author, item?.author)
        //内容
        holder.setText(R.id.tv_content, item?.content)
        //时间
        holder.setText(R.id.tv_comment_time, DateUtils.getDateFormatText(Date((item?.time ?: 0) * 1000),
                "MM-dd HH:mm"))

        val replyTo = item?.replyTo

        //有些content是null,是被作者删除了的
        if (replyTo?.content == null) {
            return
        }

        holder.setVisible(R.id.tv_comment_reply, true)

        //改变字体颜色   将作者突出显示
        //先构造SpannableString
        val spanString = SpannableString("@" + item.replyTo?.author +
                ":" + item.replyTo?.content)
        //再构造一个改变字体颜色的Span
        var authorSpan: ForegroundColorSpan? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            authorSpan = ForegroundColorSpan(context.getColor(R.color.snackbar_blue))
        } else {
            authorSpan = ForegroundColorSpan(context.resources.getColor(R.color.snackbar_blue))
        }
        //将这个Span应用于指定范围的字体
        spanString.setSpan(authorSpan, 0, item.replyTo?.author?.length ?: 0+2, Spannable
                .SPAN_INCLUSIVE_INCLUSIVE)

        //回复内容
        val replyView = holder.getView(R.id.tv_comment_reply) as TextView?
        replyView?.text = spanString


        //必须这样写才能计算出TextView的行数,不然得出的结果是0
        replyView?.post {
            val replyLineCount = replyView.lineCount//获取当前回复内容所占的行数
            if (replyLineCount > 0) {
                holder.setVisible(R.id.tv_comment_expand, true)
                holder.setText(R.id.tv_comment_expand, "展开")
            }

            holder.setOnClickListener(R.id.tv_comment_expand, View.OnClickListener {
                /*
                            *   有
                            *       1,少   不用管
                            *       2,多
                            *           a,已展开
                            *           b,未展开
                            * */
                if (replyTo.expandState == NOT_EXPANDED) {
                    replyView.maxLines = Integer.MAX_VALUE
                    holder.setText(R.id.tv_comment_expand, "收缩")
                    replyTo.expandState = EXPANDED
                } else if (replyTo.expandState == EXPANDED) {
                    replyView.maxLines = MAX_LINES
                    holder.setText(R.id.tv_comment_expand, "展开")
                    replyTo.expandState = NOT_EXPANDED
                }
            })
        }


    }

    companion object {

        /**
         * 回复内容的最大行数(未展开时)
         */
        private val MAX_LINES = 2

        private val NOT_EXPANDED = 0
        private val EXPANDED = 1
    }
}
