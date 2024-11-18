package com.migueldk17.breeze.ui.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OndasAnimadas(
    baseColor: Color, //Cor base das ondas
    onWaveEnd: () -> Unit
) {
    val waveRadius by animateFloatAsState(
        targetValue = 2000f,
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        label = "animateFloatWave"
    )
    val alphaTransition by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(1500, easing = LinearEasing),
        label = "alphaTransition"
    )
    //Chamado ao final da animação
    LaunchedEffect(waveRadius) {
        if (waveRadius >= 2000f) onWaveEnd()
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = baseColor.copy(alphaTransition),
            center = center,
            radius = waveRadius
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    Canvas(modifier = Modifier.fillMaxSize()){
        drawCircle(
            color = Color.Blue.copy(alpha = 1f),
            center = center,
            radius = 0f // Começa em 0 para simular o estado inicial
        )
    }
}