package com.android.developmentchallenge.base.model

import com.google.gson.annotations.SerializedName

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

abstract class BaseResponseModel<T> {
    @SerializedName("data")
    var result: T? = null

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("message")
    var message: String? = null
}