package com.android.developmentchallenge.util.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.view.View
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.view.BaseDialog
import com.android.developmentchallenge.databinding.DialogLoadingBinding

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class DialogLoading(private val context: Activity) :
    BaseDialog<DialogLoadingBinding>(R.layout.dialog_loading, context) {
    // Current status of dialog
    var currentStatus = 0

    /**
     * Show progress dialog
     * @param contentText is string to view in bottom progress
     */
    fun showProgressDialog(contentText: String?) {
        // Start Animation
        val animationDrawable =
            (this@DialogLoading.dataBinding.spinnerImageView.background as AnimationDrawable)
        animationDrawable.start()

        // Need reset progress first
        this@DialogLoading.currentStatus = 0
        // Check to view text or not
        if (contentText.isNullOrEmpty()) {
            this@DialogLoading.dataBinding.viewContent.visibility = View.GONE
        } else {
            this@DialogLoading.dataBinding.viewContent.text = contentText
            this@DialogLoading.dataBinding.viewContent.visibility = View.VISIBLE
        }

        if (!context.isFinishing) {
            this@DialogLoading.alertDialog?.show()
            this@DialogLoading.setUpWidthOfDialogWhenShowing()
        }
    }

    /**
     * Update progress if need
     */
    @SuppressLint("SetTextI18n")
    fun updateStatusProgress(status: Int) {
        if (status > currentStatus) {
            this@DialogLoading.currentStatus = status
            if (this@DialogLoading.alertDialog?.isShowing!!) {
                this@DialogLoading.dataBinding.viewContent.text = "$status%"
                this@DialogLoading.dataBinding.viewContent.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Dismiss dialog
     */
    fun dismissLoadingDialog() {
        this@DialogLoading.alertDialog?.dismiss()
    }
}