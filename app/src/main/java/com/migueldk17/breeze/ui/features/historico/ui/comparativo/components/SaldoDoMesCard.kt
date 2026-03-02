package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.theme.NavyBlue


@Composable
fun SaldoDoMesCard(
    modifier: Modifier = Modifier
){
    ElevatedCard(
        modifier = modifier
            .padding(5.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           TitleText(
                text = "Saldo do mês",
               color = NavyBlue,
               modifier = Modifier
            )
            SaldoFinal()
        }
    }
}
