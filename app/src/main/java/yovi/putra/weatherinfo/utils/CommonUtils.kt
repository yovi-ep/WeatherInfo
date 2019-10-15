package yovi.putra.weatherinfo.utils

import android.content.Context
import yovi.putra.weatherinfo.R

object CommonUtils {

    fun getTemperature(context: Context, kelvin: Double?) : String {
        val tempC = KelvinToCelcius(kelvin ?: 0.0).toInt()
        return String.format(context.getString(R.string.lbl_template), tempC)
    }

    fun KelvinToCelcius(kelvin: Double) : Double {
        return kelvin - 273.15
    }

    fun getIcon(status: String?) : Int =
        when (status) {
            "Clear" -> R.drawable.ic_sun
            "Rain" -> R.drawable.ic_rain
            "light rain" -> R.drawable.ic_lightning
            "aa" -> R.drawable.ic_drizzle
            else -> R.drawable.ic_cloudy
        }
}