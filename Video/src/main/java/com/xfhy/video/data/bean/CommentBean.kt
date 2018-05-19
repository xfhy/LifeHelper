package com.xfhy.video.data.bean

import java.io.Serializable
import com.google.gson.annotations.SerializedName
import com.xfhy.library.adapter.entity.SectionEntity


/**
 * Created by xfhy on 2018/3/16 21:40
 * Description : 评论
 */

data class CommentBean(
        @SerializedName("itemList") var itemList: MutableList<ItemData>? = mutableListOf(),
        @SerializedName("count") var count: Int? = 0,
        @SerializedName("total") var total: Int? = 0,
        @SerializedName("nextPageUrl") var nextPageUrl: String? = "",
        @SerializedName("adExist") var adExist: Boolean? = false
) : Serializable {
    data class ItemData(var type: String? = "", var data: Data? = null, var tag: String? = "",
                        var id: Int? = 0) :
            Serializable, SectionEntity<ItemData>() {

        data class Data(var dataType: String,
                        var text: String,
                        var videoTitle: String,
                        var id: Long,
                        var title: String,
                        var slogan: String?,
                        var description: String,
                        var actionUrl: String,
                        var provider: Provider,
                        var category: String,
                        var parentReply: ParentReply,
                        var author: Author,
                        var cover: Cover,
                        var likeCount: Int = 0,
                        var playUrl: String,
                        var thumbPlayUrl: String,
                        var duration: Long,
                        var message: String,
                        var createTime: Long,
                        var webUrl: WebUrl,
                        var library: String,
                        var user: User,
                        var playInfo: ArrayList<PlayInfo>,
                        var consumption: Consumption,
                        var campaign: Any,
                        var waterMarks: Any,
                        var adTrack: Any,
                        var tags: ArrayList<Tag>,
                        var type: String,
                        var titlePgc: Any,
                        var descriptionPgc: Any,
                        var remark: String,
                        var idx: Int,
                        var shareAdTrack: Any,
                        var favoriteAdTrack: Any,
                        var webAdTrack: Any,
                        var date: Long,
                        var promotion: Any,
                        var label: Any,
                        var labelList: Any,
                        var descriptionEditor: String,
                        var collected: Boolean,
                        var played: Boolean,
                        var subtitles: Any,
                        var lastViewTime: Any,
                        var playlists: Any
        ) : Serializable {
            data class Tag(var id: Int, var name: String, var actionUrl: String, var adTrack: Any) : Serializable
            data class Author(var icon: String, var name: String, var description: String) : Serializable

            data class Provider(var name: String, var alias: String, var icon: String) : Serializable

            data class Cover(var feed: String, var detail: String,
                             var blurred: String, var sharing: String, var homepage: String) : Serializable

            data class WebUrl(var raw: String, var forWeibo: String) : Serializable
            data class PlayInfo(var name: String, var url: String, var type: String, var urlList: ArrayList<Url>) : Serializable
            data class Consumption(var collectionCount: Int, var shareCount: Int, var replyCount: Int) : Serializable
            data class User(var uid: Long, var nickname: String, var avatar: String, var userType: String, var ifPgc: Boolean) : Serializable
            data class ParentReply(var user: User, var message: String) : Serializable
            data class Url(var size: Long) : Serializable

        }
    }
}
