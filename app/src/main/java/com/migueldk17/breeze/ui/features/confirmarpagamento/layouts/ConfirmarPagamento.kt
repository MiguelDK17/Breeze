package com.migueldk17.breeze.ui.features.confirmarpagamento.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.migueldk17.breeze.ui.features.confirmarpagamento.components.ConfirmPaymentContent
import com.migueldk17.breeze.ui.features.confirmarpagamento.state.ConfirmPaymentState
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
fun ConfirmarPagamentoDialog(
    isVisible: Boolean,
    state: ConfirmPaymentState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onPaymentMethodCLick: () -> Unit
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
                onConfirm = {},
                onPaymentMethodClick = {},
                state = ConfirmPaymentState(300.00)
            )
        }
    }
}

@Preview
@Composable
private fun Preview(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        BreezeTheme() {
            ConfirmarPagamentoDialog(
                isVisible = true,
                state = ConfirmPaymentState(amount = 300.00),
                onDismiss = {},
                onConfirm = {},
                onPaymentMethodCLick = {}

            )
        }

    }

}