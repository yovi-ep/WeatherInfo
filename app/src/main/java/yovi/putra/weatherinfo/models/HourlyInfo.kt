package yovi.putra.weatherinfo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class HourlyInfo(
    val dt: Int? = null,
    val dt_txt: String? = null,
    val main: Main? = null,
    val weather: List<Weather> = emptyList()
)