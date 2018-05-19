package com.xfhy.news.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.xfhy.library.basekit.fragment.BaseFragment
import com.xfhy.library.utils.SPUtils
import com.xfhy.news.R
import com.xfhy.news.activity.SortNewsActivity
import com.xfhy.news.adapter.NewsTabAdapter
import com.xfhy.news.config.NewsConfig
import com.xfhy.news.data.bean.NewsSortBean
import kotlinx.android.synthetic.main.news_fragment_main.*
import org.jetbrains.anko.collections.forEachReversedWithIndex
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by xfhy on 2018/3/18 13:28
 * Description : 新闻
 */
class NewsMainFragment : BaseFragment(), ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    companion object {
        fun newInstance(): NewsMainFragment {

            val args = Bundle()

            val fragment = NewsMainFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private var mNewsTabAdapter: NewsTabAdapter? = null
    private var mNewsSortList: MutableList<NewsSortBean>? = null

    /**
     * 获取tab数据
     */
    private var tabData: MutableList<Fragment> = MutableList(NewsConfig.NEWS_TYPE.size, { i ->
        NewsCommonFragment.newInstance(NewsConfig.NEWS_TYPE[i])
    })

    override fun getLayoutResId(): Int {
        return R.layout.news_fragment_main
    }

    override fun onResume() {
        super.onResume()
        readNewsSortRule()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mViewPager.addOnPageChangeListener(this)
        mSortTypeIv.setOnClickListener {
            startActivity<SortNewsActivity>()
        }
    }

    /**
     * 读取本地新闻排序数据
     */
    private fun readNewsSortRule() {
        //获取SP中数据并显示到RecyclerView上
        val newsSortConfig = SPUtils.getValue(NewsConfig.NEWS_SORT_CONFIG, "")
        mNewsSortList = Gson().fromJson(newsSortConfig, object : TypeToken<List<NewsSortBean>>() {}
                .type) as? MutableList<NewsSortBean>
        if (mNewsSortList != null && mNewsSortList?.size ?: 0 > 0) {
            tabData.clear()
            //未选中的全部移除
            mNewsSortList?.forEachReversedWithIndex { index, it ->
                if (!it.isChecked) {
                    mNewsSortList?.removeAt(index)
                }
            }
            //添加选中的fragment
            mNewsSortList?.forEach { it ->
                tabData.add(NewsCommonFragment.newInstance(it))
            }
            val newsTabAdapter = NewsTabAdapter(childFragmentManager, tabData, mNewsSortList)
            mViewPager.adapter = newsTabAdapter
            mTabLayout.setupWithViewPager(mViewPager)
        } else {
            mNewsTabAdapter = NewsTabAdapter(childFragmentManager, tabData, NewsConfig.NEWS_TYPE)
            mViewPager.adapter = mNewsTabAdapter
            mTabLayout.setupWithViewPager(mViewPager)
        }
        //添加tab 监听器
        mTabLayout.addOnTabSelectedListener(this)
        if (tabData.size > 0 && mNewsSortList != null) {
            val newsCommonFragment = tabData[0] as? NewsCommonFragment
            newsCommonFragment?.setNewsType(mNewsSortList?.get(0))
            //默认选中第一项
            mViewPager.currentItem = 0
            mTabLayout.getTabAt(0)?.select()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        //有时候不加载数据  设置一下可更保险   里面是做了防控的,避免2次加载
        val newsCommonFragment = tabData[position] as? NewsCommonFragment
        if (newsCommonFragment?.mNewsType == null && mNewsSortList != null) {
            newsCommonFragment?.setNewsType(mNewsSortList?.get(position))
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        //第一项有时候不加载数据  采取这种方式去加载一下
        tab?.position?.let {
            if (it == 0 && mNewsSortList != null) {
                val newsCommonFragment = tabData[tab.position] as? NewsCommonFragment
                if (newsCommonFragment?.mNewsType == null) {
                    newsCommonFragment?.setNewsType(mNewsSortList?.get(tab.position))
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
    }
}