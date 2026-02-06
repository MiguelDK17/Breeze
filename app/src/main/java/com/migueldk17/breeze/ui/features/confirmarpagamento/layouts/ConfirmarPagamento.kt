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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.migueldk17.breeze.ui.features.confirmarpagamento.components.ConfirmPaymentContent
import com.migueldk17.breeze.ui.features.confirmarpagamento.model.ConfirmPaymentModel
import com.migueldk17.breeze.ui.features.confirmarpagamento.viewmodels.ConfirmarPagamentoViewModel

@Composable
fun ConfirmarPagamentoDialog(
    viewModel: ConfirmarPagamentoViewModel = hiltViewModel(),
    isVisible: Boolean,
    state: ConfirmPaymentModel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
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
                onConfirm = {
                    onConfirm()
                },
                state = state,
                viewModel = viewModel
            )
        }
    }
}