package com.android.developmentchallenge.base.view

import android.app.Activity
import android.app.AlertDialog
import android.content.res.Resources
import android.graphics.Rect
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.developmentchallenge.R
import com.android.developmentchallenge.util.ext.lifecycleOwner
import kotlin.math.roundToInt

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

abstract class BaseDialog<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val context: Activity
) {
    // Dialog alert
    var alertDialog: AlertDialog? = null

    // Data binding for view
    var dataBinding: T

    init {
        val builder = AlertDialog.Builder(this@BaseDialog.context, R.style.customDialogTheme)
        this@BaseDialog.dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), layoutResId, null, false
        )

        this@BaseDialog.dataBinding.root.let { builder.setView(it) }
        this@BaseDialog.dataBinding.lifecycleOwner = this@BaseDialog.context.lifecycleOwner()

        this@BaseDialog.alertDialog = builder.create()
        this@BaseDialog.alertDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this@BaseDialog.alertDialog?.setCancelable(false)
    }

    /**
     * Set min width for dialog if need
     */
    fun setUpWidthOfDialogWhenShowing() {
        if (!context.isFinishing) {
            // Set width for dialog
            val displayRectangle = Rect()
            val window: Window = this@BaseDialog.context.window
            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
            val density = context.resources.displayMetrics.density
            val widthNew =
                (350 * density).roundToInt()
                    .coerceAtMost((displayRectangle.width() * 0.9).roundToInt())
            this@BaseDialog.alertDialog?.window?.attributes?.height?.let {
                this@BaseDialog.alertDialog?.window?.setLayout(
                    widthNew,
                    it
                )
            }
            this@BaseDialog.alertDialog?.window?.setGravity(Gravity.CENTER)
        }
    }

    /**
     * Set custom width full screen for dialog
     */
    fun setUpCustomSizeWhenShowing(
        widthPx: Int = -1,
        heightPx: Int = -1,
        gravity: Int = Gravity.CENTER
    ) {
        if (!context.isFinishing) {
            val displayRectangle = Rect()
            val window: Window = this@BaseDialog.context.window
            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)

            // set custom width
            val widthCustom: Int = if (widthPx == -1) {
                Resources.getSystem().displayMetrics.widthPixels // custom value
            } else {
                widthPx // default case
            }

            // set custom height
            val heightCustom: Int = if (heightPx == -1) {
                Resources.getSystem().displayMetrics.heightPixels // custom value
            } else {
                heightPx // default case
            }

            // set value to dialog
            this@BaseDialog.alertDialog?.window?.setLayout(
                widthCustom,
                heightCustom
            )
            this@BaseDialog.alertDialog?.window?.setGravity(gravity)
        }
    }

    /**
     * Check if this dialog is showing
     * Return true if is showing
     */
    fun isShowing(): Boolean {
        return if (alertDialog != null)
            alertDialog!!.isShowing
        else false
    }
}