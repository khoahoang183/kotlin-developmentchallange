package com.android.developmentchallenge.model

import com.android.developmentchallenge.base.model.BaseModel

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

data class CityModel(
    var name: String? = null, // Ho Chi Minh city
    var coord: CityCoordinateModel? = null, // Ho Chi Minh city
    var country: String? = null, // VN
    var population: Long? = null,
    var timezone: Long? = null,
) : BaseModel() {
}


