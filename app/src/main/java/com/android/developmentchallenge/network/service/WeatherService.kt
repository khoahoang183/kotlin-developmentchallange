package com.android.developmentchallenge.network.service

import com.android.developmentchallenge.const.APPLICATION_ID
import com.android.developmentchallenge.const.DEFAULT_NUMBER_FORECAST_DAYS
import com.android.developmentchallenge.model.DailyForecastModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

interface WeatherService {

    /**
     * Get Fore cast
     */
    @GET("data/2.5/forecast/daily")
    fun getDailyForecast(
        @Query("q") cityName: String,
        @Query("cnt") numberForecastDays: Long = DEFAULT_NUMBER_FORECAST_DAYS,
        @Query("appid") appid: String = APPLICATION_ID,
        @Query("units") units: String? = null,
    ): Observable<DailyForecastModel>
}