package com.android.developmentchallenge.di
import com.android.developmentchallenge.viewmodel.home.*
import com.android.developmentchallenge.viewmodel.splash.SplashActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

/**
 * ViewModel injection
 */
val viewModelModule = module {
    viewModel { SplashActivityViewModel() }
    viewModel { HomeActivityViewModel(get()) }
}