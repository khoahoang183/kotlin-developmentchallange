package com.android.developmentchallenge.util.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

@BindingAdapter("bindingIsVisible")
fun ImageView.bindingIsVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}



