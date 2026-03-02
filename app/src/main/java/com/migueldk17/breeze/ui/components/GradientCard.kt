package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList


@Composable
fun GradientCard(
    gradientColors: ImmutableList<Color>,
    verticalPadding: Dp = 0.dp,
    horizontalPadding: Dp = 0.dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
                .padding(
                    vertical = verticalPadding,
                    horizontal = horizontalPadding
                )
        ) {
            content()
        }
    }
}