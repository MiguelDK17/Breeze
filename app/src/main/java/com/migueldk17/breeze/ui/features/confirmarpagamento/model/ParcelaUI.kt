package com.migueldk17.breeze.ui.features.confirmarpagamento.model

import java.math.BigDecimal
import java.time.LocalDate

data class ParcelaUI(
    val idDaParcela: Long,
    val numero: Int,
    val valor: BigDecimal,
    val data: LocalDate
)
