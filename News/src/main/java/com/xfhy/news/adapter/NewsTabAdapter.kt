package com.xfhy.news.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.xfhy.news.data.bean.NewsSortBean
import com.xfhy.news.fragment.NewsCommonFragment
import org.jetbrains.anko.collections.forEachByIndex
import org.jetbrains.anko.collections.forEachReversedByIndex


/**
 * Created by xfhy on 2018/3/18 17:08
 * Description : 新闻tab adapter
 */
class NewsTabAdapter(val fm: FragmentManager,
                     /**
                      * 用来盛放Fragment列表
                      */
                     private var fragmentList: MutableList<Fragment>?,
                     private var newsSortList: MutableList<NewsSortBean>?) : FragmentStatePagerAdapter(fm) {

    private val tags = mutableListOf<String>()

    override fun getItem(position: Int): Fragment? {
        return fragmentList?.get(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> newsSortList?.get(0)?.newsType
            1 -> newsSortList?.get(1)?.newsType
            2 -> newsSortList?.get(2)?.newsType
            3 -> newsSortList?.get(3)?.newsType
            4 -> newsSortList?.get(4)?.newsType
            5 -> newsSortList?.get(5)?.newsType
            6 -> newsSortList?.get(6)?.newsType
            7 -> newsSortList?.get(7)?.newsType
            8 -> newsSortList?.get(8)?.newsType
            9 -> newsSortList?.get(9)?.newsType
            10 -> newsSortList?.get(10)?.newsType
            11 -> newsSortList?.get(11)?.newsType
            12 -> newsSortList?.get(12)?.newsType
            13 -> newsSortList?.get(13)?.newsType
            else -> newsSortList?.get(0)?.newsType
        }
    }

    fun replaceData(newFragmentList: MutableList<Fragment>?, newSortList: MutableList<NewsSortBean>?) {

        //清除FragmentManager里面缓存的fragment  这样刷新才会有用
        fragmentList?.forEachReversedByIndex { it ->
            val ft = fm.beginTransaction()
            ft.remove(it)
            ft.commitNow()
        }

        newFragmentList?.forEachReversedByIndex { it ->
            val ft = fm.beginTransaction()
            ft.remove(it)
            ft.commitNow()
        }

        val fragmentTransaction = fm.beginTransaction()
        for (i in 0 until tags.size) {
            fragmentTransaction.remove(fm.findFragmentByTag(tags[i]))
        }
        fragmentTransaction.commit()
        fm.executePendingTransactions()
        tags.clear()

        fragmentList?.clear()
        newsSortList?.clear()
        newSortList?.let { newsSortList?.addAll(it) }
        newFragmentList?.forEachByIndex { it ->
            fragmentList?.add(it)
        }
        notifyDataSetChanged()
    }

}
