package com.android.developmentchallenge.util.helper

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.android.developmentchallenge.util.dialog.*
import com.android.developmentchallenge.util.ext.lifecycle
import com.android.developmentchallenge.util.interfaces.LifecycleBehaviour

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class DialogHelper(private val context: Activity) : LifecycleBehaviour {
    // Lifecycle of dialog
    override var lifecycle: Lifecycle? = null

    // Toast message
    private var toastMessage: Toast? = null

    // Internet dialog
    val dialogInternet = DialogInternet(context)

    // Confirm dialog
    val dialogConfirm = DialogConfirm(context)

    // Confirm dialog
    val dialogLoading = DialogLoading(context)

    // Confirm Error
    val dialogError = DialogError(context)

    // Confirm Rooted
    val dialogRooted = DialogRooted(context)

    init {
        this@DialogHelper.bind(this.context.lifecycle())
    }

    /**
     * Clear or dismiss dialog in here
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        dialogInternet.dismissInternetDialog()
        dialogConfirm.dismissConfirmDialog()
        dialogLoading.dismissLoadingDialog()
        dialogError.dismissErrorDialog()
        dialogRooted.dismissErrorDialog()
    }

    /**
     * Stop show toast and re create toast with message
     *
     * @param message is content showing
     */
    fun iniToastMessage(message: String) {
        if (toastMessage != null) {
            toastMessage!!.cancel()
            toastMessage = Toast.makeText(this@DialogHelper.context, message, Toast.LENGTH_SHORT)
            toastMessage!!.show()
        } else {
            toastMessage = Toast.makeText(this@DialogHelper.context, message, Toast.LENGTH_SHORT)
            toastMessage!!.show()
        }
    }
}