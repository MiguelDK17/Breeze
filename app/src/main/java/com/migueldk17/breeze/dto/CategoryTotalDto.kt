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
    val category: String, //Categoria das contas
    val totalAmount: BigDecimal, //Valor total da categoria
)