package com.migueldk17.breeze.ui.features.historico.model


import androidx.compose.ui.graphics.toArgb
import java.time.LocalDateTime

data class LinhaDoTempoModel(
    val id: Long,
    var name: String,
    val valor: Double,
    val dateTime: LocalDateTime,
    val icon: String,
    val colorIcon: Int = -9573033,
    val colorCard: Int = -1838339,
    val isContaParcelada: Boolean = false,
    val category: String = "",
    val subCategory: String = "",
    val isReceita: Boolean = false,
    )