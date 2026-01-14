package com.migueldk17.breeze.ui.features.confirmarpagamento.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.migueldk17.breezeicons.icons.BreezeIcons
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
    val verticalScroll = rememberScrollState()
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
                onPaymentMethodClick = {onPaymentMethodCLick()},
                state = ConfirmPaymentState(
                    name = "Spotify",
                    valor = 300.00,
                    juros = 0.00,
                    icon = BreezeIcons.Linear.Company.SpotifyLinear,
                    isContaParcelada = true,
                    qtdParcelas = listOf("5", "6", "7", "8")
                )
            )
        }
    }
}