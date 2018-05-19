package com.xfhy.userfo.data.bean
import com.google.gson.annotations.SerializedName


/**
 * Created by feiyang on 2018/4/4 15:27
 * Description : 每日一句
 */

data class DayStateBean(
		@SerializedName("sid") var sid: String? = "",
		@SerializedName("tts") var tts: String? = "",
		@SerializedName("content") var content: String? = "",
		@SerializedName("note") var note: String? = "",
		@SerializedName("love") var love: String? = "",
		@SerializedName("translation") var translation: String? = "",
		@SerializedName("picture") var picture: String? = "",
		@SerializedName("picture2") var picture2: String? = "",
		@SerializedName("caption") var caption: String? = "",
		@SerializedName("dateline") var dateline: String? = "",
		@SerializedName("s_pv") var sPv: String? = "",
		@SerializedName("sp_pv") var spPv: String? = "",
		@SerializedName("fenxiang_img") var shareImg: String? = ""
)