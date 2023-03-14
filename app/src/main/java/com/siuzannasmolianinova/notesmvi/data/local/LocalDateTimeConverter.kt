package com.siuzannasmolianinova.notesmvi.data.local

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeConverter {

    @TypeConverter
    fun localDateTimeToTimestamp(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    @TypeConverter
    fun timestampToLocalDateTime(timeStamp: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())
    }

    fun dayTime(timeStamp: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())

    fun day(dayTime: LocalDateTime) =
        "${dayTime.dayOfMonth} : ${dayTime.monthValue} : ${dayTime.year}"
}
