package com.xfhy.userfo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.activity.TitleBarActivity
import com.xfhy.library.ext.enable
import com.xfhy.library.ext.snackbar
import com.xfhy.library.utils.MD5Util
import com.xfhy.library.utils.SoftKeyboardUtil
import com.xfhy.library.utils.StringUtils
import com.xfhy.provider.arouter.ARouterPath
import com.xfhy.userfo.R
import com.xfhy.provider.bean.UserInfo
import kotlinx.android.synthetic.main.user_activity_login.*
import org.jetbrains.anko.startActivity


/**
 * 2018年3月3日19:44:25
 * 登录界面
 *
 * Bmob底层源码处理登录:首先通过账号密码进行登录,登录成功后将账号信息中的password置为空(保证了安全),
 * 然后将账户信息通过Gson转成成json信息保存到SharedPreferences文件bmob_sp.xml中,key是"user".
 * 这个时候如果需要获取账户信息只需要将SharedPreferences中的"user"节点取出,再通过Gson转成UserInfo类即可.
 */
@Route(path = ARouterPath.USER_CENTER_LOGIN_PATH)
class LoginActivity : TitleBarActivity(), View.OnClickListener, View.OnFocusChangeListener {

    override fun getThisTitle() = "登录"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_login)

        initView()
    }

    private fun initView() {
        mLoginBtn.enable(mUserPhoneEt, { isLoginBtnEnable() })
        mLoginBtn.enable(mPwdEt, { isLoginBtnEnable() })

        mLoginBtn.setOnClickListener(this)
        mNoAccountTv.setOnClickListener(this)
        mForgetPwdTv.setOnClickListener(this)
        mUserPhoneEt.onFocusChangeListener = this
        mPwdEt.onFocusChangeListener = this
    }

    /**
     * 当2个文本输入框都为非空时才让注册按钮设置为true
     */
    private fun isLoginBtnEnable(): Boolean {
        return mUserPhoneEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLoginBtn -> {
                startLogin()
            }
            R.id.mForgetPwdTv -> {
                startActivity<ForgetPwdActivity>()
            }
            R.id.mNoAccountTv -> {
                startActivity<RegisterActivity>()
            }
        }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            when (v.id) {
                R.id.mUserPhoneEt -> {
                    mCurrentStateIv.setImageResource(R.drawable.login_state_input)
                }
                R.id.mPwdEt -> {
                    mCurrentStateIv.setImageResource(R.drawable.login_state_password)
                }
            }
        }
    }

    private fun startLogin() {
        SoftKeyboardUtil.hideSoftInput(mContext, mUserPhoneEt)
        //判断数据是否合法
        if (!StringUtils.isPhone(mUserPhoneEt.text.toString())) {
            snackbar(mCurrentStateIv, "请输入正确的手机号")
            return
        }
        if (mPwdEt.text.toString().length < 6) {
            snackbar(mCurrentStateIv, "密码过于简短")
            return
        }

        showLoading()

        //设置用户数据
        val user = UserInfo()
        user.username = mUserPhoneEt.text.toString()
        user.setPassword(MD5Util.encoder(mPwdEt.text.toString()))

        //登录
        user.login(object : SaveListener<UserInfo>() {
            override fun done(userInfo: UserInfo?, e: BmobException?) {
                hideLoading()
                if (e == null) {
                    snackbar(mUserPhoneEt, "登录成功")
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息

                    //登录成功 则跳转到主页
                    val intent = Intent()
                    intent.setClassName(mContext,"com.xfhy.life.ui.activity.MainActivity")
                    mContext.startActivity(intent)
                    finish()
                } else {
                    snackbar(mUserPhoneEt, "登录失败 请稍后重试")
                    Logger.e("登录失败 ${e.message}")
                }
            }
        })

    }


}
