package yovi.putra.weatherinfo.view

import yovi.putra.weatherinfo.base.IBasePresenter
import yovi.putra.weatherinfo.base.IBaseView
import yovi.putra.weatherinfo.models.CurrentWeather
import yovi.putra.weatherinfo.models.ForecastDaily
import yovi.putra.weatherinfo.models.ForecastHourly

interface MainContract {

    interface View : IBaseView {
        fun onWeatherCurrent(weather: CurrentWeather)

        fun onForecastDaily(weather: ForecastDaily)

        fun onForecastHourly(weather: ForecastHourly)
    }

    interface Presenter : IBasePresenter {
        fun getWeatherCurrent(latitude: Double, longitude: Double)

        fun getForecastDaily(latitude: Double, longitude: Double)

        fun getForecastHourly(latitude: Double, longitude: Double)
    }

}