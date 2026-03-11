package com.migueldk17.breeze.ui.features.adicionarconta.models

import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import java.math.BigDecimal
import java.time.LocalDate

data class DadosContaUI(
    val nome: String,
    val icone: BreezeIconsType,
    val corIcone: Color,
    val corCard: Color,
    val valor: BigDecimal,
    val categoria: String,
    val subCategoria: String,
    val valorParcela: BigDecimal,
    val totalParcelas: Int,
    val data: LocalDate,
    val isParcelada: Boolean,
    val taxaJuros: BigDecimal
)