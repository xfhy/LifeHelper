package com.xfhy.zhihu.data.bean


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.xfhy.library.adapter.entity.SectionEntity

/**
 * author xfhy
 * create at 2018年3月10日16:44:36
 * description：日报评论   长评论和短评论都是一样的格式
 */
class DailyCommentBean {

    /**
     * 长评论列表，形式为数组（请注意，其长度可能为 0）
     */
    var comments: MutableList<CommentsBean>? = null

    class CommentsBean : SectionEntity<DailyCommentBean.CommentsBean> {

        /**
         * 评论作者
         */
        var author: String? = null
        /**
         * 评论的内容
         */
        var content: String? = null
        /**
         * 用户头像图片的地址
         */
        var avatar: String? = null
        /**
         * 评论时间
         */
        var time: Long = 0
        /**
         * 评论者的唯一标识符
         */
        var id: Int = 0
        /**
         * 评论所获『赞』的数量
         */
        var likes: Int = 0
        /**
         * 所回复的消息
         */
        @SerializedName("replyTo")
        var replyTo: ReplyToBean? = null

        constructor() {}

        constructor(isHeader: Boolean) : super(isHeader) {}
        constructor(isHeader: Boolean, header: String) : super(isHeader, header)


        override fun toString(): String {
            return "CommentsBean{" +
                    "author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", time=" + time +
                    ", id=" + id +
                    ", likes=" + likes +
                    ", replyTo=" + replyTo +
                    '}'
        }

        class ReplyToBean {

            /**
             * 原消息的内容
             */
            var content: String? = null
            /**
             * 消息状态，0为正常，非0为已被删除
             */
            var status: Int = 0
            /**
             * 被回复者的唯一标识符
             */
            var id: Int = 0
            /**
             * 被回复者
             */
            var author: String? = null

            /**
             * 展开状态
             */
            @Expose(serialize = false)
            var expandState = 0

            override fun toString(): String {
                return "ReplyToBean{" +
                        "content='" + content + '\'' +
                        ", status=" + status +
                        ", id=" + id +
                        ", author='" + author + '\'' +
                        '}'
            }
        }
    }

    override fun toString(): String {
        return "DailyCommentBean{" +
                "comments=" + comments +
                '}'
    }
}
