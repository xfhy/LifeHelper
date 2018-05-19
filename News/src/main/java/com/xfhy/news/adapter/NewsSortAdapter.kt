package com.xfhy.news.adapter

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.SnackbarUtil
import com.xfhy.news.R
import com.xfhy.news.data.bean.NewsSortBean
import kotlinx.android.synthetic.main.news_item_sort.view.*

/**
 * Created by xfhy on 2018/3/19 15:53
 * Description : 新闻排序管理adapter
 */
class NewsSortAdapter(newSortList: MutableList<NewsSortBean>?) :
        BaseQuickAdapter<NewsSortBean, BaseViewHolder>(R.layout.news_item_sort, newSortList) {
    override fun convert(holder: BaseViewHolder, item: NewsSortBean?) {
        holder.itemView.mNewsTypeTv.text = item?.newsType
        holder.itemView.mTypeCheckedSwitch.isChecked = item?.isChecked ?: true
        holder.itemView.mTypeCheckedSwitch.setOnCheckedChangeListener { switch, isChecked ->

            mData?.get(holder.adapterPosition)?.isChecked = isChecked

            //至少得留一个
            if (getRemainCount() == 0) {
                switch.isChecked = true
                mData?.get(holder.adapterPosition)?.isChecked = true
                SnackbarUtil.showBarShortTime(switch,"客官,留一个呗~")
            }
        }
    }

    /**
     * 判断是否还剩最后一个
     */
    private fun getRemainCount(): Int {
        var remainCount = 0
        mData?.forEach { it ->
            if (it.isChecked) {
                remainCount++
            }
        }
        return remainCount
    }


}