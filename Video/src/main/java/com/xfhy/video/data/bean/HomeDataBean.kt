package com.xfhy.video.data.bean

import com.google.gson.annotations.SerializedName
import com.xfhy.library.adapter.entity.SectionEntity
import java.io.Serializable

/**
 * Created by xfhy on 2018/3/11 8:31
 * Description : 首页数据bean
 *
 * 对应API 5
 *
 */
class HomeDataBean : Serializable {

    /**
     * 卡片个数
     */
    var count: Int = 0
    var total: Int = 0

    /**
     * 加载更多时需要用到
     */
    @SerializedName("nextPageUrl")
    var nextPageUrl: String? = null
    var adExist: Boolean = false
    @SerializedName("itemList")
    var itemList: MutableList<ItemList>? = null

    class ItemList : SectionEntity<ItemList>, Serializable {

        /**
         * textCard : 文字
         * followCard : 卡片
         */
        @SerializedName("type")
        var type: String? = null
        @SerializedName("data")
        var data: DataBean? = null
        @SerializedName("tag")
        var tag: Any? = null
        @SerializedName("id")
        var id: Int = 0
        @SerializedName("adIndex")
        var adIndex: Int = 0

        constructor() : super()
        constructor(isHeader: Boolean) : super(isHeader)
        constructor(t: ItemList) : super(t)
        constructor(isHeader: Boolean, header: String) : super(isHeader, header)


        data class DataBean(
                /**
                 * TextCard
                 * FollowCard
                 */
                @SerializedName("dataType") var dataType: String? = "",
                /**
                 * 文字卡片的标题
                 */
                @SerializedName("text") var text: String? = "",
                @SerializedName("type") var type: String? = "",
                @SerializedName("id") var id: Int? = 0,
                @SerializedName("subTitle") var subTitle: Any? = "",
                @SerializedName("actionUrl") var actionUrl: Any? = "",
                @SerializedName("adTrack") var adTrack: Any? = "",
                @SerializedName("follow") var follow: Any? = "",
                @SerializedName("header") var header: Header? = null,
                @SerializedName("content") var content: Content? = null
        ) : Serializable {
            data class Header(
                    /**
                     * 视频id
                     */
                    @SerializedName("id") var id: Int? = 0,
                    @SerializedName("title") var title: String? = "",
                    @SerializedName("font") var font: Any? = Any(),
                    @SerializedName("subTitle") var subTitle: Any? = Any(),
                    @SerializedName("subTitleFont") var subTitleFont: Any? = Any(),
                    @SerializedName("textAlign") var textAlign: String? = "",
                    @SerializedName("cover") var cover: Any? = Any(),
                    @SerializedName("label") var label: Any? = Any(),
                    @SerializedName("actionUrl") var actionUrl: String? = "",
                    @SerializedName("labelList") var labelList: Any? = Any(),
                    @SerializedName("icon") var icon: String? = "",
                    @SerializedName("iconType") var iconType: String? = "",
                    @SerializedName("description") var description: String? = "",
                    @SerializedName("time") var time: Long? = 0,
                    @SerializedName("showHateVideo") var showHateVideo: Boolean? = false
            ) : Serializable

            data class Content(
                    @SerializedName("type") var type: String? = "",
                    @SerializedName("data") var data: Data? = Data(),
                    @SerializedName("tag") var tag: Any? = Any(),
                    @SerializedName("id") var id: Int? = 0,
                    @SerializedName("adIndex") var adIndex: Int? = 0
            ) : Serializable {
                data class Data(
                        @SerializedName("dataType") var dataType: String? = "",
                        @SerializedName("id") var id: Int? = 0,
                        /**
                         * 首页需要展示的卡片标题
                         */
                        @SerializedName("title") var title: String? = "",
                        @SerializedName("description") var description: String? = "",
                        @SerializedName("library") var library: String? = "",
                        @SerializedName("tags") var tags: MutableList<Any>? = mutableListOf(),
                        @SerializedName("consumption") var consumption: Consumption? = Consumption(),
                        @SerializedName("resourceType") var resourceType: String? = "",
                        @SerializedName("slogan") var slogan: String? = "",
                        @SerializedName("provider") var provider: Provider? = Provider(),
                        @SerializedName("category") var category: String? = "",
                        @SerializedName("author") var author: Author? = Author(),
                        @SerializedName("cover") var cover: Cover? = Cover(),
                        @SerializedName("playUrl") var playUrl: String? = "",
                        @SerializedName("thumbPlayUrl") var thumbPlayUrl: Any? = Any(),
                        @SerializedName("duration") var duration: Int? = 0,
                        @SerializedName("webUrl") var webUrl: WebUrl? = WebUrl(),
                        @SerializedName("releaseTime") var releaseTime: Long? = 0,
                        @SerializedName("playInfo") var playInfo: MutableList<PlayInfo>? = mutableListOf(),
                        @SerializedName("campaign") var campaign: Any? = Any(),
                        @SerializedName("waterMarks") var waterMarks: Any? = Any(),
                        @SerializedName("adTrack") var adTrack: Any? = Any(),
                        @SerializedName("type") var type: String? = "",
                        @SerializedName("titlePgc") var titlePgc: String? = "",
                        @SerializedName("descriptionPgc") var descriptionPgc: String? = "",
                        @SerializedName("remark") var remark: Any? = Any(),
                        @SerializedName("idx") var idx: Int? = 0,
                        @SerializedName("shareAdTrack") var shareAdTrack: Any? = Any(),
                        @SerializedName("favoriteAdTrack") var favoriteAdTrack: Any? = Any(),
                        @SerializedName("webAdTrack") var webAdTrack: Any? = Any(),
                        @SerializedName("date") var date: Long? = 0,
                        @SerializedName("promotion") var promotion: Any? = Any(),
                        @SerializedName("label") var label: Any? = Any(),
                        @SerializedName("labelList") var labelList: MutableList<Any>? = mutableListOf(),
                        @SerializedName("descriptionEditor") var descriptionEditor: String? = "",
                        @SerializedName("collected") var collected: Boolean? = false,
                        @SerializedName("played") var played: Boolean? = false,
                        @SerializedName("subtitles") var subtitles: MutableList<Any>? = mutableListOf(),
                        @SerializedName("lastViewTime") var lastViewTime: Any? = Any(),
                        @SerializedName("playlists") var playlists: Any? = Any(),
                        @SerializedName("src") var src: Any? = Any()
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

                    data class Author(
                            @SerializedName("id") var id: Int? = 0,
                            @SerializedName("icon") var icon: String? = "",
                            @SerializedName("name") var name: String? = "",
                            @SerializedName("description") var description: String? = "",
                            @SerializedName("link") var link: String? = "",
                            @SerializedName("latestReleaseTime") var latestReleaseTime: Long? = 0,
                            @SerializedName("videoNum") var videoNum: Int? = 0,
                            @SerializedName("adTrack") var adTrack: Any? = Any(),
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

                    data class WebUrl(
                            @SerializedName("raw") var raw: String? = "",
                            @SerializedName("forWeibo") var forWeibo: String? = ""
                    ) : Serializable

                    data class Cover(
                            @SerializedName("feed") var feed: String? = "",
                            @SerializedName("detail") var detail: String? = "",
                            @SerializedName("blurred") var blurred: String? = "",
                            @SerializedName("sharing") var sharing: Any? = Any(),
                            /**
                             * 首页需要展示的图片
                             */
                            @SerializedName("homepage") var homepage: String? = ""
                    ) : Serializable
                }
            }

        }

    }
}
