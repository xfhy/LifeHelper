package com.xfhy.joke.adapter

import android.view.View
import com.xfhy.joke.R
import com.xfhy.joke.data.bean.JokeBean
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import kotlinx.android.synthetic.main.joke_item_joke.view.*

/**
 * Created by xfhy on 2018/3/21 13:05
 * Description : 段子adapter
 */
class JokeAdapter(itemDataList: MutableList<JokeBean.Item>?) :
        BaseQuickAdapter<JokeBean.Item, BaseViewHolder>(R.layout.joke_item_joke, itemDataList) {
    override fun convert(holder: BaseViewHolder, item: JokeBean.Item?) {
        item?.u?.header?.let {
            if (it.size > 0) {
                GlideUtils.loadCustomImageNoPlace(holder.itemView, it[0], holder.itemView.mUserIcon)
            }
        }
        holder.itemView.mUserNameTv.text = item?.u?.name
        holder.itemView.iv_joke_is_hot.visibility = if (item?.topComments == null ||
                item.topComments?.size == 0) View.GONE else View.VISIBLE
        holder.itemView.tv_joke_content.text = item?.text?.replace("，", ",")
                ?.replace("（", "(")?.replace("）", ")")
        if (item?.topComments == null || item.topComments?.size == 0) {
            holder.itemView.rl_reply_layout.visibility = View.GONE
        } else {
            holder.itemView.rl_reply_layout.visibility = View.VISIBLE
            val comment = item.topComments?.get(0)
            comment?.u?.header?.let {
                if (it.size > 0) {
                    GlideUtils.loadCustomImageNoPlace(holder.itemView, it[0], holder.itemView
                            .mReplyUserIcon)
                }
            }
            holder.itemView.mReplyUserNameTv.text = comment?.u?.name
            holder.itemView.tv_reply_content.text = comment?.content
        }
    }
}