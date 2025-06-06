package com.migueldk17.breeze.converters


import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDate.toDatabaseValue(): String {
    return this.toString()
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
}


fun LocalDateTime.toDatabaseValue(): String {
    return this.toString()
}
fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)

}