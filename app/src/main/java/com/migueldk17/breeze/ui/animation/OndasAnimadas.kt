package com.migueldk17.breeze.ui.animation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
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
    Log.d(TAG, "OndasAnimadas: Início")
    val waveRadius by animateFloatAsState(
        targetValue = 2000f,
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        label = "animateFloatWave"
    )
    val alphaTransition by animateFloatAsState(
        targetValue = 0.9f,
        animationSpec = tween(1500, easing = LinearEasing),
        label = "alphaTransition"
    )
    Log.d(TAG, "OndasAnimadas: $waveRadius")
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
    Log.d(TAG, "OndasAnimadas: Fim")

}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    Column(modifier = Modifier.fillMaxSize()){
        OndasAnimadas(Color.Blue) { Log.d(TAG, "Preview: Fim da animação Preview") }
    }
}