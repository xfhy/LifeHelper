package com.xfhy.joke.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by xfhy on 2018/3/20 13:23
 * Description : 干货 tab adapter
 */
class JokeTabAdapter(fm: FragmentManager,
                     /**
                      * 用来盛放Fragment列表
                      */
                     private val fragmentList: List<Fragment>?) : FragmentPagerAdapter(fm) {

    companion object {
        val TAB_TITLE = arrayOf("段子", "视频")
    }

    override fun getItem(position: Int): Fragment? {
        return fragmentList?.get(position)
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> TAB_TITLE[0]
            1 -> TAB_TITLE[1]
            else -> TAB_TITLE[0]
        }
    }
}