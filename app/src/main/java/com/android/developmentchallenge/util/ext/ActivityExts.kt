package com.android.developmentchallenge.util.ext

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

/**
 * Setup hide keyboard
 */
@SuppressLint("ClickableViewAccessibility")
fun Activity.setupUIHideKeyBoard(view: View) {
    // Set up touch listener for non-text box views to hide keyboard.
    if (view !is AutoCompleteTextView) {
        view.setOnTouchListener { _, _ ->
            hideSoftKeyboard()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setupUIHideKeyBoard(innerView)
            innerView.clearFocus()
        }
    }
}

// Hide keyboard
fun Activity.hideSoftKeyboard() {
    try {
        val inputMethodManager = this.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (this.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                this.currentFocus!!.windowToken, 0
            )
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Check internet of device
 *
 * @return true if internet is available
 */
@RequiresApi(Build.VERSION_CODES.M)
fun Activity.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        } else {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        }

    return if (capabilities != null) {

        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    } else false
}