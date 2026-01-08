package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.features.confirmarpagamento.state.ConfirmPaymentState

@Composable
fun ConfirmPaymentContent(
    state: ConfirmPaymentState,
    onConfirm: () -> Unit,
    onPaymentMethodClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PaymentHeader(state)

        HorizontalDivider()

        PaymentAmount()

        HorizontalDivider()

        PaymentMethodField()

        InterestField()

        InstallmentField()

        Spacer(modifier = Modifier.weight(1f))

        BreezeButton(
            text = "Confirmar",
            onClick = {}
        )
    }

}