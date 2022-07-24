package com.android.developmentchallenge.view.home


import com.android.developmentchallenge.base.BaseKoinTest
import com.android.developmentchallenge.model.DailyForecastModel
import com.android.developmentchallenge.network.service.WeatherService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.*
import org.koin.test.inject

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
class HomeActivityUnitTest : BaseKoinTest() {

    // inject weatherService
    private val weatherService by inject<WeatherService>()

    // get Disposable DailyForecast of weatherService
    private fun getDisposableDailyForecast(cityName: String): Disposable {
        return weatherService.getDailyForecast(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { }
            .doOnTerminate { }
            .subscribe({
                assert(true)
            }, {
                assert(false)
            })
    }

    @Test
    fun `call API getDailyForecast with empty value`() {
        compositeDisposable.add(
            getDisposableDailyForecast(""), // empty value case
        )
    }

    @Test
    fun `call API getDailyForecast with limit length value`() {
        compositeDisposable.add(
            getDisposableDailyForecast ("sa"), // limit length value case
        )
    }

    @Test
    fun `call API getDailyForecast with normal value`() {
        compositeDisposable.add(
            getDisposableDailyForecast ("saigon"), // limit length value case
        )
    }

    @Test
    fun `call API getDailyForecast with long value`() {
        compositeDisposable.add(
            getDisposableDailyForecast ("sai gon ha noi city in Viet Nam"), // limit length value case
        )
    }
}