package com.xfhy.joke.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.xfhy.joke.R
import com.xfhy.joke.adapter.JokeTabAdapter
import com.xfhy.library.basekit.fragment.BaseFragment
import kotlinx.android.synthetic.main.joke_fragment_main.*

/**
 * Created by xfhy on 2018/3/20 23:03
 * Description : 内涵段子 主页
 */
class JokeMainFragment : BaseFragment() {

    companion object {

        fun newInstance(): JokeMainFragment {

            val args = Bundle()

            val fragment = JokeMainFragment()
            fragment.arguments = args
            return fragment
        }

    }

    private val jokeFragment: JokeConnotaFragment by lazy {
        JokeConnotaFragment.newInstance()
    }
    private val videoFragment: JokeVideoFragment by lazy {
        JokeVideoFragment.newInstance()
    }

    override fun getLayoutResId(): Int {
        return R.layout.joke_fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        val tabData = mutableListOf<Fragment>(jokeFragment, videoFragment)
        val gankTabAdapter = JokeTabAdapter(childFragmentManager, tabData)
        mViewPager.adapter = gankTabAdapter
        // 设置默认的缓存个数
        mViewPager.offscreenPageLimit = JokeTabAdapter.TAB_TITLE.size
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun onBackPress(): Boolean {
        return videoFragment.onBackPress()
    }

}