package com.migueldk17.breeze.dto

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.utils.soften
import kotlinx.collections.immutable.persistentListOf
import java.math.BigDecimal

data class CategoryTotalDto(
    val category: String, //Vem do Room
    val totalAmount: BigDecimal, //Vem do Room

)


private fun returnIconColor(category: String): Color {
    return when(category) {
        "Alimentação" -> Color(0xFF87480F)
        "Transporte" -> Color(0xFF742E01)
        "Educação" -> Color(0xFF05A542)
        "Moradia" -> Color(0xFF3BC2BE)
        "Lazer" -> Color(0xFF5333E9)
        "Saúde" -> Color(0xFF1A68DC)
        "Trabalho/Negócios" -> Color(0xFF2F94F4)
        "Pets" -> Color(0xFFEA3B00)
        "Pessoais" -> Color(0xFFD61350)
        "Outros" -> Color(0xFF304E99)
        else -> Color.White
    }
}