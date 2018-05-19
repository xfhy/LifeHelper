package com.xfhy.news.adapter

import android.view.View
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import com.xfhy.news.R
import com.xfhy.news.data.bean.NewsBean
import kotlinx.android.synthetic.main.news_item_news.view.*

/**
 * Created by xfhy on 2018/3/18 20:14
 * Description : 新闻列表adapter
 */
class NewsItemAdapter(newsList: MutableList<NewsBean.Result.Item>?) : BaseQuickAdapter<NewsBean.Result.Item,
        BaseViewHolder>(R.layout.news_item_news, newsList) {
    override fun convert(holder: BaseViewHolder, item: NewsBean.Result.Item?) {
        if (item?.pic.isNullOrEmpty()) {
            holder.itemView.mNewsPicIv.visibility = View.GONE
        } else {
            GlideUtils.loadCustomImage(holder.itemView, item?.pic, holder.itemView.mNewsPicIv)
        }
        holder.itemView.mNewsTitleTv.text = item?.title
        holder.itemView.mNewsSrcTv.text = item?.src
        val splitTime = item?.time?.split(" ")
        holder.itemView.mNewsTimeTv.text = splitTime?.get(0)

    }
}