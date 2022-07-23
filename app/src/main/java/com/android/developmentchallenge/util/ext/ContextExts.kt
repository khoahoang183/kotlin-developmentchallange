package com.android.developmentchallenge.util.ext

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

/**
 * Find LifecycleOwner of context if need
 */
fun Context.lifecycle(): Lifecycle? {
    val curContext = this
    return if (curContext is AppCompatActivity) {
        (curContext).lifecycle
    } else null
}

/**
 * Find lifecycle form context
 */
fun Context.lifecycleOwner(): LifecycleOwner? {
    var curContext = this
    var maxDepth = 20
    while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
        curContext = (curContext as ContextWrapper).baseContext
    }
    return if (curContext is LifecycleOwner) {
        curContext
    } else {
        null
    }
}