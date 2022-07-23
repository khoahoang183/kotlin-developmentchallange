package com.android.developmentchallenge.model

import androidx.room.Entity
import com.android.developmentchallenge.base.model.BaseModel

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

@Entity(tableName = "daily_forecast_table")
data class DailyForecastModel(
    var city: CityModel? = null,
    var cod: String? = null,
    var message: Float? = null,
    var cnt: Long? = null,
    var list: ArrayList<ForecastModel>? = null,
) : BaseModel()