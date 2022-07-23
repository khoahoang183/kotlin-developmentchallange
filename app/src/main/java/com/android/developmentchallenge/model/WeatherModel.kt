package com.android.developmentchallenge.model

import com.android.developmentchallenge.base.model.BaseModel

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

data class WeatherModel(
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null,
):BaseModel() {
}