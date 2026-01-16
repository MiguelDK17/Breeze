package com.migueldk17.breeze.ui.features.confirmarpagamento.layouts

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.migueldk17.breeze.ui.features.confirmarpagamento.components.ConfirmPaymentContent
import com.migueldk17.breeze.ui.features.confirmarpagamento.state.ConfirmPaymentState

@Composable
fun ConfirmarPagamentoDialog(
    isVisible: Boolean,
    state: ConfirmPaymentState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    val name = state.name
    val preco = state.valor
    val juros = state.juros
    val isContaParcelada = state.isContaParcelada
    val icon = state.icon
    val qdtParcelas = state.listaDeParcelas
    if (!isVisible) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(24.dp),
        ) {
            ConfirmPaymentContent(
                onConfirm = {onConfirm()},
                state = ConfirmPaymentState(
                    name = name,
                    valor = preco,
                    juros = juros,
                    icon = icon,
                    isContaParcelada = isContaParcelada,
                    listaDeParcelas = qdtParcelas
                )
            )
        }
    }
}