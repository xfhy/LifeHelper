package com.xfhy.library.utils

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * Created by feiyang on 2018/4/4 16:27
 * Description : 模糊图片
 */
class GlideBlurformation(val context: Context) : BitmapTransformation() {

    companion object {
        /**
         * 模糊度
         */
        val blurRadius = 25f
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return BitmapUtil.blurBitmap(context, toTransform, blurRadius, outWidth, outHeight);
    }
}