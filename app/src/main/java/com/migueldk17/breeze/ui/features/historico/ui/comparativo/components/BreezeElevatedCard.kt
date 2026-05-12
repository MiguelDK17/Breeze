package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.theme.DarkBlue
import com.migueldk17.breeze.ui.theme.MidnightBlue
import com.migueldk17.breeze.ui.theme.NavyPetrol

@Composable
fun BreezeElevatedCard(
    modifier: Modifier = Modifier,
    containerColor: Color = if (!isSystemInDarkTheme()) Color(0xFFFFFFFF) else Color(0xFF091F3F),
    shape: Shape = MaterialTheme.shapes.large,
    elevation: Dp = 6.dp,
    outlineColor: Color = Color(0xFF152E55),
    outlineWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = outlineWidth,
                    color = outlineColor,
                    shape = shape
                )
        ) {
            content()
        }

    }
}