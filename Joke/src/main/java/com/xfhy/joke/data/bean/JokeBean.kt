package com.xfhy.joke.data.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal


/**
 * Created by xfhy on 2018/3/21 10:16
 * Description : 段子 bean
 */

data class JokeBean(
		@SerializedName("info") var info: Info? = Info(),
		@SerializedName("list") var list: MutableList<Item>? = mutableListOf()
) {
	data class Info(
			@SerializedName("count") var count: Int? = 0,
			@SerializedName("np") var np: Int? = 0
	)

	data class Item(
			@SerializedName("status") var status: Int? = 0,
			@SerializedName("comment") var comment: String? = "",
			@SerializedName("top_comments") var topComments: MutableList<TopComment>? = mutableListOf(),
			@SerializedName("tags") var tags: MutableList<Tag>? = mutableListOf(),
			@SerializedName("bookmark") var bookmark: String? = "",
			@SerializedName("text") var text: String? = "",
			@SerializedName("up") var up: String? = "",
			@SerializedName("share_url") var shareUrl: String? = "",
			@SerializedName("down") var down: Int? = 0,
			@SerializedName("forward") var forward: Int? = 0,
			@SerializedName("u") var u: U? = U(),
			@SerializedName("passtime") var passtime: String? = "",
			@SerializedName("type") var type: String? = "",
			@SerializedName("id") var id: String? = ""
	) {
		data class U(
				@SerializedName("header") var header: MutableList<String>? = mutableListOf(),
				@SerializedName("uid") var uid: String? = "",
				@SerializedName("is_vip") var isVip: Boolean? = false,
				@SerializedName("is_v") var isV: Boolean? = false,
				@SerializedName("room_url") var roomUrl: String? = "",
				@SerializedName("room_name") var roomName: String? = "",
				@SerializedName("room_role") var roomRole: String? = "",
				@SerializedName("room_icon") var roomIcon: String? = "",
				@SerializedName("name") var name: String? = ""
		)
		data class Tag(
				@SerializedName("post_number") var postNumber: Int? = 0,
				@SerializedName("image_list") var imageList: String? = "",
				@SerializedName("forum_sort") var forumSort: Int? = 0,
				@SerializedName("forum_status") var forumStatus: Int? = 0,
				@SerializedName("id") var id: Int? = 0,
				@SerializedName("info") var info: String? = "",
				@SerializedName("name") var name: String? = "",
				@SerializedName("colum_set") var columSet: Int? = 0,
				@SerializedName("tail") var tail: String? = "",
				@SerializedName("sub_number") var subNumber: Int? = 0,
				@SerializedName("display_level") var displayLevel: Int? = 0
		)
		data class TopComment(
				@SerializedName("voicetime") var voicetime: Int? = 0,
				@SerializedName("status") var status: Int? = 0,
				@SerializedName("hate_count") var hateCount: Int? = 0,
				@SerializedName("cmt_type") var cmtType: String? = "",
				@SerializedName("precid") var precid: Int? = 0,
				@SerializedName("content") var content: String? = "",
				@SerializedName("like_count") var likeCount: Int? = 0,
				@SerializedName("u") var u: U? = U(),
				@SerializedName("preuid") var preuid: Int? = 0,
				@SerializedName("passtime") var passtime: String? = "",
				@SerializedName("voiceuri") var voiceuri: String? = "",
				@SerializedName("id") var id: Int? = 0
		) {
			data class U(
                    @SerializedName("header") var header: MutableList<String>? = mutableListOf(),
                    @SerializedName("uid") var uid: String? = "",
                    @SerializedName("is_vip") var isVip: Boolean? = false,
                    @SerializedName("room_url") var roomUrl: String? = "",
                    @SerializedName("sex") var sex: String? = "",
                    @SerializedName("room_name") var roomName: String? = "",
                    @SerializedName("room_role") var roomRole: String? = "",
                    @SerializedName("room_icon") var roomIcon: String? = "",
                    @SerializedName("name") var name: String? = ""
			)
		}
	}
}