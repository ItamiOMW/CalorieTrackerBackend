package com.itami.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DateTimeUtil {

    fun currentDateTime(): LocalDateTime {
        return LocalDateTime.now(ZoneId.systemDefault())
    }

    fun stringToDateTime(datetimeStr: String): LocalDateTime {
        return ZonedDateTime.parse(datetimeStr)
            .withZoneSameInstant(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun stringToDate(dateStr: String): LocalDate {
        return ZonedDateTime.parse(dateStr)
            .withZoneSameInstant(ZoneId.systemDefault())
            .toLocalDate()
    }

    fun datetimeToString(localDateTime: LocalDateTime): String {
        return localDateTime.atZone(ZoneId.systemDefault()).toString()
    }
}

