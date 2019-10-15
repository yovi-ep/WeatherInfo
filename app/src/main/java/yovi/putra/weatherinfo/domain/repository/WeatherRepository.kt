package yovi.putra.weatherinfo.domain.repository

import io.reactivex.Observable
import yovi.putra.mvvm_databinding_corountines_retrofit.utils.Constants
import yovi.putra.weatherinfo.base.BaseRepository
import yovi.putra.weatherinfo.domain.api.WeatherApi
import yovi.putra.weatherinfo.models.CurrentWeather
import yovi.putra.weatherinfo.models.ForecastDaily
import yovi.putra.weatherinfo.models.ForecastHourly
import yovi.putra.weatherinfo.models.HourlyInfo
import yovi.putra.weatherinfo.utils.DateUtils
import yovi.putra.weatherinfo.utils.RxUtils
import java.util.*

class WeatherRepository : BaseRepository() {

    private val service = api<WeatherApi>()
    /* get sample, because free not availabe */
    private val serviceSample = apiSample<WeatherApi>()

    fun getWeatherCurrent(
        latitude: Double, longitude: Double) : Observable<CurrentWeather> =
        service.weatherCurrent(
            latitude,
            longitude,
            Constants.APIKEY
        ).compose(RxUtils.applyObservableAsync())

    fun getForecastDaily(
        latitude: Double, longitude: Double) : Observable<ForecastDaily> =
        serviceSample.forecastDaily(
            latitude,
            longitude,
            7,
            Constants.APIKEY_TRIAL
        ).compose(RxUtils.applyObservableAsync())

    fun getForecastHourly(latitude: Double, longitude: Double)
            : Observable<ForecastHourly> =
        service.forecastHourly(
            latitude,
            longitude,
            7,
            Constants.APIKEY
        ).compose(RxUtils.applyObservableAsync())
            .filter {
                val daily = mutableListOf<HourlyInfo>()

                /* Get new data */
                it.list.forEach { hourly ->
                    val dt = DateUtils.parser(hourly.dt?.toLong()?:0)
                    if (dt.after(Date())) {
                        daily.add(hourly)
                    }
                }

                it.list.clear()
                it.list.addAll(daily)
                true
            }

}