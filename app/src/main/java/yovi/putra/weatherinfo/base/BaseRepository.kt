package yovi.putra.weatherinfo.base

import yovi.putra.weatherinfo.utils.RestClient


abstract class BaseRepository {

    inline fun <reified API> api() : API {
        return RestClient()
            .get()
            .build()
            .create(API::class.java)
    }

    inline fun <reified API> apiSample() : API {
        return RestClient()
            .get()
            //.baseUrl(SyncStateContract.Constants.BASE_URL_SAMPLE)
            .build()
            .create(API::class.java)
    }
}