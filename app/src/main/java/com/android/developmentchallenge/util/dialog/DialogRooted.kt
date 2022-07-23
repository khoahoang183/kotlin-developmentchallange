package com.android.developmentchallenge.util.dialog

import android.app.Activity
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.view.BaseDialog
import com.android.developmentchallenge.databinding.DialogRootedBinding

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
class DialogRooted(private val context: Activity) :
    BaseDialog<DialogRootedBinding>(R.layout.dialog_rooted, context) {

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


        this@DialogRooted.dataBinding.textContent.text = contentText

        // Button text
        if (!textOk.isNullOrEmpty()) {
            this@DialogRooted.dataBinding.buttonOk.text = textOk
        }

        this@DialogRooted.alertDialog?.show()
        this@DialogRooted.setUpWidthOfDialogWhenShowing()

        // Button ok
        this@DialogRooted.dataBinding.buttonOk.setOnClickListener {
            this@DialogRooted.dismissErrorDialog()
            callBackConfirm.invoke()
        }
    }

    /**
     * Dismiss dialog
     */
    fun dismissErrorDialog() {
        this@DialogRooted.alertDialog?.dismiss()
    }
}