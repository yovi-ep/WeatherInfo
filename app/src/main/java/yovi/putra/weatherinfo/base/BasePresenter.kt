package yovi.putra.weatherinfo.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import org.json.JSONObject
import retrofit2.HttpException
import yovi.putra.weatherinfo.R


/**
 * Created by yovi.putra
 */
abstract class BasePresenter(private val activity: IBaseView) : IBasePresenter {

    private val TAG = BasePresenter::class.java.simpleName

    val contextView: Context by lazy { activity.contextView() }

    protected var composite = CompositeDisposable()

    override fun onDestroyPresenter() {
        composite.dispose()
    }

    fun <DATA> onSuccess(listener: (DATA) -> Unit): Consumer<Any> =
        Consumer {
            val resp = it as BaseResponse
            activity.onShowLoading(false, "")
            when (it.cod) {
                200 -> listener(it as DATA)
                else -> activity.onRequestFailed(it.message)
            }
        }

    fun onFailed(): Consumer<Any> =
        Consumer {
            activity.onShowLoading(false, "")
            when (it) {
                is HttpException -> {
                    Log.d(TAG, "HttpException")
                    try {
                        val errBody = it.response()?.errorBody()?.string()
                        val json = JSONObject(errBody ?: "")
                        activity.onRequestFailed(json["cod"].toString()+": " + json["message"].toString())
                    } catch (e: Exception) {
                        Log.e(TAG, e.message ?: "")
                        activity.onRequestFailed(activity.contextView().getString(R.string.server_under_maintenance))
                    }
                }
                else -> {
                    if (it is Throwable) {
                        Log.e(TAG, it.message?: "")
                    }

                    val connectivityManager = contextView.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
                    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

                    if (isConnected) {
                        Log.e(TAG, "Internet available")
                        activity.onRequestFailed(activity.contextView().getString(R.string.cannot_connect_toserver))
                    } else {
                        Log.e(TAG, "Internet not available")
                        activity.onRequestFailed(activity.contextView().getString(R.string.no_internet_connection))
                    }
                }
            }
        }
}