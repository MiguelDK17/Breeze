package com.migueldk17.breeze.domain

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import java.math.BigDecimal

data class CategoryExpense(
    val category: String, //Categoria da conta, vem do Room
    val totalAmount: BigDecimal, //Valor total da categoria, vem do Room
    val percentage: Float, //Porcentagem da conta em relação ao valor total de todas as categorias somadas
    val colorCard: Color, //Cor do card/container do icone
    val iconColor: Color, //Cor do icone
    val progressBrush: Brush //Brush de progresso usado em LinearProgressBar
)
