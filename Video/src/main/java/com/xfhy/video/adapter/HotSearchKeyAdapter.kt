package com.xfhy.video.adapter

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.video.R
import kotlinx.android.synthetic.main.video_item_hot_search_key.view.*

/**
 * Created by xfhy on 2018/3/18 8:58
 * Description : 热搜关键词
 */
class HotSearchKeyAdapter(keyList: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.video_item_hot_search_key, keyList) {
    override fun convert(holder: BaseViewHolder, item: String?) {
        holder.itemView.mHotKeyTv.text = item
        holder.itemView.setOnClickListener { onClick.invoke(item) }
    }

    /**
     * item点击事件
     */
    var onClick: ((String?) -> Unit) = {}

}