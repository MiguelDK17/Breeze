package com.migueldk17.breeze.ui.features.confirmarpagamento.model

import com.github.migueldk17.breezeicons.icons.BreezeIconsType

data class ConfirmPaymentModel(
    val id: Long,
    val name: String,
    val formaDePagamento: String = "Nenhum",
    val juros: Double,
    val valor: Double,
    val icon: BreezeIconsType,
    val isContaParcelada: Boolean,
    val parcelas: List<ParcelaUI>
)
