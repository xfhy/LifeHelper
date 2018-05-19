package com.xfhy.library.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.xfhy.library.R

/**
 * Created by xfhy on 2017/9/24 21:27.
 * Description 用于Glide的加载图片
 * 由于每次加载图片都需要单独地配置,显得很麻烦,所以在这里简单封装一下
 */

object GlideUtils {

    /*
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */

    /**
     * 图片加载的默认动画时长
     */
    private val IMAGE_LOAD_DURATION = 500

    /**
     * 默认加载方式
     */
    fun loadImageView(mContext: Context, path: String, mImageView: ImageView) {
        Glide.with(mContext).load(path).into(mImageView)
    }

    /**
     * 加载指定大小
     */
    fun loadImageViewSize(mContext: Context, path: String, width: Int, height: Int,
                          mImageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(mContext).load(path).apply(requestOptions.override(width, height)).into(mImageView)
    }

    /**
     * 设置加载中以及加载失败图片
     */
    fun loadImageViewLoding(mContext: Context, path: String, mImageView: ImageView,
                            loadingImage: Int, errorImageView: Int) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(mContext).load(path).apply(requestOptions.placeholder(loadingImage).error(errorImageView)).into(mImageView)
    }

    /**
     * 设置加载中以及加载失败图片并且指定大小
     */
    fun loadImageViewLoadingSize(mContext: Context, path: String, width: Int, height: Int, mImageView: ImageView, loadingImage: Int, errorImageView: Int) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(mContext).load(path).apply(requestOptions.override(width, height).placeholder(loadingImage).error(errorImageView)).into(mImageView)
    }

    /**
     * 加载图片 设置跳过内存缓存
     */
    fun loadImageViewCache(mContext: Context, path: String, mImageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(mContext).load(path).apply(requestOptions.skipMemoryCache(true)).into(mImageView)
    }

    /**
     * 设置下载优先级
     */
    fun loadImageViewPriority(mContext: Context, path: String, mImageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(mContext).load(path).apply(requestOptions.priority(Priority.NORMAL)).into(mImageView)
    }

    /*
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    /**
     * 设置缓存策略
     */
    fun loadImageViewDiskCache(mContext: Context, path: String, mImageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(mContext).load(path).apply(requestOptions.diskCacheStrategy(DiskCacheStrategy
                .ALL)).into(mImageView)
    }

    /*
     * api也提供了几个常用的动画：比如crossFade()
     */

    /**
     * 加载图片 并设置加载动画
     */
    fun loadImageViewAnim(mContext: Context, path: String, anim: Int, mImageView: ImageView) {
        Glide.with(mContext).load(path).transition(DrawableTransitionOptions.withCrossFade()).into(mImageView)
    }

    /**
     * 设置缩略图支持  会先加载缩略图
     */
    fun loadImageViewThumbnail(mContext: Context, path: String, mImageView: ImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView)
    }

    /*
     * api提供了比如：centerCrop()、fitCenter()等
     */

    /**
     * 加载图片 并设置动态转换
     */
    fun loadImageViewCrop(mContext: Context, path: String, mImageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(mContext).load(path).apply(requestOptions.centerCrop()).into(mImageView)
    }

    /**
     * 清理磁盘缓存
     */
    fun GuideClearDiskCache(mContext: Context) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache()
    }

    /**
     * 清理内存缓存
     */
    fun GuideClearMemory(mContext: Context) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory()
    }

    /**
     * 加载图片   并设置完所有东西  方便加载  里面设置有加载失败图片,缓存策略,动画
     *
     * @param context   Context
     * @param path      路径
     * @param imageView 需要加载图片到那个ImageView上
     */
    @JvmStatic
    fun loadCustomImage(context: Context, path: String?, imageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(context)
                .load(path)
                .thumbnail(0.1f)   //缩略图
                .apply(requestOptions
                        .placeholder(R.drawable.ic_icon_load_error)
                        .error(R.drawable.ic_icon_load_error)   // 加载失败图片   暂时没设置,还没有素材
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) // 缓存策略是:所有的都缓存:内存缓存和磁盘缓存
                .transition(DrawableTransitionOptions.withCrossFade(IMAGE_LOAD_DURATION)) //动画
                .into(imageView)
    }

    /**
     * 加载图片   并设置完所有东西  方便加载  里面设置有加载失败图片,缓存策略,动画
     *
     * @param context   Context
     * @param path      路径
     * @param imageView 需要加载图片到那个ImageView上
     */
    @JvmStatic
    fun loadCustomImage(activity: Activity?, path: String?, imageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        activity?.let {
            Glide.with(activity)
                    .load(path)
                    .thumbnail(0.1f)   //缩略图
                    .apply(requestOptions
                            .placeholder(R.drawable.ic_icon_load_error)
                            .error(R.drawable.ic_icon_load_error)   // 加载失败图片
                            .diskCacheStrategy(DiskCacheStrategy.ALL)) // 缓存策略是:所有的都缓存:内存缓存和磁盘缓存
                    .transition(DrawableTransitionOptions.withCrossFade(IMAGE_LOAD_DURATION)) //动画
                    .into(imageView)
        }
    }

    /**
     * 加载图片   并设置完所有东西  方便加载  里面设置有加载失败图片,缓存策略,动画
     *
     * @param view   View
     * @param path      路径
     * @param imageView 需要加载图片到那个ImageView上
     */
    fun loadCustomImage(view: View, path: String?, imageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(view)
                .load(path)
                .thumbnail(0.1f)   //缩略图
                .apply(requestOptions
                        .placeholder(R.drawable.ic_icon_load_error)
                        .error(R.drawable.ic_icon_load_error)   // 加载失败图片
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) // 缓存策略是:所有的都缓存:内存缓存和磁盘缓存
                .transition(DrawableTransitionOptions.withCrossFade(IMAGE_LOAD_DURATION)) //动画
                .into(imageView)
    }

    /**
     * 加载图片 无占位图   并设置完所有东西  方便加载  里面设置有加载失败图片,缓存策略,动画
     *
     * @param view   View
     * @param path      路径
     * @param imageView 需要加载图片到那个ImageView上
     */
    fun loadCustomImageNoPlace(view: View, path: String?, imageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(view)
                .load(path)
                .apply(requestOptions
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) // 缓存策略是:所有的都缓存:内存缓存和磁盘缓存
                .into(imageView)
    }

    /**
     * 加载图片 无占位图   并设置完所有东西  方便加载  里面设置有加载失败图片,缓存策略,动画
     *
     * @param context   Context
     * @param path      路径
     * @param imageView 需要加载图片到那个ImageView上
     */
    fun loadCustomImageNoPlace(context: Context, path: String?, imageView: ImageView) {
        //图片加载的配置参数
        val requestOptions = RequestOptions()
        Glide.with(context)
                .load(path)
                .apply(requestOptions
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) // 缓存策略是:所有的都缓存:内存缓存和磁盘缓存
                .into(imageView)
    }

    /**
     * 加载Gif图片
     */
    fun loadGif(context: Context, drawable: Int, imageView: ImageView) {
        Glide.with(context).load(drawable).into(imageView)
    }

}
