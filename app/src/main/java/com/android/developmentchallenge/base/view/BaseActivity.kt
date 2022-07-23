package com.android.developmentchallenge.base.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.BuildConfig
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.viewmodel.BaseViewModel
import com.android.developmentchallenge.const.MAX_LIMIT_TRY_ERROR_INTERNET
import com.android.developmentchallenge.singleton.ApiRest
import com.android.developmentchallenge.util.ext.isOnline
import com.android.developmentchallenge.util.ext.setupUIHideKeyBoard
import com.android.developmentchallenge.util.helper.DialogHelper
import com.android.developmentchallenge.util.helper.PreferenceHelper
import com.android.developmentchallenge.util.helper.isRooted
import com.android.developmentchallenge.util.interfaces.OnCallAPIListener
import com.android.developmentchallenge.util.interfaces.OnErrorCallAPIListener
import com.android.developmentchallenge.util.interfaces.OnJWTTimeoutListener
import com.android.developmentchallenge.view.splash.SplashActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

abstract class BaseActivity<T : ViewDataBinding, K : BaseViewModel>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity(), OnCallAPIListener, OnErrorCallAPIListener, OnJWTTimeoutListener {

    // Data Binding
    lateinit var dataBinding: T

    // View model
    val viewModel: K by lazy {
        getViewModel(clazz = viewModelClass())
    }

    override fun onResume() {
        super.onResume()

        // Check device is rooted
        if (isRooted(this)) {
            this@BaseActivity.alertDialogHelper.dialogRooted.showErrorConfirm(
                contentText = getString(R.string.dialog_rooted),
                callBackConfirm = {finishAffinity()})
        }
    }

    /**
     * Get class type for view model
     */
    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<K> {
        // Dirty hack to get generic type https://stackoverflow.com/a/1901275/719212
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<K>).kotlin
    }

    // Max try to get data form server
    private var numberLimitTry = 0

    // Bundle data if need progress
    var bundleCreate: Bundle? = null

    // Get PreferenceHelper by di
    val preferenceHelper: PreferenceHelper by inject()

    // Method for call last when onCreate is complete
    abstract fun initCreateDone()

    // Flag check if user press back two time to exit
    var doubleBackToExitPressedOnce = false

    // Alert dialog helper
    val alertDialogHelper: DialogHelper by lazy {
        DialogHelper(this@BaseActivity)
    }

    // Event need override to progress logout and try to login
    open fun eventNeedToProgressKeyTimeout() {
        TODO("Implement if application have userRefreshTokenCache - based on brief")
    }

    // On create complete
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        this@BaseActivity.dataBinding = DataBindingUtil.setContentView(
            this@BaseActivity,
            layoutResId
        )

        // Config lifecycleOwner of viewmodel to activity
        //this@BaseActivity.dataBinding.lifecycleOwner = this@BaseActivity
        this@BaseActivity.dataBinding.lifecycleOwner = this@BaseActivity

        // Hide key Board
        setupUIHideKeyBoard(this@BaseActivity.dataBinding.root)

        // Config interface
        this@BaseActivity.viewModel.onLoadingDataListener = this@BaseActivity
        this@BaseActivity.viewModel.onErrorLoadListener = this@BaseActivity
        this@BaseActivity.viewModel.onJWTTimeoutListener = this@BaseActivity

        // Get bundle data
        if (intent != null) {
            this@BaseActivity.bundleCreate = intent.extras
        }

        Timber.e("Create Activity ${this@BaseActivity::class.java.simpleName} done")

        initCreateDone()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        this@BaseActivity.alertDialogHelper.lifecycleOnDestroy()
        Timber.e("Destroy Activity ${this@BaseActivity::class.java.simpleName}")
    }

    /**
     * Check internet connection
     * And set event re try for recall
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkInternetAndStartCallApi(callApi: () -> Unit) {
        if (isOnline()) {
            // Reset
            numberLimitTry = 0
            callApi.invoke()
        } else {
            // Count and check time is retry, if count > 3, stop load api
            numberLimitTry += 1
            if (numberLimitTry < MAX_LIMIT_TRY_ERROR_INTERNET) {
                this@BaseActivity.alertDialogHelper.dialogInternet.showDialogInternet(
                    isShowTitle = true,
                    callBackOk = {
                        checkInternetAndStartCallApi(callApi)
                    }
                )
            } else {
                // Show dialog and stop this
                this@BaseActivity.alertDialogHelper.dialogConfirm.showDialogOneButton(
                    contentText = getString(
                        R.string.dialog_common_error_try_too_much
                    ),
                    callBackConfirm = {
                        numberLimitTry = 0
                    }
                )
            }
        }
    }

    /**
     * Show dialog progress when call api
     */
    override fun onStartCallApi() {
        this@BaseActivity.alertDialogHelper.dialogLoading.showProgressDialog(
            contentText = getString(
                R.string.dialog_content_loading
            )
        )
    }

    /**
     * Stop show dialog progress
     */
    override fun onStopCallApi() {
        this@BaseActivity.alertDialogHelper.dialogLoading.dismissLoadingDialog()
    }

    /**
     * Update progress if need
     */
    override fun onUpdate(status: Int) {
        this@BaseActivity.alertDialogHelper.dialogLoading.updateStatusProgress(status)
    }

    /**
     * Show error with Throwable
     */
    override fun onErrorThrow(error: Throwable) {
        if (BuildConfig.DEBUG) {
            this@BaseActivity.alertDialogHelper.dialogError.showErrorConfirm(
                contentText = error.message.toString(),
                callBackConfirm = {})
        } else {
            this@BaseActivity.alertDialogHelper.dialogError.showErrorConfirm(
                contentText = getString(
                    R.string.dialog_common_unknown_error
                ), callBackConfirm = {})
        }
    }

    /**
     * Show error with string
     */
    override fun onErrorString(error: String) {
        this@BaseActivity.alertDialogHelper.dialogError.showErrorConfirm(
            error,
            callBackConfirm = {})
    }

    /**
     * Show error with res string
     */
    override fun onErrorInt(error: Int) {
        this@BaseActivity.alertDialogHelper.dialogError.showErrorConfirm(
            getString(error),
            callBackConfirm = {})
    }

    /**
     * Show dialog time out to need login
     */
    override fun onKeyJWTTimeout() {
        this@BaseActivity.alertDialogHelper.dialogError.showErrorConfirm(getString(R.string.dialog_common_error_key_time_out)) {
            eventNeedToProgressKeyTimeout()
        }
    }
}