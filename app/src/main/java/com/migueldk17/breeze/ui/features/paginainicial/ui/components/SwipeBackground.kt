package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.features.paginainicial.model.SwipeBackgroundConfig

@Composable
fun SwipeBackground(
    dismissState: SwipeToDismissBoxState,
    modifier: Modifier = Modifier,
){
    val direction = dismissState.dismissDirection

    val (text, backgroundColor, alignment, textColor) = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> SwipeBackgroundConfig(
            text = "Detalhes",
            backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
            alignment = Alignment.CenterStart,
            textColor = MaterialTheme.colorScheme.primary
        )
        SwipeToDismissBoxValue.EndToStart -> SwipeBackgroundConfig(
            text = "Excluir",
            backgroundColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
            alignment = Alignment.CenterEnd,
            textColor = MaterialTheme.colorScheme.error
        )
        else -> SwipeBackgroundConfig(
            text = "",
            backgroundColor = Color.Transparent,
            alignment = Alignment.Center,
            textColor = Color.Transparent
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        BreezeRegularText(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}