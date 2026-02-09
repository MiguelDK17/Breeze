package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.R
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.paginainicial.model.SwipeBackgroundConfig

@Composable
fun SwipeBackground(
    dismissState: SwipeToDismissBoxState,
    alpha: Float,
    modifier: Modifier = Modifier,
){
    val direction = dismissState.dismissDirection
    val detailsColor = lerp(
        Color.Transparent,
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        alpha
    )
    val deleteColor = lerp(
        Color.Transparent,
        MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
        alpha
    )

    val (text, backgroundColor, alignment, textColor) = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> SwipeBackgroundConfig(
            text = "Detalhes",
            backgroundColor = Color.Transparent,
            alignment = Alignment.CenterStart,
            textColor = MaterialTheme.colorScheme.primary
        )
        SwipeToDismissBoxValue.EndToStart -> SwipeBackgroundConfig(
            text = "Excluir",
            backgroundColor = deleteColor,
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LottieAnimation(
                animationRes = R.raw.payment_verify_loader,
                isPlaying = true,
                size = 80.dp,
            )
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )
        }

    }
}

@Preview
@Composable
private fun Preview(){
    val swipe = rememberSwipeToDismissBoxState(

    )
    SwipeBackground(
        dismissState =swipe,
        alpha = 1.0f,

    )
}