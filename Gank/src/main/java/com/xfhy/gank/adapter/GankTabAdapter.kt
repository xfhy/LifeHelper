package com.xfhy.gank.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.xfhy.gank.config.GankConfig

/**
 * Created by xfhy on 2018/3/20 13:23
 * Description : 干货 tab adapter
 */
class GankTabAdapter(fm: FragmentManager,
                     /**
                      * 用来盛放Fragment列表
                      */
                     private val fragmentList: List<Fragment>?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return fragmentList?.get(position)
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> GankConfig.TAB_TITLE[0]
            1 -> GankConfig.TAB_TITLE[1]
            2 -> GankConfig.TAB_TITLE[2]
            else -> GankConfig.TAB_TITLE[0]
        }
    }
}