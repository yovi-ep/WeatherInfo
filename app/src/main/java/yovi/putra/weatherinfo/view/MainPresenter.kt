package yovi.putra.weatherinfo.view

import io.reactivex.rxkotlin.addTo
import yovi.putra.weatherinfo.base.BasePresenter
import yovi.putra.weatherinfo.domain.repository.WeatherRepository
import yovi.putra.weatherinfo.models.CurrentWeather
import yovi.putra.weatherinfo.models.ForecastDaily
import yovi.putra.weatherinfo.models.ForecastHourly

class MainPresenter (
    private val activity: MainContract.View,
    private val repository: WeatherRepository = WeatherRepository()
) : BasePresenter(activity), MainContract.Presenter {

    override fun getWeatherCurrent(latitude: Double, longitude: Double) {
        repository.getWeatherCurrent(latitude, longitude)
            .doOnSubscribe { activity.onShowLoading(true, MainActivity.CURRENT_LOADING) }
            .doOnTerminate { activity.onShowLoading(false, MainActivity.CURRENT_LOADING) }
            .subscribe(onSuccess<CurrentWeather> { data ->
                activity.onWeatherCurrent(data)
            }, onFailed())
            .addTo(composite)
    }

    override fun getForecastDaily(latitude: Double, longitude: Double) {
        repository.getForecastDaily(latitude, longitude)
            .doOnSubscribe { activity.onShowLoading(true, MainActivity.DAILY_LOADING) }
            .doOnTerminate { activity.onShowLoading(false, MainActivity.DAILY_LOADING) }
            .subscribe(onSuccess<ForecastDaily> { data ->
                activity.onForecastDaily(data)
            }, onFailed())
            .addTo(composite)
    }

    override fun getForecastHourly(latitude: Double, longitude: Double) {
        repository.getForecastHourly(latitude, longitude)
            .doOnSubscribe { activity.onShowLoading(true, MainActivity.HOURLY_LOADING) }
            .doOnTerminate { activity.onShowLoading(false, MainActivity.HOURLY_LOADING) }
            .subscribe(onSuccess<ForecastHourly> { data ->
                activity.onForecastHourly(data)
            }, onFailed())
            .addTo(composite)
    }
}