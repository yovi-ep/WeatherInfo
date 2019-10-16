package yovi.putra.weatherinfo.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.reobserve(owner: LifecycleOwner, crossinline func: (T?) -> (Unit)) {
    removeObservers(owner)
    observe(owner, Observer<T> { t -> func(t) })
}