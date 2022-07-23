package com.android.developmentchallenge.di

import com.android.developmentchallenge.network.service.WeatherService
import com.android.developmentchallenge.singleton.ApiRest
import org.koin.dsl.module

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

val apiModule = module {
    single { createWeatherService() }
}

// Create Weather Service
internal fun createWeatherService(): WeatherService = ApiRest[WeatherService::class.java]
