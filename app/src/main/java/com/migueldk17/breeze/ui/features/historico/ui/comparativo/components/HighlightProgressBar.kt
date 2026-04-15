package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun HighlightProgressBar(
    progress: Float,
    progressBrush: Brush,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFECEFF7),
) {
    var startAnimation by remember { mutableStateOf(false) }

    // Animação da barra clara (shimmer) - dispara imediatamente e chega ao fim mais rápido
    val animatedLightProgress by animateFloatAsState(
        targetValue = if (startAnimation) progress else 0f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "light_progress_animation"
    )

    // Animação da barra principal - tem um delay e vai cobrir a barra clara ao finalizar
    val animatedMainProgress by animateFloatAsState(
        targetValue = if (startAnimation) progress else 0f,
        animationSpec = tween(
            durationMillis = 600,
            delayMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "main_progress_animation"
    )

    val trackAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(300),
        label = "track_alpha"
    )

    LaunchedEffect(progress) {
        startAnimation = true
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor.copy(alpha = trackAlpha))
    ) {
        // 1. Barra Clara (fundo)
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedLightProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .alpha(0.3f) // Tom mais claro
                .background(progressBrush)
        )

        // 2. Barra Principal (frente)
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedMainProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(progressBrush)
        )
    }
}