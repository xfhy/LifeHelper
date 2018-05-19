package com.xfhy.zhihu.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import com.xfhy.library.basekit.fragment.BaseFragment
import com.xfhy.zhihu.R
import com.xfhy.zhihu.adapter.ZHTabAdapter
import kotlinx.android.synthetic.main.fragment_zh_main.*

import java.util.ArrayList

/**
 * author feiyang
 * create at 2018年3月10日16:45:49
 * description：知乎板块的主fragment
 */
class ZHMainFragment : BaseFragment() {

    companion object {

        private val PAGE_COUNT = 4

        fun newInstance(): ZHMainFragment {

            val args = Bundle()

            val fragment = ZHMainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * 获取tab数据
     */
    private val tabData: List<Fragment>
        get() {
            val fragmentList = ArrayList<Fragment>()
            val zhLatestDailyFragment = ZHLatestDailyFragment.newInstance()
            val zhThemeFragment = ZHThemeFragment.newInstance()
            val zhSectionFragment = ZHSectionFragment.newInstance()
            val zhHotFragment = ZHHotFragment.newInstance()

            fragmentList.add(zhLatestDailyFragment)
            fragmentList.add(zhThemeFragment)
            fragmentList.add(zhSectionFragment)
            fragmentList.add(zhHotFragment)
            return fragmentList
        }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_zh_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val zhihuTabAdapter = ZHTabAdapter(childFragmentManager, tabData)
        mViewPager.adapter = zhihuTabAdapter
        // 设置默认的缓存个数
        mViewPager.offscreenPageLimit = PAGE_COUNT
        mTabLayout.setupWithViewPager(mViewPager)
    }

}
