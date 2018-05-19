package com.xfhy.weather.common

/**
 * Created by xfhy on 2018/3/22 20:11
 * Description : 天气 工具
 */
object WeatherUtil {

    private val HEWEATHER_BASE_ICON = "https://www.heweather.com/files/images/cond_icon/"

    //https://www.heweather.com/files/images/cond_icon/100.png
    /*
    100	晴	Sunny/Clear	100.png
    101	多云	Cloudy	101.png
    102	少云	Few Clouds	102.png
    103	晴间多云	Partly Cloudy	103.png
    104	阴	Overcast	104.png
    200	有风	Windy	200.png
    201	平静	Calm	201.png
    202	微风	Light Breeze	202.png
    203	和风	Moderate/Gentle Breeze	203.png
    204	清风	Fresh Breeze	204.png
    205	强风/劲风	Strong Breeze	205.png
    206	疾风	High Wind, Near Gale	206.png
    207	大风	Gale	207.png
    208	烈风	Strong Gale	208.png
    209	风暴	Storm	209.png
    210	狂爆风	Violent Storm	210.png
    211	飓风	Hurricane	211.png
    212	龙卷风	Tornado	212.png
    213	热带风暴	Tropical Storm	213.png
    300	阵雨	Shower Rain	300.png
    301	强阵雨	Heavy Shower Rain	301.png
    302	雷阵雨	Thundershower	302.png
    303	强雷阵雨	Heavy Thunderstorm	303.png
    304	雷阵雨伴有冰雹	Hail	304.png
    305	小雨	Light Rain	305.png
    306	中雨	Moderate Rain	306.png
    307	大雨	Heavy Rain	307.png
    308	极端降雨	Extreme Rain	308.png
    309	毛毛雨/细雨	Drizzle Rain	309.png
    310	暴雨	Storm	310.png
    311	大暴雨	Heavy Storm	311.png
    312	特大暴雨	Severe Storm	312.png
    313	冻雨	Freezing Rain	313.png
    400	小雪	Light Snow	400.png
    401	中雪	Moderate Snow	401.png
    402	大雪	Heavy Snow	402.png
    403	暴雪	Snowstorm	403.png
    404	雨夹雪	Sleet	404.png
    405	雨雪天气	Rain And Snow	405.png
    406	阵雨夹雪	Shower Snow	406.png
    407	阵雪	Snow Flurry	407.png
    500	薄雾	Mist	500.png
    501	雾	Foggy	501.png
    502	霾	Haze	502.png
    503	扬沙	Sand	503.png
    504	浮尘	Dust	504.png
    507	沙尘暴	Duststorm	507.png
    508	强沙尘暴	Sandstorm	508.png
    900	热	Hot	900.png
    901	冷	Cold	901.png
    999	未知	Unknown	999.png
    * */

    fun getImageUrlByWeatherId(id: String) = when (id) {
        "0" -> "${HEWEATHER_BASE_ICON}100.png"
        "1" -> "${HEWEATHER_BASE_ICON}101.png"
        "2" -> "${HEWEATHER_BASE_ICON}104.png"
        "3" -> "${HEWEATHER_BASE_ICON}300.png"
        "4" -> "${HEWEATHER_BASE_ICON}302.png"
        "5" -> "${HEWEATHER_BASE_ICON}304.png"
        "6" -> "${HEWEATHER_BASE_ICON}404.png"
        "7" -> "${HEWEATHER_BASE_ICON}305.png"
        "8" -> "${HEWEATHER_BASE_ICON}306.png"
        "9" -> "${HEWEATHER_BASE_ICON}307.png"
        "10" -> "${HEWEATHER_BASE_ICON}310.png"
        "11" -> "${HEWEATHER_BASE_ICON}311.png"
        "12" -> "${HEWEATHER_BASE_ICON}312.png"
        "13" -> "${HEWEATHER_BASE_ICON}407.png"
        "14" -> "${HEWEATHER_BASE_ICON}400.png"
        "15" -> "${HEWEATHER_BASE_ICON}401.png"
        "16" -> "${HEWEATHER_BASE_ICON}402.png"
        "17" -> "${HEWEATHER_BASE_ICON}403.png"
        "18" -> "${HEWEATHER_BASE_ICON}501.png"
        "19" -> "${HEWEATHER_BASE_ICON}313.png"
        "20" -> "${HEWEATHER_BASE_ICON}507.png"
        "21" -> "${HEWEATHER_BASE_ICON}305.png"
        "22" -> "${HEWEATHER_BASE_ICON}306.png"
        "23" -> "${HEWEATHER_BASE_ICON}307.png"
        "24" -> "${HEWEATHER_BASE_ICON}310.png"
        "25" -> "${HEWEATHER_BASE_ICON}311.png"
        "26" -> "${HEWEATHER_BASE_ICON}400.png"
        "27" -> "${HEWEATHER_BASE_ICON}401.png"
        "28" -> "${HEWEATHER_BASE_ICON}402.png"
        "29" -> "${HEWEATHER_BASE_ICON}504.png"
        "30" -> "${HEWEATHER_BASE_ICON}503.png"
        "31" -> "${HEWEATHER_BASE_ICON}508.png"
        "32" -> "${HEWEATHER_BASE_ICON}501.png"
        "49" -> "${HEWEATHER_BASE_ICON}502.png"
        "53" -> "${HEWEATHER_BASE_ICON}502.png"
        "54" -> "${HEWEATHER_BASE_ICON}502.png"
        "55" -> "${HEWEATHER_BASE_ICON}502.png"
        "56" -> "${HEWEATHER_BASE_ICON}502.png"
        "57" -> "${HEWEATHER_BASE_ICON}501.png"
        "58" -> "${HEWEATHER_BASE_ICON}501.png"
        "99" -> "${HEWEATHER_BASE_ICON}999.png"
        "301" -> "${HEWEATHER_BASE_ICON}305.png"
        "302" -> "${HEWEATHER_BASE_ICON}400.png"
        else -> "${HEWEATHER_BASE_ICON}999.png"
    }

}