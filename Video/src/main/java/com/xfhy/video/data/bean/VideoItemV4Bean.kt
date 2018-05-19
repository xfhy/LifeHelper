package com.xfhy.video.data.bean

import com.google.gson.annotations.SerializedName
import com.xfhy.library.adapter.entity.SectionEntity
import java.io.Serializable


/**
 * Created by xfhy on 2018/3/12 13:27
 * Description : Video 数据类 bean
 * 对应API 4
 */

data class VideoItemV4Bean(
        @SerializedName("itemList") var itemList: MutableList<Item>? = mutableListOf(),
        @SerializedName("count") var count: Int? = 0,
        @SerializedName("total") var total: Int? = 0,
        @SerializedName("adExist") var adExist: Boolean? = false,
        @SerializedName("nextPageUrl") var nextPageUrl: String? = ""
) : Serializable {
    data class Item(
            @SerializedName("type") var type: String? = "",
            @SerializedName("data") var data: Data? = Data(),
            @SerializedName("id") var id: Int? = 0,
            @SerializedName("adIndex") var adIndex: Int? = 0
    ) : SectionEntity<Item>(), Serializable {

        data class Data(
                @SerializedName("dataType") var dataType: String? = "",
                @SerializedName("id") var id: Int? = 0,
                @SerializedName("title") var title: String? = "",
                @SerializedName("text") var text: String? = "",
                @SerializedName("description") var description: String? = "",
                @SerializedName("library") var library: String? = "",
                @SerializedName("tags") var tags: MutableList<Tag>? = mutableListOf(),
                @SerializedName("consumption") var consumption: Consumption? = Consumption(),
                @SerializedName("resourceType") var resourceType: String? = "",
                @SerializedName("slogan") var slogan: String? = "",
                @SerializedName("provider") var provider: Provider? = Provider(),
                @SerializedName("category") var category: String? = "",
                @SerializedName("author") var author: Author? = Author(),
                @SerializedName("cover") var cover: Cover? = Cover(),
                @SerializedName("playUrl") var playUrl: String? = "",
                @SerializedName("thumbPlayUrl") var thumbPlayUrl: String? = "",
                @SerializedName("duration") var duration: Int? = 0,
                @SerializedName("webUrl") var webUrl: WebUrl? = WebUrl(),
                @SerializedName("releaseTime") var releaseTime: Long? = 0,
                @SerializedName("playInfo") var playInfo: MutableList<PlayInfo>? = mutableListOf(),
                @SerializedName("type") var type: String? = "",
                @SerializedName("titlePgc") var titlePgc: String? = "",
                @SerializedName("descriptionPgc") var descriptionPgc: String? = "",
                @SerializedName("remark") var remark: String? = "",
                @SerializedName("idx") var idx: Int? = 0,
                @SerializedName("date") var date: Long? = 0,
                @SerializedName("labelList") var labelList: MutableList<Any>? = mutableListOf(),
                @SerializedName("descriptionEditor") var descriptionEditor: String? = "",
                @SerializedName("collected") var collected: Boolean? = false,
                @SerializedName("played") var played: Boolean? = false,
                @SerializedName("subtitles") var subtitles: MutableList<Any>? = mutableListOf()
        ) : Serializable {
            data class Consumption(
                    @SerializedName("collectionCount") var collectionCount: Int? = 0,
                    @SerializedName("shareCount") var shareCount: Int? = 0,
                    @SerializedName("replyCount") var replyCount: Int? = 0
            ) : Serializable

            data class Provider(
                    @SerializedName("name") var name: String? = "",
                    @SerializedName("alias") var alias: String? = "",
                    @SerializedName("icon") var icon: String? = ""
            ) : Serializable

            data class PlayInfo(
                    @SerializedName("height") var height: Int? = 0,
                    @SerializedName("width") var width: Int? = 0,
                    @SerializedName("urlList") var urlList: MutableList<Url>? = mutableListOf(),
                    @SerializedName("name") var name: String? = "",
                    @SerializedName("type") var type: String? = "",
                    @SerializedName("url") var url: String? = ""
            ) : Serializable {
                data class Url(
                        @SerializedName("name") var name: String? = "",
                        @SerializedName("url") var url: String? = "",
                        @SerializedName("size") var size: Int? = 0
                ) : Serializable
            }

            data class WebUrl(
                    @SerializedName("raw") var raw: String? = "",
                    @SerializedName("forWeibo") var forWeibo: String? = ""
            ) : Serializable

            data class Tag(
                    @SerializedName("id") var id: Int? = 0,
                    @SerializedName("name") var name: String? = "",
                    @SerializedName("actionUrl") var actionUrl: String? = "",
                    @SerializedName("bgPicture") var bgPicture: String? = "",
                    @SerializedName("headerImage") var headerImage: String? = "",
                    @SerializedName("tagRecType") var tagRecType: String? = ""
            ) : Serializable

            data class Cover(
                    @SerializedName("feed") var feed: String? = "",
                    @SerializedName("detail") var detail: String? = "",
                    @SerializedName("blurred") var blurred: String? = "",
                    @SerializedName("homepage") var homepage: String? = ""
            ) : Serializable

            data class Author(
                    @SerializedName("id") var id: Int? = 0,
                    @SerializedName("icon") var icon: String? = "",
                    @SerializedName("name") var name: String? = "",
                    @SerializedName("description") var description: String? = "",
                    @SerializedName("link") var link: String? = "",
                    @SerializedName("latestReleaseTime") var latestReleaseTime: Long? = 0,
                    @SerializedName("videoNum") var videoNum: Int? = 0,
                    @SerializedName("follow") var follow: Follow? = Follow(),
                    @SerializedName("shield") var shield: Shield? = Shield(),
                    @SerializedName("approvedNotReadyVideoCount") var approvedNotReadyVideoCount: Int? = 0,
                    @SerializedName("ifPgc") var ifPgc: Boolean? = false
            ) : Serializable {
                data class Shield(
                        @SerializedName("itemType") var itemType: String? = "",
                        @SerializedName("itemId") var itemId: Int? = 0,
                        @SerializedName("shielded") var shielded: Boolean? = false
                ) : Serializable

                data class Follow(
                        @SerializedName("itemType") var itemType: String? = "",
                        @SerializedName("itemId") var itemId: Int? = 0,
                        @SerializedName("followed") var followed: Boolean? = false
                ) : Serializable
            }
        }
    }
}