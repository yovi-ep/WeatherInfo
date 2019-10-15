package yovi.putra.weatherinfo.base

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
open class BaseResponse (
    var cod: Int? = null,
    var message: String? = null
)