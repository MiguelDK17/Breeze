package com.migueldk17.breeze.converters

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

//Converte o tipo Color do Compose para Int para evitar bugs com o Room e KSP
fun Color.toDatabaseValue(): Int {
    return this.toArgb()
}

//Converte Int para Color do Compose
fun Int.toColor(): Color {
    return Color(this)
}
