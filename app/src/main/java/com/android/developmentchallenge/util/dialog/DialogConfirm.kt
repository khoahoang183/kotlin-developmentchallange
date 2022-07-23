package com.android.developmentchallenge.util.dialog

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.view.BaseDialog
import com.android.developmentchallenge.databinding.DialogConfirmBinding

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class DialogConfirm(context: Activity) :
    BaseDialog<DialogConfirmBinding>(R.layout.dialog_confirm, context) {

    /**
     * Show dialog with only one button ok
     * @param contentText is string to view content
     * @param textOk is string to view button ok
     * @param callBackConfirm is call back when click button ok
     */
    fun showDialogOneButton(
        contentText: String,
        textOk: String? = null,
        callBackConfirm: () -> Unit
    ) {
        // Hide button cancel
        this@DialogConfirm.dataBinding.buttonCancel.visibility = View.GONE
        this@DialogConfirm.dataBinding.viewCenterButton.visibility = View.GONE

        this@DialogConfirm.dataBinding.textContent.text = contentText

        // Button text
        if (!textOk.isNullOrEmpty()) {
            this@DialogConfirm.dataBinding.buttonOk.text = textOk
        }

        this@DialogConfirm.alertDialog?.show()
        this@DialogConfirm.setUpWidthOfDialogWhenShowing()

        // Button ok
        this@DialogConfirm.dataBinding.buttonOk.setOnClickListener {
            this@DialogConfirm.dismissConfirmDialog()
            callBackConfirm.invoke()
        }
    }

    /**
     * Show dialog with only one button ok
     * And set text color is red
     * @param contentText is string to view content
     * @param textOk is string to view button ok
     * @param callBackConfirm is call back when click button ok
     */
    fun showDialogOneButtonRedText(
        contentText: String,
        textOk: String? = null,
        callBackConfirm: () -> Unit
    ) {
        // Hide button cancel
        this@DialogConfirm.dataBinding.buttonCancel.visibility = View.GONE
        this@DialogConfirm.dataBinding.viewCenterButton.visibility = View.GONE

        this@DialogConfirm.dataBinding.textContent.text = contentText

        // Button text
        if (!textOk.isNullOrEmpty()) {
            this@DialogConfirm.dataBinding.buttonOk.text = textOk
        }

        // Set new text color for button ok
        this@DialogConfirm.dataBinding.buttonOk.setTextColor(
            ContextCompat.getColor(
                this@DialogConfirm.dataBinding.buttonOk.context,
                R.color.color_text_red
            )
        )

        this@DialogConfirm.alertDialog?.show()
        this@DialogConfirm.setUpWidthOfDialogWhenShowing()

        // Button ok
        this@DialogConfirm.dataBinding.buttonOk.setOnClickListener {
            this@DialogConfirm.dismissConfirmDialog()
            callBackConfirm.invoke()
        }
    }

    /**
     * Show dialog with two button
     * @param textCancel is string to view button cancel
     * @param textOk is string to view button ok
     * @param contentText is string to view in bottom title
     * @param callBackOk is call back when click button ok
     * @param callBackCancel is call back when click button cancel
     */
    fun showConfirmTwoButton(
        contentText: String?,
        textCancel: String? = null,
        textOk: String? = null,
        callBackOk: () -> Unit,
        callBackCancel: () -> Unit
    ) {
        // Show button
        this@DialogConfirm.dataBinding.buttonCancel.visibility = View.VISIBLE
        this@DialogConfirm.dataBinding.viewCenterButton.visibility = View.VISIBLE

        // Content
        if (contentText.isNullOrEmpty()) {
            this@DialogConfirm.dataBinding.textContent.visibility = View.GONE
            this@DialogConfirm.dataBinding.textContent.text = contentText
        } else {
            this@DialogConfirm.dataBinding.textContent.text = contentText
            this@DialogConfirm.dataBinding.textContent.visibility = View.VISIBLE
        }

        // Button text
        if (!textCancel.isNullOrEmpty()) {
            this@DialogConfirm.dataBinding.buttonCancel.text = textCancel
        }
        if (!textOk.isNullOrEmpty()) {
            this@DialogConfirm.dataBinding.buttonOk.text = textOk
        }

        this@DialogConfirm.alertDialog?.show()
        this@DialogConfirm.setUpWidthOfDialogWhenShowing()

        // Button cancel
        this@DialogConfirm.dataBinding.buttonCancel.setOnClickListener {
            this@DialogConfirm.dismissConfirmDialog()
            callBackCancel.invoke()
        }

        // Button ok
        this@DialogConfirm.dataBinding.buttonOk.setOnClickListener {
            this@DialogConfirm.dismissConfirmDialog()
            callBackOk.invoke()
        }
    }

    /**
     * Dismiss dialog
     */
    fun dismissConfirmDialog() {
        this@DialogConfirm.alertDialog?.dismiss()
    }
}