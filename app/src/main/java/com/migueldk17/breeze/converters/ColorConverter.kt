package com.migueldk17.breeze.converters

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.toDatabaseValue(): Int {
    return this.toArgb()
}

fun Int.toColor(): Color {
    return Color(this)
}
