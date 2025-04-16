package com.migueldk17.breeze.converters


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toDatabaseValue(): String {
    return this.toString()
}
fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)

}