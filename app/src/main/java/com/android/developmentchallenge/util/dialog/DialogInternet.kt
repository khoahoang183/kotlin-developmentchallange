package com.android.developmentchallenge.util.dialog

import android.app.Activity
import android.view.View
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.view.BaseDialog
import com.android.developmentchallenge.databinding.DialogInternetBinding

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class DialogInternet(context: Activity) :
    BaseDialog<DialogInternetBinding>(R.layout.dialog_internet, context) {

    /**
     * Show dialog with two button
     * @param isShowTitle show title or not
     * @param callBackOk is call back when click button ok
     */
    fun showDialogInternet(
        isShowTitle: Boolean = false,
        callBackOk: () -> Unit
    ) {

        if (isShowTitle) {
            this@DialogInternet.dataBinding.textTitle.visibility = View.VISIBLE
        } else {
            this@DialogInternet.dataBinding.textTitle.visibility = View.GONE
        }

        this@DialogInternet.alertDialog?.show()
        this@DialogInternet.setUpWidthOfDialogWhenShowing()

        // Button ok
        this@DialogInternet.dataBinding.buttonOk.setOnClickListener {
            this@DialogInternet.dismissInternetDialog()
            callBackOk.invoke()
        }
    }

    /**
     * Dismiss dialog
     */
    fun dismissInternetDialog() {
        this@DialogInternet.alertDialog?.dismiss()
    }
}