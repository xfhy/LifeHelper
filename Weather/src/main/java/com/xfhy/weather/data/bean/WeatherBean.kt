package com.xfhy.weather.data.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by xfhy on 2018/3/22 14:13
 * Description : 天气bean
 */

data class WeatherBean(
		@SerializedName("status") var status: String? = "",
		@SerializedName("msg") var msg: String? = "",
		@SerializedName("result") var result: Result? = Result()
) {
	data class Result(
            @SerializedName("city") var city: String? = "",
            @SerializedName("cityid") var cityid: String? = "",
            @SerializedName("citycode") var citycode: String? = "",
            @SerializedName("date") var date: String? = "",
            @SerializedName("week") var week: String? = "",
            @SerializedName("weather") var weather: String? = "",
            @SerializedName("temp") var temp: String? = "",
            @SerializedName("temphigh") var temphigh: String? = "",
            @SerializedName("templow") var templow: String? = "",
            @SerializedName("img") var img: String? = "",
            @SerializedName("humidity") var humidity: String? = "",
            @SerializedName("pressure") var pressure: String? = "",
            @SerializedName("windspeed") var windspeed: String? = "",
            @SerializedName("winddirect") var winddirect: String? = "",
            @SerializedName("windpower") var windpower: String? = "",
            @SerializedName("updatetime") var updatetime: String? = "",
            @SerializedName("index") var index: MutableList<Index>? = mutableListOf(),
            @SerializedName("aqi") var aqi: Aqi? = Aqi(),
            @SerializedName("daily") var daily: MutableList<Daily>? = mutableListOf(),
            @SerializedName("hourly") var hourly: MutableList<Hourly>? = mutableListOf()
	) {
		data class Aqi(
				@SerializedName("so2") var so2: String? = "",
				@SerializedName("so224") var so224: String? = "",
				@SerializedName("no2") var no2: String? = "",
				@SerializedName("no224") var no224: String? = "",
				@SerializedName("co") var co: String? = "",
				@SerializedName("co24") var co24: String? = "",
				@SerializedName("o3") var o3: String? = "",
				@SerializedName("o38") var o38: String? = "",
				@SerializedName("o324") var o324: String? = "",
				@SerializedName("pm10") var pm10: String? = "",
				@SerializedName("pm1024") var pm1024: String? = "",
				@SerializedName("pm2_5") var pm25: String? = "",
				@SerializedName("pm2_524") var pm2524: String? = "",
				@SerializedName("iso2") var iso2: String? = "",
				@SerializedName("ino2") var ino2: String? = "",
				@SerializedName("ico") var ico: String? = "",
				@SerializedName("io3") var io3: String? = "",
				@SerializedName("io38") var io38: String? = "",
				@SerializedName("ipm10") var ipm10: String? = "",
				@SerializedName("ipm2_5") var ipm25: String? = "",
				@SerializedName("aqi") var aqi: String? = "",
				@SerializedName("primarypollutant") var primarypollutant: String? = "",
				@SerializedName("quality") var quality: String? = "",
				@SerializedName("timepoint") var timepoint: String? = "",
				@SerializedName("aqiinfo") var aqiinfo: Aqiinfo? = Aqiinfo()
		) {
			data class Aqiinfo(
					@SerializedName("level") var level: String? = "",
					@SerializedName("color") var color: String? = "",
					@SerializedName("affect") var affect: String? = "",
					@SerializedName("measure") var measure: String? = ""
			)
		}
		data class Daily(
				@SerializedName("date") var date: String? = "",
				@SerializedName("week") var week: String? = "",
				@SerializedName("sunrise") var sunrise: String? = "",
				@SerializedName("sunset") var sunset: String? = "",
				@SerializedName("night") var night: Night? = Night(),
				@SerializedName("day") var day: Day? = Day()
		) {
			data class Day(
					@SerializedName("weather") var weather: String? = "",
					@SerializedName("temphigh") var temphigh: String? = "",
					@SerializedName("img") var img: String? = "",
					@SerializedName("winddirect") var winddirect: String? = "",
					@SerializedName("windpower") var windpower: String? = ""
			)
			data class Night(
					@SerializedName("weather") var weather: String? = "",
					@SerializedName("templow") var templow: String? = "",
					@SerializedName("img") var img: String? = "",
					@SerializedName("winddirect") var winddirect: String? = "",
					@SerializedName("windpower") var windpower: String? = ""
			)
		}
		data class Hourly(
				@SerializedName("time") var time: String? = "",
				@SerializedName("weather") var weather: String? = "",
				@SerializedName("temp") var temp: String? = "",
				@SerializedName("img") var img: String? = ""
		)
		data class Index(
				@SerializedName("iname") var iname: String? = "",
				@SerializedName("ivalue") var ivalue: String? = "",
				@SerializedName("detail") var detail: String? = ""
		)
	}
}