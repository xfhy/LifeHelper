package com.xfhy.userfo.activity

import android.os.Bundle
import android.view.View
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.activity.TitleBarActivity
import com.xfhy.library.ext.enable
import com.xfhy.library.ext.snackbar
import com.xfhy.library.utils.SoftKeyboardUtil
import com.xfhy.library.utils.StringUtils
import com.xfhy.userfo.R
import com.xfhy.provider.bean.UserInfo
import kotlinx.android.synthetic.main.user_activity_forget_pwd.*
import org.jetbrains.anko.startActivity


/**
 * 2018年3月3日19:40:37
 * 忘记密码
 *
 * 1. 首先查找当前用户是否存在,不存在则提示用户
 *
 * 2. 存在则进行登录操作,然后跳转到重置密码界面,
 * 直接获取已登录用户的本地信息,然后获取到id之后,更新用户信息并同步到bmob数据库
 */
class ForgetPwdActivity : TitleBarActivity(), View.OnClickListener {

    override fun getThisTitle() = "找回密码"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_forget_pwd)

        initView()
    }

    private fun initView() {

        mForgetPwdOkBtn.enable(mUserPhoneEt, { isForgetPwdBtnEnable() })
        mForgetPwdOkBtn.enable(mVerifyCodeEt, { isForgetPwdBtnEnable() })

        mVerifyCodeBtn.setOnClickListener(this)
        mForgetPwdOkBtn.setOnClickListener(this)
    }

    /**
     * 当2个文本输入框都为非空时才让注册按钮设置为true
     */
    private fun isForgetPwdBtnEnable(): Boolean {
        return mUserPhoneEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mForgetPwdOkBtn -> {
                requestUpdatePwd()
            }
            R.id.mVerifyCodeBtn -> {
                getVerifyCode()
            }
        }
    }

    /**
     * 获取验证码
     */
    private fun getVerifyCode() {
        SoftKeyboardUtil.hideSoftInput(mContext, mUserPhoneEt)
        if (!StringUtils.isPhone(mUserPhoneEt.text.toString())) {
            snackbar(mUserPhoneEt, "请输入正确的手机号")
            return
        }
        mVerifyCodeBtn.sendVerifyNumber()
        BmobSMS.requestSMSCode(mUserPhoneEt.text.toString(), "验证码", object : QueryListener<Int>() {

            override fun done(smsId: Int?, ex: BmobException?) {
                if (ex == null) {//验证码发送成功
                    snackbar(mVerifyCodeBtn, "验证码发送成功")
                } else {
                    Logger.e("${ex.message}")
                    snackbar(mVerifyCodeBtn, "验证码发送失败")
                }
            }
        })
    }

    private fun requestUpdatePwd() {
        val user = UserInfo()
        user.username = mUserPhoneEt.text.toString()
        user.mobilePhoneNumber = mUserPhoneEt.text.toString()

        startActivity<ResetPwdActivity>("verify_code" to mVerifyCodeEt.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        //避免内存泄露
        mVerifyCodeBtn.removeRunable()
    }

}
