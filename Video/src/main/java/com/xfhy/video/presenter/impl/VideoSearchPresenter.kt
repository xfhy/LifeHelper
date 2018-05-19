package com.xfhy.video.presenter.impl

import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.video.data.VideoDataManager
import com.xfhy.video.data.bean.HomeDataBean
import com.xfhy.video.presenter.VideoSearchContract
import org.jetbrains.anko.collections.forEachReversedByIndex
import java.net.URLDecoder

/**
 * Created by xfhy on 2018/3/17 21:00
 * Description : 视频搜索
 */
class VideoSearchPresenter(val mView: VideoSearchContract.View) :
        RxPresenter(), VideoSearchContract.Presenter {

    /**
     * 下一页视频地址
     */
    private var mNextPageUrl: String? = ""

    override fun reqHotSearchKeys() {
        addSubscribe(VideoDataManager.getHotSearchKeys()
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<MutableList<String>?>(mView) {
                    override fun onNext(t: MutableList<String>?) {
                        super.onNext(t)
                        mView.showHotSearchKeys(t)
                    }
                })
        )
    }

    override fun searchVideoByKey(key: String) {
        //将空格替换成"+"号
        mView.showLoading()
        key.replace(" ", "+")
        addSubscribe(VideoDataManager.searchVideoByKey(key)
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<HomeDataBean?>(mView) {
                    override fun onNext(t: HomeDataBean?) {
                        super.onNext(t)
                        mView.hideLoading()
                        //这样用是可以的,因为内部其实是for循环(while循环 从后往前)
                        t?.itemList?.forEachReversedByIndex { it ->
                            if (it.type == "textCard" || it.type == "briefCard") {
                                t.itemList?.remove(it)
                            }
                        }

                        /*
                        不能这样用 ,forEach时不能操作元素
                        t?.itemList?.forEach { it ->
                            if (it.type == "textCard" || it.type == "briefCard") {
                                t.itemList?.remove(it)
                            }
                        }*/

                        mView.showSearchVideoList(t?.itemList)
                        mNextPageUrl = t?.nextPageUrl
                    }
                })
        )
    }

    override fun loadMoreData() {
        //http://baobab.kaiyanapp.com/api/v3/search?start=10&num=10&query=%E9%98%85%E5%90%8E%E5%8D%B3%E7%9E%8E

        if (mNextPageUrl.isNullOrEmpty()) {
            mView.loadNoMoreData()
        }

        val lastIndexOfQuestMark = mNextPageUrl?.lastIndexOf("?")
        if (((lastIndexOfQuestMark ?: 0) + 1) >= mNextPageUrl?.length ?: 0) {
            mView.loadMoreFail()
            return
        }
        //将参数通过"&"隔开,然后放入map中
        mNextPageUrl = mNextPageUrl?.substring((lastIndexOfQuestMark ?: 0) + 1)
        val paramGroup = mNextPageUrl?.split("&")
        val paramMap = HashMap<String, String>()
        paramGroup?.forEach { it ->
            val param = it.split("=")
            if (param.size == 2) {
                paramMap.put(param[0], param[1])
            }
        }
        //解码 汉字参数
        val keyWord = URLDecoder.decode(paramMap["query"], "UTF-8")

        addSubscribe(VideoDataManager.searchVideoByKey(paramMap["start"] ?: "",
                paramMap["num"] ?: "",
                keyWord ?: "")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<HomeDataBean?>(mView) {
                    override fun onNext(t: HomeDataBean?) {
                        super.onNext(t)
                        mView.loadMoreSuccess(t?.itemList)
                        mNextPageUrl = t?.nextPageUrl
                    }
                })
        )
    }
}