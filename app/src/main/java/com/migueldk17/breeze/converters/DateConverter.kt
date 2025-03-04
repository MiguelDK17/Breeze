package com.migueldk17.breeze.converters


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toDatabaseValue(): String {
    return this.toString()
}
fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return LocalDateTime.parse(this, formatter)

}