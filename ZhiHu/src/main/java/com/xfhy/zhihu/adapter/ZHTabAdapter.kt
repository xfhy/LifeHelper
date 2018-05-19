package com.xfhy.zhihu.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * author xfhy
 * create at 2018年3月10日16:44:10
 * description：知乎首页顶部分页
 */
class ZHTabAdapter(fm: FragmentManager,
                   /**
                    * 用来盛放Fragment列表
                    */
                   private val fragmentList: List<Fragment>?) : FragmentPagerAdapter(fm) {

    init {
        if (fragmentList == null) {
            throw IllegalArgumentException("知乎模块tab不能为空")
        }
    }

    override fun getItem(position: Int): Fragment? {
        return fragmentList?.get(position)
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> TAB_TITLES[0]
            1 -> TAB_TITLES[1]
            2 -> TAB_TITLES[2]
            3 -> TAB_TITLES[3]
            else -> TAB_TITLES[0]
        }
    }

    companion object {

        /**
         * 知乎的标题栏是直接定了的
         */
        private val TAB_TITLES = arrayOf("日报", "主题", "专栏", "热门")
    }
}
