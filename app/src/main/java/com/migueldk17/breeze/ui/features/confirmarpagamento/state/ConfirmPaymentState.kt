package com.migueldk17.breeze.ui.features.confirmarpagamento.state

import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.entity.ParcelaEntity

data class ConfirmPaymentState(
    val name: String,
    val juros: Double,
    val valor: Double,
    val icon: BreezeIconsType,
    val isContaParcelada: Boolean,
    val listaDeParcelas: List<ParcelaEntity>
)
