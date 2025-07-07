package com.migueldk17.breeze.ui.features.historico.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class GraficoDoDia(
    val dayOfMonth: Int,
    val valor: Float,
    val brush: Brush,
)
