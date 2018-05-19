package com.xfhy.video.fragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import android.view.View
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.fragment.BaseFragment
import com.xfhy.video.R
import com.xfhy.video.activity.VideoSearchActivity
import kotlinx.android.synthetic.main.video_fragment_main.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by xfhy on 2018/3/10 21:00
 * Description : 视频主页
 */
class VideoMainFragment : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {

        fun newInstance(): VideoMainFragment {

            val args = Bundle()

            val fragment = VideoMainFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private val mHomeFragment by lazy { VideoHomeFragment.newInstance() }
    private val mTypeFragment by lazy { VideoTypeFragment.newInstance() }
    private val mHotFragment by lazy { VideoHotFragment.newInstance() }
    private val mFragmentList: List<BaseFragment> = listOf(mHomeFragment, mTypeFragment, mHotFragment)

    override fun getLayoutResId(): Int {
        return R.layout.video_fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        mBottomNaviView.setOnNavigationItemSelectedListener(this)
        addFragment((activity as FragmentActivity).supportFragmentManager, mHomeFragment, "HomeFragment")
        mSearchFab.setOnClickListener {
            startActivity<VideoSearchActivity>()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.video_bottom_tab_home -> {
                addFragment((activity as FragmentActivity).supportFragmentManager, mHomeFragment, "HomeFragment")
                showFragment((activity as FragmentActivity).supportFragmentManager, mHomeFragment)
            }
            R.id.video_bottom_tab_type -> {
                addFragment((activity as FragmentActivity).supportFragmentManager, mTypeFragment, "TypeFragment")
                showFragment((activity as FragmentActivity).supportFragmentManager, mTypeFragment)
            }
            R.id.video_bottom_tab_hot -> {
                addFragment((activity as FragmentActivity).supportFragmentManager, mHotFragment, "HotFragment")
                showFragment((activity as FragmentActivity).supportFragmentManager, mHotFragment)
            }
        }
        return true
    }

    private fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, tag: String) {
        if (!fragment.isAdded && fragmentManager.findFragmentByTag(tag) == null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.mContentLayout, fragment, tag)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    private fun showFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        //显示自己 隐藏其他
        mFragmentList.forEach { it ->
            if (fragment == it) {
                fragmentTransaction.show(fragment)
            } else {
                if (!it.isHidden) {
                    fragmentTransaction.hide(it)
                }
            }
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

}