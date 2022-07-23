package com.android.developmentchallenge.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.view.BaseActivity
import com.android.developmentchallenge.const.DURATION_HANDLER_POST_DELAYED
import com.android.developmentchallenge.const.DURATION_SPLASH_SCREEN
import com.android.developmentchallenge.databinding.ActivitySplashScreenBinding
import com.android.developmentchallenge.singleton.ApiRest
import com.android.developmentchallenge.singleton.AppSingletonData
import com.android.developmentchallenge.view.home.HomeActivity
import com.android.developmentchallenge.viewmodel.splash.SplashActivityViewModel

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity: BaseActivity<ActivitySplashScreenBinding, SplashActivityViewModel>(R.layout.activity_splash_screen) {

    override fun initCreateDone() {
        // Config view model
        this@SplashActivity.dataBinding.viewModel = this@SplashActivity.viewModel

        configSplashStep()
    }

    /**
     * configSplashStep
     *
     */
    private fun configSplashStep() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                this@SplashActivity.finish()
            },
            DURATION_SPLASH_SCREEN
        )
    }
}