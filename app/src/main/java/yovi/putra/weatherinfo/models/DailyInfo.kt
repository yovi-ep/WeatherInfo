package yovi.putra.weatherinfo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class DailyInfo(
    val dt: Int? = null,
    val temp: Temp? = null,
    val weather: List<Weather> = emptyList()
)