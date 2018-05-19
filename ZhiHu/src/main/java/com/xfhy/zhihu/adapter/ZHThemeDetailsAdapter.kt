package com.xfhy.zhihu.adapter

import android.content.Context
import android.widget.ImageView

import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import com.xfhy.zhihu.R
import com.xfhy.zhihu.data.bean.ThemeDailyDetailsBean

/**
 * @author xfhy
 * create at 2018年3月10日16:44:21
 * description：知乎主题详情页adapter
 */
class ZHThemeDetailsAdapter(layoutResId: Int, data: MutableList<ThemeDailyDetailsBean.Story>?,
                            override var mContext: Context) :
        BaseQuickAdapter<ThemeDailyDetailsBean.Story, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: ThemeDailyDetailsBean.Story?) {

        //日报图片  可能没有
        val images = item?.images
        if (images == null || images.size == 0) {
            holder.setVisible(R.id.iv_daily_image, false)
        } else {
            holder.setVisible(R.id.iv_daily_image, true)
            GlideUtils.loadCustomImage(mContext, images[0], (holder.getView(R.id
                    .iv_daily_image) as ImageView))
        }

        //日报标题
        holder.setText(R.id.tv_daily_title, item?.title)
    }
}
