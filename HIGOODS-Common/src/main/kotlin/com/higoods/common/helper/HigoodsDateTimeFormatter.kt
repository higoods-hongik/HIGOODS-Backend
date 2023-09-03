package com.higoods.common.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object HigoodsDateTimeFormatter {
    val dateTime: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
}

fun String.toHigoodsDateTimeFormat(): LocalDateTime = LocalDateTime.parse(this, HigoodsDateTimeFormatter.dateTime)

fun LocalDateTime.toHigoodsDateTimeFormat(): String = format(HigoodsDateTimeFormatter.dateTime)
