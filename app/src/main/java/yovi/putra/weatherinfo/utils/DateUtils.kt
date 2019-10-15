package yovi.putra.weatherinfo.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun format(date: Date, format: String): String =
        format(date, format, Locale.getDefault())

    fun format(date: Date, format: String, locale: Locale): String =
        SimpleDateFormat(format, locale).format(date)

    fun parser(timestamp: Long): Date = Date(Timestamp(timestamp).time * 1000L)

}