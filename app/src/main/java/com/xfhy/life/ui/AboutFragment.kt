package com.xfhy.life.ui

import android.os.Bundle
import com.xfhy.library.basekit.fragment.BaseFragment
import com.xfhy.life.R

/**
 * Created by xfhy on 2018/3/26 20:21
 * Description :关于
 */
class AboutFragment : BaseFragment() {

    companion object {

        fun newInstance(): AboutFragment {

            val args = Bundle()

            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_about
    }


}