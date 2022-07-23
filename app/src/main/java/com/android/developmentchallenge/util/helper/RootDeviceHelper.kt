package com.android.developmentchallenge.util.helper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings.Secure
import java.io.File


/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
fun isRooted(context: Context): Boolean {
    val isEmulator = isEmulator(context)
    val buildTags = Build.TAGS
    return if (!isEmulator && buildTags != null && buildTags.contains("test-keys")) {
        true
    } else {
        var file = File("/system/app/DevChallenge.apk")
        if (file.exists()) {
            true
        } else {
            file = File("/system/xbin/su")
            !isEmulator && file.exists()
        }
    }
}

@SuppressLint("HardwareIds")
fun isEmulator(context: Context): Boolean {
    val androidId = Secure.getString(context.contentResolver, "android_id")
    return "sdk" == Build.PRODUCT || "google_sdk" == Build.PRODUCT || androidId == null
}

private fun executeShellCommand(command: String): Boolean {
    var process: Process? = null
    return try {
        process = Runtime.getRuntime().exec(command)
        true
    } catch (e: Exception) {
        false
    } finally {
        if (process != null) {
            try {
                process.destroy()
            } catch (e: Exception) {
            }
        }
    }
}