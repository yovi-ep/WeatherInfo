package yovi.putra.weatherinfo.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import yovi.putra.weatherinfo.R

/***
 * yovi.putra
 */
object DialogUtils {

    fun showPositiveDialog(
        context: Context,
        title: String,
        content: String,
        onClickListener: (DialogInterface, Int) -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setPositiveButton(context.getString(R.string.lbl_ok), onClickListener)
        show(context, builder)
    }

    /***
     * @param context
     * @param builder
     */
    private fun show(context: Context, builder: AlertDialog.Builder?) {
        if (builder != null) {
            val activity = context as Activity
            if (!activity.isFinishing) {
                builder.show()
            }
        }
    }
}
