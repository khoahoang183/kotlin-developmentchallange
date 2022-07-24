package com.android.developmentchallenge.app

import android.app.Application
import com.android.developmentchallenge.BuildConfig
import com.android.developmentchallenge.BuildConfig.BASE_URL
import com.android.developmentchallenge.di.apiModule
import com.android.developmentchallenge.di.databaseModule
import com.android.developmentchallenge.di.singletonModule
import com.android.developmentchallenge.di.viewModelModule
import com.android.developmentchallenge.singleton.ApiRest
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class DevChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
        setupApiRest()
    }

    /**
     * Setup Module for inject
     */
    private fun setupKoin() {
        startKoin {
            androidContext(this@DevChallengeApplication)
            modules(
                apiModule,
                databaseModule,
                singletonModule,
                viewModelModule
            )
        }
    }

    /**
     * Setup log by timber
     */
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupApiRest(){

        // Set content type
        ApiRest.updateAdditionalHeaders(mapOf("Content-Type" to "application/json"))

        // Need config base url first
        ApiRest.updateAPIBaseURL(BuildConfig.BASE_URL)

    }

}