package com.android.developmentchallenge.viewmodel.home

import androidx.lifecycle.MutableLiveData
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.viewmodel.BaseViewModel
import com.android.developmentchallenge.model.DailyForecastModel
import com.android.developmentchallenge.network.response.ErrorResponse
import com.android.developmentchallenge.network.service.WeatherService
import com.android.developmentchallenge.util.adapter.home.AdapterHomeForecast

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class HomeActivityViewModel(private val weatherService: WeatherService) : BaseViewModel() {

    // Adapter of forecast recyclerView
    var adapterHomeForecast = AdapterHomeForecast()

    var dailyForecastModel = MutableLiveData<DailyForecastModel?>()

    var toastMessCallback = MutableLiveData<String>()

    var lastStateSearch: String = ""

    fun getDailyForecast(cityName: String) {
        callApiMainThreadWithDialog<DailyForecastModel>(
            weatherService.getDailyForecast(
                cityName = cityName
            ),
            {
                it.let { model ->
                    dailyForecastModel.postValue(model)
                }
            }, {
                dailyForecastModel.postValue(null)
                val errorObject = handleErrorResponse<ErrorResponse>(it)
                if (errorObject != null) {
                    errorObject.error?.message?.let { message ->
                        onErrorLoadListener?.onErrorString(error = message)
                    }
                } else
                    onErrorLoadListener?.onErrorInt(R.string.dialog_common_unknown_error)
            })
    }
}