package yovi.putra.weatherinfo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Main(
    val temp: Double? = null
)