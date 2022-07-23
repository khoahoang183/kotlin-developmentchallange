package com.android.developmentchallenge.util.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.developmentchallenge.R
import com.android.developmentchallenge.const.LOG_EXCEPTION_PATTERN
import com.android.developmentchallenge.util.ext.convertFahrenheitToCelsius
import com.android.developmentchallenge.util.ext.convertKelvinToCelsius
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
@BindingAdapter("bindingIsVisibleNoDataText")
fun TextView.bindingIsVisibleNoDataText(listSize: Int) {
    this.visibility = if (listSize <= 0) View.VISIBLE else View.GONE
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("bindingLongToDateTimeInput", "bindingLongToDateTimePattern", requireAll = true)
fun TextView.bindingLongToDateTime(
    bindingLongToDateTimeInput: Long,
    bindingLongToDateTimePattern: String?,
) {
    try {
        val pattern = bindingLongToDateTimePattern ?: "EEE, dd LLL YYYY"
        val date = Date(bindingLongToDateTimeInput)
        val format = SimpleDateFormat(pattern)
        this.text = format.format(date)
    } catch (e: Exception) {
        Timber.e(
            LOG_EXCEPTION_PATTERN.format(
                this::class.simpleName,
                ::bindingLongToDateTime.name,
                e.message
            )
        )
        this.text = ""
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindingHumidityText")
fun TextView.bindingHumidityText(
    humidityPercent: Long
) {
    try {
        this.text =
            this.context.getString(R.string.home_humidity_sample).format(humidityPercent.toString())
    } catch (e: Exception) {
        Timber.e(
            LOG_EXCEPTION_PATTERN.format(
                this::class.simpleName,
                ::bindingHumidityText.name,
                e.message
            )
        )
        this.text = ""
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindingCelsiusText")
fun TextView.bindingCelsiusText(kelvin: Float) {
    try {
        val celsiusValue = convertKelvinToCelsius(kelvin)
        this.text =
            this.context.getString(R.string.home_humidity_temperature)
                .format(celsiusValue.toString())
    } catch (e: Exception) {
        Timber.e(
            LOG_EXCEPTION_PATTERN.format(
                this::class.simpleName,
                ::bindingHumidityText.name,
                e.message
            )
        )
        this.text = ""
    }
}




