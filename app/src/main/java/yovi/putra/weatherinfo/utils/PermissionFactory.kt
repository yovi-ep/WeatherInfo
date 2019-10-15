package yovi.putra.weatherinfo.utils

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Activity.checkPermission(permissions: MutableList<String>, onSuccessListener: () -> Unit) {
    Dexter.withActivity(this)
        .withPermissions(permissions)
        .withListener(object: MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if (it.isAnyPermissionPermanentlyDenied) {
                        checkPermission(permissions, onSuccessListener)
                    }

                    if (it.areAllPermissionsGranted()){
                        onSuccessListener()
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        })
        .check()
}