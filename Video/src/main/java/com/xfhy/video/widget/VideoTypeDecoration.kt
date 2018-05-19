package com.xfhy.video.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xfhy.library.utils.DensityUtil


/**
 * Created by xfhy on 2018/3/12 8:07
 * Description : 视频分类 分割线
 */
class VideoTypeDecoration : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val itemPosition = parent?.getChildAdapterPosition(view) ?: 0

        view?.let {
            val offset = DensityUtil.dip2px(it.context, 2f)
            outRect?.set(if (itemPosition % 2 == 0) 0 else offset,   //第一列无左   第二列有左
                    offset,
                    if (itemPosition % 2 == 0) offset else 0,        //第一列有右   第二列无右
                    offset)
        }

    }

}