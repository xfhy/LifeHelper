package com.xfhy.library.widgets

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.Button
import com.xfhy.library.R

/**
 *验证码按钮
 */
class VerifyCodeButton(mContext: Context, attrs: AttributeSet) : Button(mContext, attrs) {
    private val mHandler: Handler
    private var mCount = 60
    private var mOnVerifyListener: OnVerifyListener? = null

    init {
        this.text = "获取验证码"
        mHandler = Handler()
    }

    /**
     *倒计时，并处理点击事件
     */
    fun sendVerifyNumber() {
        mHandler.postDelayed(countDownRunnable, 0)
        mOnVerifyListener?.onClick()
    }

    /**
     * 倒计时
     */
    private val countDownRunnable = object : Runnable {
        override fun run() {
            //显示秒数
            this@VerifyCodeButton.text = "${mCount}s "
            this@VerifyCodeButton.setBackgroundColor(resources.getColor(R.color.common_disable))
            this@VerifyCodeButton.setTextColor(resources.getColor(R.color.common_white))
            //此时禁止点击按钮
            this@VerifyCodeButton.isEnabled = false

            if (mCount > 0) {
                //继续发送事件  -1s
                mHandler.postDelayed(this, 1000)
            } else {
                resetCounter()
            }
            mCount--
        }
    }

    fun removeRunable() {
        mHandler.removeCallbacks(countDownRunnable)
    }

    /*
        恢复到初始状态
     */
    fun resetCounter(vararg text: String) {
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]) {
            this.text = text[0]
        } else {
            this.text = "重获验证码"
        }
        this.setBackgroundColor(resources.getColor(R.color.common_blue))
        this.setTextColor(resources.getColor(R.color.common_white))
        mCount = 60
    }

    /*
        点击事件接口
     */
    interface OnVerifyListener {
        fun onClick()
    }

    fun setOnVerifyListener(onVerifyListener: OnVerifyListener) {
        this.mOnVerifyListener = onVerifyListener
    }
}
