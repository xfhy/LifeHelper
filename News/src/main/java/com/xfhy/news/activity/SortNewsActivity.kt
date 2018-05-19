package com.xfhy.news.activity

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xfhy.library.basekit.activity.TitleBarActivity
import com.xfhy.library.utils.SPUtils
import com.xfhy.library.widgets.CustomDialog
import com.xfhy.library.widgets.DefaultItemTouchHelpCallback
import com.xfhy.news.R
import com.xfhy.news.adapter.NewsSortAdapter
import com.xfhy.news.config.NewsConfig
import com.xfhy.news.data.bean.NewsSortBean
import kotlinx.android.synthetic.main.news_activity_sort_news.*
import java.util.*

/**
 * Created by xfhy on 2018/3/19 14:27
 * Description : 用于管理新闻排序的
 */
class SortNewsActivity : TitleBarActivity() {

    private val mSortAdapter: NewsSortAdapter  by lazy { NewsSortAdapter(null) }
    /**
     * 老数据
     */
    private var mOldNewsSortList: MutableList<NewsSortBean>? = null

    override fun getThisTitle(): CharSequence {
        return "首页类别排序"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity_sort_news)

        initView()
    }

    private fun initView() {
        mNewsSortRv.adapter = mSortAdapter
        mNewsSortRv.setHasFixedSize(true)

        //获取SP中数据并显示到RecyclerView上
        val newsSortConfig = SPUtils.getValue(NewsConfig.NEWS_SORT_CONFIG, "")
        if (TextUtils.isEmpty(newsSortConfig)) {
            //如果SP中数据为空,则说明用户未配置排序顺序
            mSortAdapter.replaceData(NewsConfig.NEWS_TYPE)
        } else {
            mOldNewsSortList = Gson().fromJson(newsSortConfig, object : TypeToken<List<NewsSortBean>>() {}
                    .type) as? MutableList<NewsSortBean>
            //复制一份,专门用于显示,避免显示的数据与老数据引用是一样的
            val dest = MutableList(mOldNewsSortList?.size ?: 0, { _ -> NewsSortBean() })
            mOldNewsSortList?.forEachIndexed { index, newsSortBean ->
                dest[index].newsType = newsSortBean.newsType
                dest[index].isChecked = newsSortBean.isChecked
            }
            mSortAdapter.replaceData(dest)
        }

        //利用ItemTouchHelper绑定RecyclerView、ItemTouchHelper.Callback来实现UI更新，
        // 并且实现动态控制是否开启拖拽功能和滑动删除功能。
        val mCallback = DefaultItemTouchHelpCallback(object : DefaultItemTouchHelpCallback
        .OnItemTouchCallbackListener {
            override fun onSwiped(adapterPosition: Int) {
            }

            override fun onMove(srcPosition: Int, targetPosition: Int): Boolean {
                if (mSortAdapter.data != null) {
                    Collections.swap(mSortAdapter.data, srcPosition, targetPosition)
                    mSortAdapter.notifyItemMoved(srcPosition, targetPosition)
                    return true
                }
                return false
            }
        })
        mCallback.isCanDrag = true
        val itemTouchHelper = ItemTouchHelper(mCallback)
        itemTouchHelper.attachToRecyclerView(mNewsSortRv)

    }

    override fun onBackPressed() {
        //先判断数据是否发生变化
        val dataChanged = judgeDataChange()

        //数据发生变化
        if (dataChanged) {
            showSureDialog()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * 展示确认对话框
     */
    private fun showSureDialog() {
        //展示提示框 让用户保存
        val customDialog = CustomDialog(mContext, null, "确定保存?", "不保存", "确定", object : CustomDialog
        .OnCustomDialogListener {
            override fun onLeftClick() {
                finish()
            }

            override fun onRightClick() {
                saveSortDataToSp()
                finish()
            }
        })
        customDialog.show()
    }

    /**
     * 判断数据是否发生变化
     */
    private fun judgeDataChange(): Boolean {
        var dataChanged = false
        mOldNewsSortList?.forEachIndexed oldDataLoop@ { index, newsSortBean ->
            mSortAdapter.data?.get(index)?.let {
                //这里的!=已经进行了各个属性的比较  而不是简单进行对象的堆地址进行比较
                if (it != newsSortBean) {
                    dataChanged = true
                    return@oldDataLoop
                }
            }
        }
        //第一次进入,那么老数据为空   只需要判断新数据是否有 没有被选中的
        if (mOldNewsSortList == null) {
            mSortAdapter.data?.forEach adapterData@ {
                //这里的!=已经进行了各个属性的比较  而不是简单进行对象的堆地址进行比较
                if (!it.isChecked) {
                    dataChanged = true
                    return@adapterData
                }
            }
        }
        return dataChanged
    }

    override fun onBackClick() {
        //先判断数据是否发生变化
        val dataChanged = judgeDataChange()

        //数据发生变化
        if (dataChanged) {
            showSureDialog()
        } else {
            finish()
        }
    }

    /**
     * 保存数据到SharedPreferences中
     */
    private fun saveSortDataToSp() {
        val newsSortJson = Gson().toJson(mSortAdapter.data, object : TypeToken<List<NewsSortBean>>() {}.type)
        SPUtils.putValue(NewsConfig.NEWS_SORT_CONFIG, newsSortJson)
    }

}