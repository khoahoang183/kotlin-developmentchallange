package com.android.developmentchallenge.model

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
data class ForecastModel(
    var dt: Long? = null,
    var sunrise: Long? = null,
    var sunset: Long? = null,
    var temp: TemperatureModel? = null,
    var feels_like: TemperatureModel? = null,
    var pressure: Long? = null,
    var humidity: Long? = null,
    var weather: ArrayList<WeatherModel>? = null,
    var speed: Float? = null,
    var deg: Long? = null,
    var gust: Float? = null,
    var clouds: Long? = null,
    var pop: Float? = null,
    var rain: Float? = null,
) {
}