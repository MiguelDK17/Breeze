package com.migueldk17.breeze.converters

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

fun LocalDateTime.toDatabaseValue(): String {
    return this.toString()
}
fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return LocalDateTime.parse(this, formatter)

}