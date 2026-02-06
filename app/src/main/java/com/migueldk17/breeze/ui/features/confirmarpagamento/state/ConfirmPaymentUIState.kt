package com.migueldk17.breeze.ui.features.confirmarpagamento.state

import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ConfirmPaymentModel

data class ConfirmPaymentUIState(
    val isLoading: Boolean = false,
    val model: ConfirmPaymentModel? = null,
    val error: String? = null
)
