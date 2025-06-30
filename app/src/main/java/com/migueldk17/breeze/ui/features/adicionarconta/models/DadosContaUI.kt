package com.migueldk17.breeze.ui.features.adicionarconta.models

import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import java.time.LocalDate

data class DadosContaUI(
    val nome: String,
    val icone: BreezeIconsType,
    val corIcone: Color,
    val corCard: Color,
    val valor: Double,
    val categoria: String,
    val subCategoria: String,
    val valorParcela: Double,
    val totalParcelas: Int,
    val data: LocalDate,
    val isParcelada: Boolean,
    val taxaJuros: Double
)