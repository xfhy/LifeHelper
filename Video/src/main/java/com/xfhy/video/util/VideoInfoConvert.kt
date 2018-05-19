package com.xfhy.video.util

import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.data.bean.VideoInfoBean
import com.xfhy.video.data.bean.VideoItemV4Bean

/**
 * Created by xfhy on 2018/3/17 16:50
 * Description : 用于数据转换工具类  视频info
 */
object VideoInfoConvert {

    /**
     * V5 -> VideoInfoBean
     */
    fun convertV5ItemData(itemData: HomeDataBean.ItemList?): VideoInfoBean {
        val videoInfoBean = VideoInfoBean()

        videoInfoBean.title = itemData?.data?.content?.data?.title
        videoInfoBean.category = itemData?.data?.content?.data?.category
        videoInfoBean.duration = itemData?.data?.content?.data?.duration

        videoInfoBean.description = itemData?.data?.content?.data?.description
        videoInfoBean.collectionCount = itemData?.data?.content?.data?.consumption?.collectionCount
        videoInfoBean.shareCount = itemData?.data?.content?.data?.consumption?.shareCount
        videoInfoBean.replyCount = itemData?.data?.content?.data?.consumption?.replyCount
        videoInfoBean.raw = itemData?.data?.content?.data?.webUrl?.raw
        itemData?.data?.content?.data?.playInfo?.forEach { it ->
            if (it.type == "high") {
                videoInfoBean.highUrl = it.url
            }
            if (it.type == "normal") {
                videoInfoBean.normalUrl = it.url
            }
        }
        videoInfoBean.id = itemData?.data?.content?.data?.id

        return videoInfoBean
    }

    /**
     * V4 -> VideoInfoBean
     */
    fun convertV4ItemData(itemData: VideoItemV4Bean.Item?): VideoInfoBean {

        val videoInfoBean = VideoInfoBean()

        videoInfoBean.title = itemData?.data?.title
        videoInfoBean.category = itemData?.data?.category
        videoInfoBean.duration = itemData?.data?.duration

        videoInfoBean.description = itemData?.data?.description
        videoInfoBean.collectionCount = itemData?.data?.consumption?.collectionCount
        videoInfoBean.shareCount = itemData?.data?.consumption?.shareCount
        videoInfoBean.replyCount = itemData?.data?.consumption?.replyCount
        videoInfoBean.raw = itemData?.data?.webUrl?.raw
        itemData?.data?.playInfo?.forEach { it ->
            if (it.type == "high") {
                videoInfoBean.highUrl = it.url
            }
            if (it.type == "normal") {
                videoInfoBean.normalUrl = it.url
            }
        }
        videoInfoBean.id = itemData?.data?.id

        return videoInfoBean
    }

}