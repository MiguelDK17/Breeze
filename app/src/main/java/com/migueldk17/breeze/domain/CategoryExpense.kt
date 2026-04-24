package com.migueldk17.breeze.domain

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import java.math.BigDecimal

data class CategoryExpense(
    val category: String,
    val totalAmount: BigDecimal,
    val percentage: Float,
    val colorCard: Color,
    val icon: BreezeIconsType,
    val iconColor: Color,
    val progressBrush: Brush
)
