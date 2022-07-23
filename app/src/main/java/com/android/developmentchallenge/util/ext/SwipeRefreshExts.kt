package com.android.developmentchallenge.util.ext

import androidx.annotation.ColorRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.developmentchallenge.const.SWIPE_DEFAULT_COLOR_SCHEME
import com.android.developmentchallenge.const.SWIPE_DISTANCE_TRIGGER_SYNC

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

/**
 * Enable button click
 */
fun SwipeRefreshLayout.setupSwipeRefresh(
    @ColorRes colorId: Int = SWIPE_DEFAULT_COLOR_SCHEME,
    distanceTrigger: Int = SWIPE_DISTANCE_TRIGGER_SYNC
) {
    this.setColorSchemeResources(colorId)
    this.setDistanceToTriggerSync(distanceTrigger)
}