package com.xfhy.weather.adapter

import android.annotation.SuppressLint
import com.xfhy.library.adapter.BaseQuickAdapter
import com.xfhy.library.adapter.BaseViewHolder
import com.xfhy.library.utils.GlideUtils
import com.xfhy.weather.R
import com.xfhy.weather.common.WeatherUtil
import com.xfhy.weather.data.bean.WeatherBean
import kotlinx.android.synthetic.main.weather_item_hour_weather.view.*

/**
 * Created by xfhy on 2018/3/22 20:06
 * Description : 每小时的天气 adapter
 */
class HourWeatherAdapter(dataList: MutableList<WeatherBean.Result.Hourly>?) :
        BaseQuickAdapter<WeatherBean.Result.Hourly, BaseViewHolder>
        (R.layout.weather_item_hour_weather, dataList) {

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: WeatherBean.Result.Hourly?) {
        //时间
        holder.itemView.mHourTime.text = item?.time
        GlideUtils.loadCustomImageNoPlace(holder.itemView,
                WeatherUtil.getImageUrlByWeatherId(item?.img ?: "0"),
                holder.itemView.iv_hour_weather_img)
        holder.itemView.tv_hour_temp.text = "${item?.temp}°C"
    }
}