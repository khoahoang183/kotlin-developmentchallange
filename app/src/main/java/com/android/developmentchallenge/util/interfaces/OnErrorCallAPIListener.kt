package com.android.developmentchallenge.util.interfaces

import androidx.annotation.StringRes

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

interface OnErrorCallAPIListener {
    fun onErrorThrow(error: Throwable)
    fun onErrorString(error: String)
    fun onErrorInt(@StringRes error: Int)
}