package com.android.developmentchallenge.const

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

const val DEFAULT_NUMBER_FORECAST_DAYS: Long = 7

const val LOG_EXCEPTION_PATTERN = "Author: Khoa Hoang Dang (Steve) - %s - %s - %s"

// default format when save data to DB
const val DATE_LOCAL_EFFECT_FORMAT = "yyyy-MM-dd"

enum class EnumVoiceRecognizeResult(val value: String) {
    RESULT_REFRESH("refresh"),
    RESULT_CLEAR("clear"),
}
