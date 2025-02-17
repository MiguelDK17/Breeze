package com.migueldk17.breeze

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter

fun Color.toDatabaseValue(): Int {
    return this.toArgb()
}

fun Int.toColor(): Color {
    return Color(this)
}
