package com.xfhy.library.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.zhihu.matisse.engine.ImageEngine
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.xfhy.library.R


/**
 * Created by xfhy on 2018/3/6 16:43
 *
 * Description : 因Matisse库中的Glide版本是v3,而我的项目中引入的Glide是v4,
 * 一些方法用法已经不一样了,直接使用GlideEngine会报错.
 * 于是自己实现一个ImageEngine.
 */
class CustomGlideEngine : ImageEngine {
    /**
     * 加载gif
     */
    override fun loadGifImage(context: Context?, resizeX: Int, resizeY: Int, imageView: ImageView?, uri: Uri?) {

    }

    /**
     * 加载静态图片资源
     */
    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri?) {
        val options = RequestOptions()
                .centerCrop()
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)   //设置加载优先级
        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView)
    }

    /**
     * 是否支持gif   我觉得可以不需要gif
     */
    override fun supportAnimatedGif(): Boolean {
        return false
    }

    /**
     * 加载gif缩略图
     */
    override fun loadGifThumbnail(context: Context, resize: Int, placeholder: Drawable?, imageView: ImageView, uri: Uri?) {
        val options = RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .error(R.drawable.icon_load_error)
                .override(resize, resize)
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载静态资源缩略图
     */
    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable?, imageView: ImageView, uri: Uri?) {
        val options = RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .error(R.drawable.icon_load_error)
                .override(resize, resize)
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(uri)
                .apply(options)
                .into(imageView)
    }
}