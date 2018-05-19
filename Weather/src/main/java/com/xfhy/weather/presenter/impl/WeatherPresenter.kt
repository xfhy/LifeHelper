package com.xfhy.weather.presenter.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.xfhy.library.basekit.presenter.RxPresenter
import com.xfhy.library.rx.CommonSubscriber
import com.xfhy.library.rx.scheduler.SchedulerUtils
import com.xfhy.weather.data.WeatherDataManager
import com.xfhy.weather.data.bean.WeatherBean
import com.xfhy.weather.presenter.WeatherContract
import android.os.Bundle
import android.location.LocationListener
import android.location.LocationManager
import com.xfhy.provider.config.ApiConstants
import com.xfhy.weather.data.bean.BingImageBean


/**
 * Created by xfhy on 2018/3/22 12:47
 * Description : 天气Presenter
 */
class WeatherPresenter(val mView: WeatherContract.View) : RxPresenter(), WeatherContract.Presenter {

    /**
     * 纬度
     */
    var mLatitude = 0.0
    /**
     * 经度
     */
    var mLongitude = 0.0
    var isLoad = false //是否已经加载过
    private var mLocationManager: LocationManager? = null

    companion object {
        val MIN_TIME = 1000L
        val MIN_DISTANCE = 0.0f
    }

    override fun reqWeatherData(latitude: Double, longitude: Double) {
        isLoad = true
        addSubscribe(WeatherDataManager
                .getWeatherData("$latitude,$longitude")
                .compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<WeatherBean>(mView) {
                    override fun onNext(t: WeatherBean?) {
                        super.onNext(t)
                        if (t?.msg == "ok") {
                            mView.showWeatherData(t.result)
                        } else {
                            mView.showErrorMsg("加载数据失败")
                        }
                    }
                })
        )
    }

    @SuppressLint("MissingPermission")
    override fun locateCurrentLocation(context: Context?) {
        if (context == null) {
            return
        }
        mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        if (mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {  //从gps获取经纬度
            mLocationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE,
                    locationListener)
            val location = mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                mLatitude = location.latitude
                mLongitude = location.longitude
                if (!isLoad) {
                    reqWeatherData(mLatitude, mLongitude)
                }
            } else {
                //当GPS信号弱(获取到的位置信息是空的)  这个时候从网络获取位置
                getLocationFromNetwork()
            }
        } else {
            //从网络获取经纬度
            getLocationFromNetwork()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocationFromNetwork() {
        mLocationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE,
                locationListener)
        val location = mLocationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            mLatitude = location.latitude
            mLongitude = location.longitude
        } else {
            mView.showNoGPSNote()
        }
    }

    private var locationListener: LocationListener = object : LocationListener {

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onProviderDisabled(provider: String) {

        }

        override fun onLocationChanged(location: Location) {
            //第一次定位成功 则去加载数据   避免浪费
            if (!isLoad) {
                reqWeatherData(mLatitude, mLongitude)
            }
        }
    }

    override fun refreshData() {
        if (mLatitude == 0.0) {
            mView.showErrorMsg("未定位成功呢..别忙着刷新")
        }
        reqWeatherData(mLatitude, mLongitude)
    }

    override fun reqBingImage() {
        addSubscribe(WeatherDataManager.getBingDailyImage().compose(SchedulerUtils.ioToMain())
                .subscribeWith(object : CommonSubscriber<BingImageBean>(mView) {
                    override fun onNext(t: BingImageBean?) {
                        super.onNext(t)
                        t?.images?.let {
                            if (it.size > 0) {
                                mView.showBingImageBg("${ApiConstants.BING_BASE_URL}${it[0].urlbase}_1080x1920.jpg")
                            }
                        }
                    }
                }))
    }

    @SuppressLint("MissingPermission")
    override fun onDestroy() {
        super.onDestroy()
        //移除监听
        mLocationManager?.removeUpdates(locationListener)
    }

}