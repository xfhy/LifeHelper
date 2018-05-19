package com.xfhy.weather.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tbruyelle.rxpermissions2.RxPermissions
import com.xfhy.library.basekit.fragment.BaseMvpFragment
import com.xfhy.library.utils.DevicesUtils
import com.xfhy.library.utils.GlideUtils
import com.xfhy.weather.R
import com.xfhy.weather.data.bean.WeatherBean
import com.xfhy.weather.presenter.WeatherContract
import com.xfhy.weather.presenter.impl.WeatherPresenter
import com.xfhy.library.utils.IntentUtils
import com.xfhy.library.utils.SnackbarUtil
import com.xfhy.weather.adapter.DailyWeatherAdapter
import com.xfhy.weather.adapter.HourWeatherAdapter
import com.xfhy.weather.data.WeatherDataManager
import kotlinx.android.synthetic.main.weather_fragment_main.*
import kotlinx.android.synthetic.main.weather_layout_aqi.*
import kotlinx.android.synthetic.main.weather_layout_life_advice.*


/**
 * Created by xfhy on 2018/3/22 12:39
 * Description : 天气fragment
 */
class WeatherFragment : BaseMvpFragment<WeatherPresenter>(), WeatherContract.View, SwipeRefreshLayout.OnRefreshListener {

    private val mHourWeatherAdapter by lazy {
        HourWeatherAdapter(null)
    }
    private val mDailyWeatherAdapter by lazy {
        DailyWeatherAdapter(null)
    }

    companion object {

        fun newInstance(): WeatherFragment {

            val args = Bundle()

            val fragment = WeatherFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun getLayoutResId(): Int {
        return R.layout.weather_fragment_main
    }

    override fun initPresenter() {
        mPresenter = WeatherPresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        //下拉刷新颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mHourWeatherRv.adapter = mHourWeatherAdapter

        //垂直滑动会和ScrollView起冲突   直接禁止RecyclerView的垂直滑动
        val layoutManager = object : LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        mDailyWeatherRv.layoutManager = layoutManager
        mDailyWeatherRv.adapter = mDailyWeatherAdapter

        //一来   就直接请求权限
        activity?.let {
            RxPermissions(it).request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe { granted ->
                if (granted) {    //6.0之前的总是true?   小米的不是,vivo的不是
                    showLoading()
                    //获取到权限  去定位当前位置
                    mPresenter?.locateCurrentLocation(context)
                } else {
                    //未获取到权限   让用户自己去授权
                    SnackbarUtil.showBarLongTime(mWeatherLayout,
                            "您未授予定位权限,APP无法精确定位",
                            text = "去授权",
                            listener = View.OnClickListener {
                                IntentUtils.goAppSetting(context, true)
                            })
                }
            }
        }
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun lazyLoad() {
        super.lazyLoad()
        mPresenter?.reqBingImage()
    }

    @SuppressLint("SetTextI18n", "Range")
    override fun showWeatherData(weatherData: WeatherBean.Result?) {
        closeRefresh()
        mLocateCityTv.text = weatherData?.city
        mCurrentTempTv.text = "${weatherData?.temp}°C"
        mWeatherTv.text = weatherData?.weather
        mLowHighTempTv.text = "${weatherData?.temphigh} / ${weatherData?.templow}"
        mAirQualityTv.text = weatherData?.aqi?.quality
        mHourWeatherAdapter.replaceData(weatherData?.hourly)
        mDailyWeatherAdapter.replaceData(weatherData?.daily)
        mAqiTv.text = weatherData?.aqi?.aqi
        mAqiTv.setTextColor(Color.parseColor(weatherData?.aqi?.aqiinfo?.color))
        mPM25Tv.text = weatherData?.aqi?.pm25
        mAffectTv.text = "健康:${weatherData?.aqi?.aqiinfo?.affect}"
        mMeasureTv.text = "建议:${weatherData?.aqi?.aqiinfo?.measure}"
        weatherData?.index?.forEachIndexed { index, indexItem ->
            when (index) {
                0 -> {
                    mAirConditioningTv.text = "${indexItem.iname}: ${indexItem.detail}"
                }
                1 -> {
                    mSportIndexTv.text = "${indexItem.iname}: ${indexItem.detail}"
                }
                2 -> {
                    mUVRaysIndexTv.text = "${indexItem.iname}: ${indexItem.detail}"
                }
                3 -> {
                    mColdIndexTv.text = "${indexItem.iname}: ${indexItem.detail}"
                }
                4 -> {
                    mCarWashIndexTv.text = "${indexItem.iname}: ${indexItem.detail}"
                }
                5 -> {
                    mAirPollutionIndexTv.text = "${indexItem.iname}: ${indexItem.detail}"
                }
                6 -> {
                    mDressingIndexTv.text = "${indexItem.iname}: ${indexItem.detail}"
                }
            }
        }

    }

    override fun showNoGPSNote() {
        SnackbarUtil.showBarLongTime(mSwipeRefreshLayout, message = "GPS好像没有打开哟",
                text = "去打开", listener = View.OnClickListener {
            IntentUtils.goAppSetting(context, true)
        })
    }

    override fun showBingImageBg(url: String) {
        GlideUtils.loadCustomImageNoPlace(mWeatherBgIv, url, mWeatherBgIv)
    }

    override fun showEmptyView() {
    }

    override fun showOffline() {
    }

    override fun showContent() {
        closeRefresh()
    }

    override fun onRefresh() {
        mPresenter?.refreshData()
    }

    fun closeRefresh() {
        mSwipeRefreshLayout?.isRefreshing = false
    }

}