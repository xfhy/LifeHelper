package com.xfhy.library.widgets

import android.content.Context
import android.os.Handler
import android.support.annotation.AttrRes
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.xfhy.library.R
import com.xfhy.library.utils.DensityUtil
import org.jetbrains.anko.collections.forEachByIndex

import java.util.ArrayList

/**
 * author xfhy
 * create at 17-10-3 下午1:19
 * description：自定义的Banner   广告轮播图
 *
 *
 * 需要实现的东西：
 * 自动轮播
 * 无限左划右划
 * 传入广告条目
 * 加载网络图片（Glide），加载文字
 * 底部小白点可切换，大小可换，数量可动态增加
 * 监听点击事件
 * 设置滑动事件
 * PagerAdapter
 * 触摸时不能自动滑动
 * 实现ViewPager点击事件
 * 让外部去实现图片加载，实现解耦
 *
 *
 *
 *
 * 遇到的坑：ViewPager把触摸事件消费了，外层重写onTouchEvent没用的，已经被子View消费的事件，是没用回传回来的，所以我直接在dispatchTouchEvent()
 * 分发事件的时候就获取到该事件，并停止ViewPager的滚动   当用户抬起手指时，继续滚动。
 */

class EasyBanner : FrameLayout, ViewPager.OnPageChangeListener {
    /**
     * 每个广告条目的图片地址
     */
    private var imageUrlList: List<String>? = null
    /**
     * 每个广告条目的文字内容
     */
    private var contentList: List<String>? = null
    /**
     * 用来盛放广告条目的
     */
    private var mViewPager: ViewPager? = null
    /**
     * 当前广告条目的文字内容
     */
    private var mContent: TextView? = null
    /**
     * 底部小圆点整个布局
     */
    private var mPointLayout: LinearLayout? = null
    /**
     * 用来加载banner图片的
     */
    private var imageViewList: MutableList<ImageView>? = null
    /**
     * 小圆点上一次的位置
     */
    private var lastPosition: Int = 0
    /**
     * 用户是否正在触摸banner
     */
    private var isTouched = false
    private val mHandler = PollingHandler()
    /**
     * banner点击事件监听器
     */
    private var listener: OnItemClickListener? = null
    /**
     * 图片加载器
     */
    private var imageLoader: ImageLoader? = null

    /**
     * 开启轮询?
     */
    /**
     * 获取当前轮播图是否在轮播
     * true:正在轮播  false:没有在轮播
     */
    var isPollingEnable = false
        private set

    /**
     * 延时的任务
     */
    internal var delayRunnable: Runnable = object : Runnable {
        override fun run() {
            //用户在触摸时不能进行自动滑动
            if (!isTouched) {
                //ViewPager设置为下一项
                mViewPager?.currentItem = (mViewPager?.currentItem ?: 0) + 1
            }
            if (isPollingEnable) {
                //继续延迟切换广告
                mHandler.postDelayed(this, BANNER_SWITCH_DELAY_MILLIS.toLong())
            }
        }
    }

    private class PollingHandler : Handler()

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //加载布局   子View个数==0  则还没有加载布局
        if (childCount == 0) {
            View.inflate(context, R.layout.layout_banner, this)
            mViewPager = findViewById<View>(R.id.vp_banner) as ViewPager
            mContent = findViewById<View>(R.id.tv_banner_content) as TextView
            mPointLayout = findViewById<View>(R.id.ll_banner_point) as LinearLayout
        }
    }

    /**
     * 初始化banner
     * 图片地址数必须和文字内容条目数相同
     *
     * @param imageUrlList 每个广告条目的图片地址
     * @param contentList  每个广告条目的文字内容
     */
    fun initBanner(imageUrlList: List<String>, contentList: List<String>) {
        this.imageUrlList = imageUrlList
        this.contentList = contentList
        if (imageUrlList == null || contentList == null || imageUrlList.size == 0 || contentList
                .size == 0) {
            throw IllegalArgumentException("传入图片地址或广告内容不能为空")
        }

        if (imageUrlList.size != contentList.size) {
            throw IllegalArgumentException("传入图片地址或广告内容数量必须一致")
        }

        initView()
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        imageViewList = ArrayList()
        var pointView: View

        imageUrlList?.forEachIndexed { index, imageUrl ->
            //加载图片
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            //让外部去实现图片加载，实现解耦
            if (imageLoader != null) {
                imageLoader?.loadImage(imageView, imageUrlList?.get(index) ?: "")
            }

            imageViewList?.add(imageView)

            //底部的小白点
            pointView = View(context)
            //设置背景
            pointView.setBackgroundResource(R.drawable.selector_banner_point)
            //设置小圆点的大小
            val layoutParams = LinearLayout.LayoutParams(DensityUtil.dip2px(context, POINT_DEFAULT_SIZE), DensityUtil.dip2px(context, POINT_DEFAULT_SIZE))

            //除第一个以外，其他小白点都需要设置左边距
            if (index != 0) {
                layoutParams.leftMargin = DensityUtil.dip2px(context, POINT_DEFAULT_SIZE / 2)
                pointView.isEnabled = false //默认小白点是不可用的
            }

            pointView.layoutParams = layoutParams
            mPointLayout?.addView(pointView)  //添加到linearLayout中
        }

        val bannerAdapter = BannerAdapter()
        mViewPager?.adapter = bannerAdapter
        //页面切换监听器
        mViewPager?.addOnPageChangeListener(this)

        //将ViewPager的起始位置放在  一个很大的数处，那么一开始就可以往左划动了   那个数必须是imageUrlList.size()的倍数
        val remainder = Integer.MAX_VALUE / 2 % (imageUrlList?.size ?: 0)
        mViewPager?.currentItem = Integer.MAX_VALUE / 2 - remainder
        //文本默认为第一项
        mContent?.text = contentList?.get(0)
        mPointLayout?.getChildAt(0)?.isEnabled = true
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

        val newPosition = position % (imageUrlList?.size ?: 0)

        //当页面切换时，将底部白点的背景颜色换掉
        mPointLayout?.getChildAt(newPosition)?.isEnabled = true
        mPointLayout?.getChildAt(lastPosition)?.isEnabled = false
        //文字内容替换掉
        mContent?.text = contentList?.get(newPosition)
        lastPosition = newPosition
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> isTouched = true   //正在触摸  按下
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> isTouched = false
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * banner中ViewPager的adapter
     */
    private inner class BannerAdapter : PagerAdapter() {

        /**
         * 返回资源一共有的条目数
         */
        override fun getCount(): Int {
            return Integer.MAX_VALUE
        }

        /**
         * 复用判断逻辑
         */
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val newPosition = position % (imageUrlList?.size ?: 0)
            val imageView = imageViewList?.get(newPosition)

            //设置点击事件
            imageView?.setOnClickListener {
                //回调
                if (listener != null) {
                    listener?.onItemClick(newPosition, (contentList?.get(newPosition) ?: ""))
                }
            }
            container.addView(imageView)
            return imageView as Any
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    /**
     * 设置banner的item点击事件
     *
     * @param listener OnItemClickListener
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        if (listener == null) {
            throw IllegalArgumentException("Item监听器不能为空！")
        }
        this.listener = listener
    }

    /**
     * 设置图片加载器  --必须设置 否则图片不会加载出来
     *
     * @param imageLoader
     */
    fun setImageLoader(imageLoader: ImageLoader) {
        if (imageLoader == null) {
            throw IllegalArgumentException("图片加载器不能为空")
        }
        this.imageLoader = imageLoader
        if (imageViewList != null) {
            imageViewList?.forEachIndexed { index, _ ->
                imageViewList?.get(index)?.let {
                    imageLoader.loadImage(it, imageUrlList?.get(index))
                }
            }
        }
    }

    /**
     * Item点击的”监听器“
     */
    interface OnItemClickListener {
        /**
         * 点击item时的回调函数
         *
         * @param position 当前点击item的索引
         * @param title    当前点击item的标题
         */
        fun onItemClick(position: Int, title: String)
    }

    /**
     * 向外部暴露的图片加载器，外界需要通过Glide或者其他方式来进行网络加载图片
     */
    interface ImageLoader {
        /**
         * 加载图片
         *
         * @param imageView ImageView
         * @param url       图片地址
         */
        fun loadImage(imageView: ImageView, url: String?)
    }

    /**
     * 开始轮播
     */
    fun start() {
        // 之前已经开启轮播  无需再开启
        if (isPollingEnable) {
            return
        }
        isPollingEnable = true
        mHandler.postDelayed(delayRunnable, BANNER_SWITCH_DELAY_MILLIS.toLong())
    }

    /**
     * 结束轮播
     */
    fun stop() {
        isPollingEnable = false
        isTouched = false
        //移除Handler Callback 和 Message 防止内存泄漏
        mHandler.removeCallbacksAndMessages(null)
    }

    /**
     * 重新设置数据
     *
     * @param imageUrlList 图片地址集合
     * @param contentList  标题集合
     */
    fun resetData(imageUrlList: List<String>, contentList: List<String>) {
        this.imageUrlList = imageUrlList
        this.contentList = contentList
        if (imageUrlList == null || contentList == null || imageUrlList.size == 0 || contentList
                .size == 0) {
            throw IllegalArgumentException("传入图片地址或广告内容不能为空")
        }

        if (imageUrlList.size != contentList.size) {
            throw IllegalArgumentException("传入图片地址或广告内容数量必须一致")
        }

        //判断是否之前在轮播
        if (isPollingEnable) {
            //停止之前的轮播
            stop()
        }
        //开始新的轮播
        start()
    }

    companion object {
        /**
         * 底部小圆点默认大小
         */
        private val POINT_DEFAULT_SIZE = 5f
        /**
         * 切换广告的时长  单位：ms
         */
        private val BANNER_SWITCH_DELAY_MILLIS = 3000
    }
}
