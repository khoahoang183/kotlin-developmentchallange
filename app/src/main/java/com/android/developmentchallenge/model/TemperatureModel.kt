package com.android.developmentchallenge.model

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

data class TemperatureModel(
    var day: Float? = null,
    var min: Float? = null,
    var max: Float? = null,
    var night: Float? = null,
    var eve: Float? = null,
    var morn: Float? = null,
) {
}