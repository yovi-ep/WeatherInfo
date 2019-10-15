package yovi.putra.weatherinfo.domain.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import yovi.putra.weatherinfo.models.CurrentWeather
import yovi.putra.weatherinfo.models.ForecastDaily
import yovi.putra.weatherinfo.models.ForecastHourly

interface WeatherApi {

    @GET("weather")
    fun weatherCurrent(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ) : Observable<CurrentWeather>

    @GET("forecast")
    fun forecastHourly(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String
    ) : Observable<ForecastHourly>

    @GET("forecast/daily")
    fun forecastDaily(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String
    ) : Observable<ForecastDaily>
}