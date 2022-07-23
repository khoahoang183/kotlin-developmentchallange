package com.android.developmentchallenge.util.interfaces

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

interface OnCallAPIListener {
    fun onStartCallApi()
    fun onStopCallApi()
    fun onUpdate(status: Int)
}