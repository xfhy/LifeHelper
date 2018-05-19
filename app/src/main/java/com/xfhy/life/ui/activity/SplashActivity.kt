package com.xfhy.life.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.xfhy.life.R
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * 2018年3月6日15:32:18
 * 启动页
 */
class SplashActivity : AppCompatActivity(), Animator.AnimatorListener {

    var jumpCount = 4
    var disposable: Disposable? = null
    /**
     * 请求权限次数
     */
    var reqPermissionCount = 0

    companion object {
        /**
         * 最多请求权限次数  超过这个数就退出
         */
        const val REQ_READ_PHONE_STATE_COUNT_MAX = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        reqPermission()
    }

    private fun reqPermission() {
        RxPermissions(this).request(Manifest.permission.READ_PHONE_STATE).subscribe { granted ->
            if (granted) {
                initView()
            } else {
                reqPermissionCount++
                if (reqPermissionCount == REQ_READ_PHONE_STATE_COUNT_MAX) {
                    toast("必要权限必须授予哦,不然不能正常使用APP")
                    finish()
                }
                reqPermission()
            }
        }
    }

    private fun initView() {
        //文字透明度  动画
        val alpha = ObjectAnimator.ofFloat(mSplashTextTv, "alpha", 0.1f, 1.0f)
        val scale = ObjectAnimator.ofFloat(mSplashTextTv, "scale", 1.0f, 1.8f)

        //动画集合
        val animatorSet = AnimatorSet()
        animatorSet.play(alpha).with(scale)
        animatorSet.duration = 4000
        animatorSet.start()

        //倒计时
        disposable = Flowable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mJumpOverTv.text = "跳过${jumpCount--}"
                })

        //添加文字动画监听器
        alpha.addListener(this)
        mJumpOverTv.setOnClickListener {
            finishActivity()
        }
    }

    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
        finishActivity()
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    override fun onAnimationStart(animation: Animator?) {
    }

    private fun finishActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun onBackPressed() {
        //返回键 不生效
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
