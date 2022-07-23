package com.android.developmentchallenge.view.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.DataBindingUtil
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.BaseClassUnitTest
import com.android.developmentchallenge.databinding.ActivityHomeBinding
import com.android.developmentchallenge.model.DailyForecastModel
import com.android.developmentchallenge.network.response.ErrorResponse
import com.android.developmentchallenge.network.service.WeatherService
import com.android.developmentchallenge.viewmodel.home.HomeActivityViewModel
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import javax.xml.validation.Validator

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
class HomeActivityUnitTest : BaseClassUnitTest<ActivityHomeBinding, HomeActivityViewModel>() {
    override fun initializeData() {
        TODO("Not yet implemented")
    }

}