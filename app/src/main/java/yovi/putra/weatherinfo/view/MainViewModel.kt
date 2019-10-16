package yovi.putra.weatherinfo.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.addTo
import yovi.putra.weatherinfo.base.BaseViewModel
import yovi.putra.weatherinfo.domain.repository.WeatherRepository
import yovi.putra.weatherinfo.models.CurrentWeather
import yovi.putra.weatherinfo.models.ForecastDaily
import yovi.putra.weatherinfo.models.ForecastHourly

class MainViewModel : BaseViewModel(), MainContract.ViewModel {
    private val repository: WeatherRepository = WeatherRepository()

    private val weatherCurrentLiveData = MutableLiveData<CurrentWeather>()
    private val forecastDailyLiveData = MutableLiveData<ForecastDaily>()
    private val forecastHourlyLiveData = MutableLiveData<ForecastHourly>()

    override fun getWeatherCurrent(latitude: Double, longitude: Double) : LiveData<CurrentWeather> {
        repository.getWeatherCurrent(latitude, longitude)
            .doOnSubscribe { activity?.onShowLoading(true, MainActivity.CURRENT_LOADING) }
            .doOnTerminate { activity?.onShowLoading(false, MainActivity.CURRENT_LOADING) }
            .subscribe(onSuccess<CurrentWeather> { data ->
                weatherCurrentLiveData.postValue(data)
            }, onFailed())
            .addTo(composite)

        return weatherCurrentLiveData
    }

    override fun getForecastDaily(latitude: Double, longitude: Double) : LiveData<ForecastDaily>  {
        repository.getForecastDaily(latitude, longitude)
            .doOnSubscribe { activity?.onShowLoading(true, MainActivity.DAILY_LOADING) }
            .doOnTerminate { activity?.onShowLoading(false, MainActivity.DAILY_LOADING) }
            .subscribe(onSuccess<ForecastDaily> { data ->
                forecastDailyLiveData.postValue(data)
            }, onFailed())
            .addTo(composite)
        return forecastDailyLiveData
    }

    override fun getForecastHourly(latitude: Double, longitude: Double) : LiveData<ForecastHourly>  {
        repository.getForecastHourly(latitude, longitude)
            .doOnSubscribe { activity?.onShowLoading(true, MainActivity.HOURLY_LOADING) }
            .doOnTerminate { activity?.onShowLoading(false, MainActivity.HOURLY_LOADING) }
            .subscribe(onSuccess<ForecastHourly> { data ->
                forecastHourlyLiveData.postValue(data)
            }, onFailed())
            .addTo(composite)
        return forecastHourlyLiveData
    }
}