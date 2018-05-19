package com.xfhy.userfo.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xfhy.library.basekit.activity.BaseMvpActivity
import com.xfhy.library.utils.GlideBlurformation
import com.xfhy.library.utils.GlideUtils
import com.xfhy.library.utils.ShareUtil
import com.xfhy.userfo.R
import com.xfhy.userfo.data.bean.DayStateBean
import com.xfhy.userfo.presenter.SignInContract
import com.xfhy.userfo.presenter.impl.SignInPresenter
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.toast

/**
 * 2018年4月3日16:52:24
 * 签到
 *
 * 服务器端User需要新增字段:上次签到时间   避免一天内重复签到
 * 还需要新增接口,查询当前用户的积分
 */
class SignInActivity : BaseMvpActivity<SignInPresenter>(), SignInContract.View {

    companion object {
        const val EXTRA_IS_SIGN_SUCCESS = "is_sign_success"
    }

    /**
     * 是否签到成功
     */
    private var mIsSignSuccess = false
    private var mDayStateBean: DayStateBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initView()
        initIntentData()
    }

    private fun initView() {
        mCloseIv.setOnClickListener {
            setBackData()
            finish()
            //指定进入退出动画风格
            overridePendingTransition(android.R.anim.fade_in, R.anim.push_top_out)
        }
        mShareStateIv.setOnClickListener {
            ShareUtil.shareText(this, "刚刚在LifeHelper上看到一句话:${mDayStateBean?.content} \n  " +
                    "${mDayStateBean?.shareImg}")
        }
    }

    private fun initIntentData() {
        val isSigned = intent.getBooleanExtra("isSigned", false)
        mIsSignSuccess = isSigned
        //如果已经签到  则不需要签到了
        if (!isSigned) {
            mPresenter?.reqSignIn()
        }
        mPresenter?.reqDayStateData()
    }

    override fun initPresenter() {
        mPresenter = SignInPresenter(this)
    }

    override fun showSignInResult(isSuccess: Boolean) {
        mIsSignSuccess = isSuccess
        toast(if (isSuccess) "签到成功  积分+2" else "签到失败")
    }

    @SuppressLint("SetTextI18n")
    override fun showDayStateData(dayStateBean: DayStateBean) {
        mDayStateBean = dayStateBean
        Glide.with(this)
                .load(dayStateBean.picture2)
                .apply(RequestOptions.bitmapTransform(GlideBlurformation(this)))
                .into(mSignInBgIv)
        GlideUtils.loadImageView(mContext, dayStateBean.picture ?: "", mDayPicture)
        mStateContentTv.text = "${dayStateBean.content} --${dayStateBean.note}"
    }

    override fun showDayOnMonthText(str: String) {
        mDayOfMonthTv.text = str
    }

    override fun showMonthText(str: String) {
        mMonthTv.text = str
    }

    override fun showDayOfWeekText(str: String) {
        mDayOfWeekTv.text = str
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setBackData()
        //指定进入退出动画风格
        overridePendingTransition(android.R.anim.fade_in, R.anim.push_top_out)
    }

    /**
     * 设置回传数据
     */
    private fun setBackData() {
        intent.putExtra(EXTRA_IS_SIGN_SUCCESS, mIsSignSuccess)
        setResult(Activity.RESULT_OK, intent)
    }

}
