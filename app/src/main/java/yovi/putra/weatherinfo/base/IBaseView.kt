package yovi.putra.weatherinfo.base

import android.content.Context


/**
 * Created by yovi.putra
 */

interface IBaseView {
    fun contextView() : Context

    fun onShowLoading(isShow: Boolean, tag: String?)

    fun onRequestFailed(message: String?)
}
