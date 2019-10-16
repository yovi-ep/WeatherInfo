package yovi.putra.weatherinfo.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import yovi.putra.weatherinfo.utils.DialogUtils
import yovi.putra.weatherinfo.R


/**
 * Created by yovi.putra
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setupLayout())
        setupData()
        setupUI()
    }

    abstract fun setupLayout(): Int

    abstract fun setupData()

    abstract fun setupUI()

    override fun contextView(): Context = this

    override fun onShowLoading(isShow: Boolean, tag: String?) {}

    override fun onRequestFailed(message: String?) {
        DialogUtils.showPositiveDialog(
            this, getString(R.string.lbl_warning),message?:"") { _, _ -> }
    }
}