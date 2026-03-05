package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.DescriptionText
import kotlin.Double


@Composable
fun TextValue(
    value: Double,
    modifier: Modifier = Modifier,
) {
    Row() {
        DescriptionText("R$")

        DescriptionText(
            text = value.toString(),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 2.dp),
            color = Color(0xFF1D1D1D)


        )
    }
}
