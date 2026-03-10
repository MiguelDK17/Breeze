package com.migueldk17.breeze.ui.features.confirmarpagamento.model

import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import java.math.BigDecimal

data class ConfirmPaymentModel(
    val id: Long,
    val name: String,
    val formaDePagamento: String = "Nenhum",
    val juros: BigDecimal,
    val valor: BigDecimal,
    val icon: BreezeIconsType,
    val isContaParcelada: Boolean,
    val parcelas: List<ParcelaUI>
)
