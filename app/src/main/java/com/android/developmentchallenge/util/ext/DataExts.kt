package com.android.developmentchallenge.util.ext

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import kotlin.math.roundToInt

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */


fun convertFahrenheitToCelsius(fahrenheit: Float): Int {
    return ((fahrenheit - 32) * 0.5556).roundToInt()
}

fun convertCelsiusToFahrenheit(celsius: Float): Int {
    return (celsius + 273.15F).toInt()
}

fun convertKelvinToCelsius(kelvin: Float): Int {
    return (kelvin - 273.15F).toInt()
}
