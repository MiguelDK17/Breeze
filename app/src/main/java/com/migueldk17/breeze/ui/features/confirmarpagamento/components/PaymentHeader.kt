package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.theme.NavyBlue

@Composable
fun PaymentHeader(){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            TitleText(
                modifier = Modifier.padding(top = 10.dp),
                text = "Confirmar pagamento",
                fontWeight = FontWeight.Bold,
                color = NavyBlue
            )

    }
}