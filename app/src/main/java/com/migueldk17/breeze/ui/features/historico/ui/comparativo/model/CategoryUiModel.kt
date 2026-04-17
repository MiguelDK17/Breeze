package com.migueldk17.breeze.ui.features.historico.ui.comparativo.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import java.math.BigDecimal

data class CategoryUiModel(
    val category: String,
    val totalAmount: BigDecimal,
    val percentage: Float,
    val icon: String,
    val progressBrush: Brush,
    val colorCard: Color,
    val colorIcon: Color
)
