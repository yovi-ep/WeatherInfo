package yovi.putra.weatherinfo.view

import androidx.lifecycle.LiveData
import yovi.putra.weatherinfo.base.IBaseViewModel
import yovi.putra.weatherinfo.base.IBaseView
import yovi.putra.weatherinfo.models.CurrentWeather
import yovi.putra.weatherinfo.models.ForecastDaily
import yovi.putra.weatherinfo.models.ForecastHourly

interface MainContract {

    interface View : IBaseView

    interface ViewModel : IBaseViewModel {
        fun getWeatherCurrent(latitude: Double, longitude: Double) : LiveData<CurrentWeather>

        fun getForecastDaily(latitude: Double, longitude: Double) : LiveData<ForecastDaily>

        fun getForecastHourly(latitude: Double, longitude: Double) : LiveData<ForecastHourly>
    }

}