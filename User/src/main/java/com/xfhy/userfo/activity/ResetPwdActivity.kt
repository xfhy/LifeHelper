package com.xfhy.userfo.activity

import android.os.Bundle
import android.view.View
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.xfhy.library.basekit.activity.TitleBarActivity
import com.xfhy.library.ext.enable
import com.xfhy.library.ext.snackbar
import com.xfhy.library.utils.MD5Util
import com.xfhy.library.utils.SoftKeyboardUtil
import com.xfhy.library.widgets.CustomDialog
import com.xfhy.library.widgets.LoadingDialog
import com.xfhy.userfo.R
import kotlinx.android.synthetic.main.user_activity_reset_pwd.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by xfhy on 2018年3月3日19:32:59
 * Description : 重置密码
 *
 * 获取验证码->成功->重新设置密码->成功
 */
class ResetPwdActivity : TitleBarActivity(), View.OnClickListener {

    override fun getThisTitle() = "重置密码"

    private var mVerifyCode: String? = null
    val mGoLoginDialog by lazy {
        CustomDialog(mContext, content = "重置密码成功!", rightBtnText =
        "去登录", listener = object : CustomDialog.OnCustomDialogListener {
            override fun onLeftClick() {
            }

            override fun onRightClick() {
                startActivity<LoginActivity>()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_reset_pwd)

        initIntentData()
        initView()
    }

    private fun initIntentData() {
        if (intent != null) {
            mVerifyCode = intent.getStringExtra("verify_code")
        }
    }

    private fun initView() {
        mResetPwdBtn.enable(mPwdEt, { isResetPwdBtnEnable() })
        mResetPwdBtn.enable(mPwdConfirmEt, { isResetPwdBtnEnable() })

        mResetPwdBtn.setOnClickListener(this)
    }

    /**
     * 当2个文本输入框都为非空时才让注册按钮设置为true
     */
    private fun isResetPwdBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mResetPwdBtn -> {
                resetPwd()
            }
            else -> {
            }
        }
    }

    /**
     * 重置密码
     */
    private fun resetPwd() {

        SoftKeyboardUtil.hideSoftInput(mContext, mPwdEt)
        if (mPwdEt.text.toString().length < 6) {
            snackbar(mPwdEt, "密码长度过于简短")
            return
        }
        if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
            snackbar(mPwdEt, "两次输入的密码不一致")
            return
        }

        showLoading()

        //重置密码  通过验证码
        BmobUser.resetPasswordBySMSCode(mVerifyCode, MD5Util.encoder(mPwdEt.text.toString()),
                object : UpdateListener() {
                    override fun done(ex: BmobException?) {
                        hideLoading()
                        if (ex == null) {
                            //重置成功
                            mGoLoginDialog.setCancelable(false)
                            mGoLoginDialog.show()
                        } else {
                            if (ex.errorCode == 207) {
                                snackbar(mPwdEt, "验证码错误,重置密码失败")
                                return
                            }
                            toast("重置失败：code = ${ex.errorCode} ,msg = ${ex.localizedMessage}")
                        }
                    }
                })
    }

}
