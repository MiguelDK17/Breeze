package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BreezeElevatedCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFFFFFFF),
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            ,
        colors = CardDefaults.elevatedCardColors(
            containerColor
        )
    ) {
        content()
    }
}