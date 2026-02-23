package com.migueldk17.breeze.domain

import com.migueldk17.breeze.enums.StatusConta
import java.math.BigDecimal
import java.time.LocalDate

data class Conta(
    val id: Long,
    val valor: Double,
    val status: StatusConta,
    val dataVencimento: LocalDate
) {
    fun isAtrasada(today: LocalDate): Boolean {
        return status == StatusConta.PENDENTE && dataVencimento.isBefore(today)
    }
}
