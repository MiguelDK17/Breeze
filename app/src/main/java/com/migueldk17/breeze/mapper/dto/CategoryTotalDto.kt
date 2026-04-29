package com.migueldk17.breeze.mapper.dto

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.dto.CategoryTotalDto
import com.migueldk17.breeze.ui.utils.CategoryIconProvider
import com.migueldk17.breeze.ui.utils.soften
import kotlinx.collections.immutable.persistentListOf
import java.math.BigDecimal
import java.math.RoundingMode

fun List<CategoryTotalDto>.toDomain(): List<CategoryExpense> {
    val totalGeral = sumOf { it.totalAmount }

    if (totalGeral == BigDecimal.ZERO) return emptyList()

    return map {
        CategoryExpense(
            category = it.category,
            totalAmount = it.totalAmount,
            percentage = it.totalAmount.divide(totalGeral,2, RoundingMode.HALF_UP).toFloat(),
            progressBrush = returnBrush(it.category),
            colorCard = returnCardColor(it.category),
            iconColor = returnIconColor(it.category)
        )
    }.sortedByDescending { it.totalAmount }

}

private fun returnBrush(category: String): Brush {
    return when(category) {
        "Alimentação" -> Brush.horizontalGradient(persistentListOf(Color(0xFFFC9438), Color(0xFFFC9438).soften()))
        "Transporte" -> Brush.horizontalGradient(persistentListOf(Color(0xFFFEBF39), Color(0xFFFEBF39).soften()))
        "Educação" -> Brush.horizontalGradient(persistentListOf(Color(0xFF6BCF92), Color(0xFF6BCF92).soften()))
        "Moradia" -> Brush.horizontalGradient(persistentListOf(Color(0xFF3BC2BE), Color(0xFF3BC2BE).soften()))
        "Lazer" -> Brush.horizontalGradient(persistentListOf(Color(0xFF5333E9), Color(0xFF5333E9).soften()))
        "Saúde" -> Brush.horizontalGradient(persistentListOf(Color(0xFF5DA3EC), Color(0xFF5DA3EC).soften()))
        "Trabalho/Negócios" -> Brush.horizontalGradient(persistentListOf(Color(0xFF2F94F4), Color(0xFF2F94F4).soften()))
        "Pets" -> Brush.horizontalGradient(persistentListOf(Color(0xFFFE9C36), Color(0xFFFE9C36).soften()))
        "Pessoais" -> Brush.horizontalGradient(persistentListOf(Color(0xFFD61350), Color(0xFFD61350).soften()))
        "Outros" -> Brush.horizontalGradient(persistentListOf(Color(0xFF304E99), Color(0xFF304E99).soften()))
        else -> Brush.horizontalGradient(persistentListOf(Color(0xFF304E99), Color(0xFF304E99).soften()))
    }
}
private fun returnCardColor(category: String): Color {
    return when(category) {
        "Alimentação" -> Color(0xFFFCE3D0)
        "Transporte" -> Color(0xFFFEEFCB)
        "Educação" -> Color(0xFFE0F6E2)
        "Moradia" -> Color(0xFFDCF5F4)
        "Lazer" -> Color(0xFFEAE3FD)
        "Saúde" -> Color(0xFFDDEEFD)
        "Trabalho/Negócios" -> Color(0xFFE3E7FC)
        "Pets" -> Color(0xFFFEE9D8)
        "Pessoais" -> Color(0xFFFCD0E7)
        "Outros" -> Color(0xFFEFEFFA)
        else -> Color.White
    }
}

private fun returnIconColor(category: String): Color {
    return when (category) {
        "Alimentação" -> Color(0xFF87480F)
        "Transporte" -> Color(0xFF742E01)
        "Moradia" -> Color(0xFF3BC2BE)
        "Lazer" -> Color(0xFF5333E9)
        "Saude" -> Color(0xFF1A68DC)
        "Compras" -> Color(0xFFD61350)
        "Assinaturas" -> Color(0xFF5328DC)
        "Educação" -> Color(0xFF05A542)
        "Pets" -> Color(0xFFEA3B00)
        "Pessoais" -> Color(0xFFD43872)
        "Trabalho/Negócios" -> Color(0xFF2126F4)
        "Outros" -> Color(0xFF304E99)
        else -> Color.Unspecified
    }
}

