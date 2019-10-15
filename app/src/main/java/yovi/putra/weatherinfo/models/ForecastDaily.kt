package yovi.putra.weatherinfo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import yovi.putra.weatherinfo.base.BaseResponse

@JsonIgnoreProperties(ignoreUnknown = true)
data class ForecastDaily(
    val city: City? = null,
    val list: MutableList<DailyInfo> = mutableListOf()
) : BaseResponse()