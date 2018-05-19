package com.xfhy.weather.adapter

import android.annotation.SuppressLint
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.DateUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.weather.R
import com.xfhy.weather.common.WeatherUtil
import com.xfhy.weather.data.bean.WeatherBean
import kotlinx.android.synthetic.main.weather_item_daily_weather.view.*

/**
 * Created by xfhy on 2018/3/22 20:30
 * Description : 后几天 天气 adapter
 */
class DailyWeatherAdapter(dataList: MutableList<WeatherBean.Result.Daily>?) :
        BaseQuickAdapter<WeatherBean.Result.Daily, BaseViewHolder>
        (R.layout.weather_item_daily_weather, dataList) {
    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: WeatherBean.Result.Daily?) {
        val date = DateUtils.getDateByText(item?.date ?: "", "yyyy-MM-dd")
        holder.itemView.tv_weather_date.text = if (holder.adapterPosition == 0) {
            "${DateUtils.getDateFormatText(date, "MM月dd日")}今天"
        } else {
            DateUtils.getDateFormatText(date, "MM月dd日EEEE")
        }
        GlideUtils.loadCustomImageNoPlace(holder.itemView,
                WeatherUtil.getImageUrlByWeatherId(item?.day?.img ?: "0"), holder.itemView.iv_weather_img)
        holder.itemView.tv_low_high_temp.text = "${item?.day?.temphigh}°C / ${item?.night?.templow}°C"
    }
}