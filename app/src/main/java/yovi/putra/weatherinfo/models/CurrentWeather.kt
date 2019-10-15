package yovi.putra.weatherinfo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import yovi.putra.weatherinfo.base.BaseResponse

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrentWeather(
    val dt: Int? = null,
    val main: Main? = null,
    val name: String? = null,
    val weather: List<Weather> = emptyList()
) : BaseResponse()