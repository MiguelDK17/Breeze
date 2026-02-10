package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import com.airbnb.lottie.Lottie
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

    val (
        text,
        backgroundColor,
        arrangementHorizontal,
        textColor,
        animationRes
    ) = when (direction) {
        SwipeToDismissBoxValue.StartToEnd -> SwipeBackgroundConfig(
            text = "Detalhes",
            backgroundColor = detailsColor,
            arrangementHorizontal = Arrangement.Start,
            textColor = MaterialTheme.colorScheme.primary,
            animationRes = R.raw.payment_verify_loader
        )

        SwipeToDismissBoxValue.EndToStart -> SwipeBackgroundConfig(
            text = "Excluir",
            backgroundColor = deleteColor,
            arrangementHorizontal = Arrangement.End,
            textColor = MaterialTheme.colorScheme.error,
            animationRes = R.raw.delete_files
        )

        else -> SwipeBackgroundConfig(
            text = "",
            backgroundColor = Color.Transparent,
            arrangementHorizontal = Arrangement.Center,
            textColor = Color.Transparent,
            animationRes = null
        )
    }
    OutlinedCard(
        modifier = modifier
            .fillMaxSize(),
        colors = CardDefaults.outlinedCardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = arrangementHorizontal
        ) {
            val hasAnimation = animationRes != null

            when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    if (hasAnimation) {
                        LottieAnimation(
                            animationRes = animationRes,
                            isPlaying = true,
                            size = 80.dp,
                            iterations = 1
                        )
                    }
                    Text(
                        text = text,
                        color = textColor,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    Text(
                        text = text,
                        color = textColor,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    if (hasAnimation) {
                        LottieAnimation(
                            animationRes = animationRes,
                            isPlaying = true,
                            size = 80.dp,
                            iterations = 1,
                        )
                    }
                }
                else -> Unit
            }
        }
    }

}