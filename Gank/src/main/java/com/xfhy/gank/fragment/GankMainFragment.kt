package com.xfhy.gank.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.xfhy.gank.R
import com.xfhy.gank.adapter.GankTabAdapter
import com.xfhy.gank.config.GankConfig
import com.xfhy.library.basekit.fragment.BaseFragment
import kotlinx.android.synthetic.main.gank_fragment_main.*

/**
 * Created by xfhy on 2018/3/20 13:07
 * Description : 干货主页
 */
class GankMainFragment : BaseFragment() {

    companion object {

        fun newInstance(): GankMainFragment {

            val args = Bundle()

            val fragment = GankMainFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private val androidFragment: GankCommonFragment by lazy {
        GankCommonFragment.newInstance(GankConfig.TAB_TITLE[0])
    }
    private val iosFragment: GankCommonFragment by lazy {
        GankCommonFragment.newInstance(GankConfig.TAB_TITLE[1])
    }
    private val uiFragment: GankCommonFragment by lazy {
        GankCommonFragment.newInstance(GankConfig.TAB_TITLE[2])
    }

    override fun getLayoutResId(): Int {
        return R.layout.gank_fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        val tabData = mutableListOf<Fragment>(androidFragment, iosFragment, uiFragment)
        val gankTabAdapter = GankTabAdapter(childFragmentManager, tabData)
        mViewPager.adapter = gankTabAdapter
        // 设置默认的缓存个数
        mViewPager.offscreenPageLimit = GankConfig.TAB_TITLE.size
        mTabLayout.setupWithViewPager(mViewPager)
    }

}