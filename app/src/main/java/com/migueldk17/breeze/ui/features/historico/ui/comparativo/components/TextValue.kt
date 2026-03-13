package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.DescriptionText
import java.math.BigDecimal


@Composable
fun TextValue(
    value: BigDecimal,
    modifier: Modifier = Modifier,
    size: TextUnit = 14.sp,
    colors: Color = Color(0xFF1D1D1D)
) {
    Row(
        modifier = modifier
    ) {
        DescriptionText(
            text = "R$",
            size = size,
            color = colors)

        DescriptionText(
            text = value.toString(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 2.dp),
            color = colors,
            size = size
        )
    }
}
