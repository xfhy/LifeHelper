package com.xfhy.library.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.xfhy.library.R
import com.xfhy.library.utils.SoftKeyboardUtil
import kotlinx.android.synthetic.main.layout_common_comment_view.view.*
import org.jetbrains.anko.textColor

/**
 * Created by xfhy on 2018/3/26 10:18
 * Description : 通用评论View
 */
class CommonCommentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), TextWatcher {

    init {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_common_comment_view, this, true)

        mCommentEt.addTextChangedListener(this)

        mSendTv.setOnClickListener sendListener@ {
            val comment = mCommentEt.text.toString()
            if (comment.isEmpty()) {
                return@sendListener
            }
            onSendClick.invoke(comment)
            mCommentEt.setText("")
            mCommentEt.clearFocus()
            SoftKeyboardUtil.hideSoftInput(context, this)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            if (it.isNotEmpty()) {
                mSendTv.textColor = resources.getColor(R.color.colorPrimary)
            } else {
                mSendTv.textColor = resources.getColor(R.color.color_949494)
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    /**
     * 发送的点击事件
     */
    var onSendClick: (String) -> Unit = {}

}