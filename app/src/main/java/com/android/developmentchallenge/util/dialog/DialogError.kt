package com.android.developmentchallenge.util.dialog

import android.app.Activity
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.view.BaseDialog
import com.android.developmentchallenge.databinding.DialogErrorBinding

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class DialogError(private val context: Activity) :
    BaseDialog<DialogErrorBinding>(R.layout.dialog_error, context) {

    /**
     * Show dialog with only one button ok
     * @param contentText is string to view message
     * @param textOk is string to view button ok
     * @param callBackConfirm is call back when click button ok
     */
    fun showErrorConfirm(
        contentText: String,
        textOk: String? = null,
        callBackConfirm: () -> Unit
    ) {


        this@DialogError.dataBinding.textContent.text = contentText

        // Button text
        if (!textOk.isNullOrEmpty()) {
            this@DialogError.dataBinding.buttonOk.text = textOk
        }

        this@DialogError.alertDialog?.show()
        this@DialogError.setUpWidthOfDialogWhenShowing()

        // Button ok
        this@DialogError.dataBinding.buttonOk.setOnClickListener {
            this@DialogError.dismissErrorDialog()
            callBackConfirm.invoke()
        }
    }

    /**
     * Dismiss dialog
     */
    fun dismissErrorDialog() {
        this@DialogError.alertDialog?.dismiss()
    }
}