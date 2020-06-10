package dk.quan.plandayhr.extensions

import android.view.View
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.showSnackBar(msgId: Int, length: Int = Snackbar.LENGTH_LONG) {
    showSnackBar(context.getString(msgId), length)
}

fun View.showSnackBar(msg: String, length: Int = Snackbar.LENGTH_LONG) {
    showSnackBar(msg, length, null, {})
}

fun View.showSnackBar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    showSnackBar(context.getString(msgId), length, context.getString(actionMessageId), action)
}

fun View.showSnackBar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackBar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackBar.setAction(actionMessage) {
            action(this)
        }
    }
    snackBar.show()
}