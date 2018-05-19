package com.xfhy.zhihu.adapter

import android.content.Context
import android.widget.ImageView

import com.xfhy.library.adapter.BaseSectionQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import com.xfhy.zhihu.R
import com.xfhy.zhihu.data.bean.LatestDailyListBean.StoriesBean

/**
 * @author xfhy
 * @time create at 2018年3月10日16:43:56
 * @description 知乎最新日报列表adapter
 */
class ZHLatestDailyAdapter(private val context: Context, data: MutableList<StoriesBean>?)
    : BaseSectionQuickAdapter<StoriesBean, BaseViewHolder>(R.layout.item_zh_common_daily_list,
        R.layout.header_zh_latest_daily, data) {

    private var listener: HeaderChangeListener? = null

    override fun convertHead(helper: BaseViewHolder, item: StoriesBean?) {
        helper.setText(R.id.tv_latest_header_title, item?.header)
        listener?.onHeaderChanged(item?.header)
    }

    override fun convert(holder: BaseViewHolder, item: StoriesBean?) {
        holder.setText(R.id.tv_news_title_latest, item?.title)
        val imageUrl = item?.images
        if (imageUrl != null && imageUrl.isNotEmpty()) {
            GlideUtils.loadCustomImage(context, imageUrl[0], (holder.getView(R.id
                    .iv_news_image_latest) as ImageView))
        }

    }

    /**
     * 监听header变化
     * @param listener HeaderChangeListener
     */
    fun setOnHeaderChangeListener(listener: HeaderChangeListener) {
        this.listener = listener
    }

    interface HeaderChangeListener {
        /**
         * 标题改变
         *
         * @param title 新标题
         */
        fun onHeaderChanged(title: String?)
    }

}
