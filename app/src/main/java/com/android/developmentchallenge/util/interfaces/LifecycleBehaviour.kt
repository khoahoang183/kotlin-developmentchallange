package com.android.developmentchallenge.util.interfaces

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import timber.log.Timber

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

interface LifecycleBehaviour : DefaultLifecycleObserver {
    // Local lifecycle
    var lifecycle: Lifecycle?

    /**
     * Attack to lifecycle
     *
     * @param lifecycle
     */
    fun bind(lifecycle: Lifecycle?) {
        if (this@LifecycleBehaviour.lifecycle != null) {
            return
        }

        this@LifecycleBehaviour.lifecycle = lifecycle
        this@LifecycleBehaviour.lifecycle?.addObserver(this@LifecycleBehaviour)
        Timber.e("${this@LifecycleBehaviour::class.java.simpleName} bind Complete")
    }

    /**
     * Un bind when lifecycle is destroy
     */
    fun unbindIfNeeded() {
        this@LifecycleBehaviour.lifecycle?.removeObserver(this@LifecycleBehaviour)
        this@LifecycleBehaviour.lifecycle = null
    }

    /**
     * When lifecycle is destroy
     */
    fun lifecycleOnDestroy() {
        this@LifecycleBehaviour.unbindIfNeeded()
        Timber.e("${this@LifecycleBehaviour::class.java.simpleName} onDestroy")
    }
}