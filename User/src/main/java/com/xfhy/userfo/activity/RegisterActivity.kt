package com.xfhy.userfo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.orhanobut.logger.Logger
import com.xfhy.library.ext.enable
import com.xfhy.library.ext.snackbar
import com.xfhy.library.utils.StringUtils
import com.xfhy.userfo.R
import kotlinx.android.synthetic.main.user_activity_register.*
import com.xfhy.library.basekit.activity.TitleBarActivity
import com.xfhy.library.data.bean.BaseResp
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.library.utils.MD5Util
import com.xfhy.library.utils.SoftKeyboardUtil
import com.xfhy.provider.bean.UserInfo
import com.xfhy.provider.data.ProviderDataManager
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * Created by xfhy on 2018/3/3 8:19
 * Description : 注册界面
 */
class RegisterActivity : TitleBarActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_register)

        initView()
    }

    override fun getThisTitle() = "注册"

    private fun initView() {
        mRegisterBtn.enable(mUserPhoneEt, { isRegisterBtnEnable() })
        mRegisterBtn.enable(mVerifyCodeEt, { isRegisterBtnEnable() })
        mRegisterBtn.enable(mPwdEt, { isRegisterBtnEnable() })
        mRegisterBtn.enable(mPwdConfirmEt, { isRegisterBtnEnable() })

        mRegisterBtn.setOnClickListener(this)
        mVerifyCodeBtn.setOnClickListener(this)
        mGoLoginTv.setOnClickListener(this)
        mForgetPwdTv.setOnClickListener(this)
    }

    /**
     * 当四个文本输入框都为非空时才让注册按钮设置为true
     */
    private fun isRegisterBtnEnable(): Boolean {
        return mUserPhoneEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mRegisterBtn -> {
                analyInputData()
            }
            R.id.mVerifyCodeBtn -> {
                sendVerifyCode()
            }
            R.id.mGoLoginTv -> {
                startActivity<LoginActivity>()
            }
            R.id.mForgetPwdTv -> {
                startActivity<ForgetPwdActivity>()
            }
        }
    }

    private fun sendVerifyCode() {
        SoftKeyboardUtil.hideSoftInput(mContext, mUserPhoneEt)
        if (!StringUtils.isPhone(mUserPhoneEt.text.toString())) {
            snackbar(mVerifyCodeBtn, "请输入正确的手机号")
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

    private fun analyInputData() {
        SoftKeyboardUtil.hideSoftInput(mContext, mUserPhoneEt)
        if (mPwdEt.text.toString().length < 6) {
            snackbar(mVerifyCodeEt, "密码过于简短")
            return
        }
        if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
            snackbar(mVerifyCodeEt, "两次输入的密码不一致")
            return
        }

        showLoading()

        val user = UserInfo()
        user.username = mUserPhoneEt.text.toString()
        user.setPassword(MD5Util.encoder(mPwdEt.text.toString()))
        user.mobilePhoneNumber = mUserPhoneEt.text.toString()

        //注册
        user.signOrLogin(mVerifyCodeEt.text.toString(), object : SaveListener<UserInfo>() {
            override fun done(userInfo: UserInfo?, e: BmobException?) {
                hideLoading()
                if (e == null) {
                    toast("注册成功")
                    addUserToLH(userInfo ?: user)
                } else {
                    toast("失败:" + e.message)
                }
            }
        })
    }

    /**
     * 将该用户信息同步添加到我的服务器上去
     */
    private fun addUserToLH(user: UserInfo) {
        ProviderDataManager.addUser(user.objectId,
                user.username,
                user.mobilePhoneNumber,
                MD5Util.encoder(mPwdEt.text.toString()))
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BaseResp<String>>(null) {
                    override fun onNext(t: BaseResp<String>?) {
                        super.onNext(t)
                        if (t?.code == 0) {
                            Logger.e("同步到LH服务器成功")
                        }
                        goMainActivity()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        Logger.e("同步到LH服务器失败")
                        goMainActivity()
                    }
                })
    }

    private fun goMainActivity() {
        //登录成功 则跳转到主页
        val intent = Intent()
        intent.setClassName(mContext, "com.xfhy.life.ui.activity.MainActivity")
        mContext.startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        //移除handler的Runnable
        mVerifyCodeBtn.removeRunable()
    }


}