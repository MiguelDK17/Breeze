package com.migueldk17.breeze.ui.animation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

@Composable
fun OndasAnimadas(
    baseColor: Color, //Cor base das ondas
    modifier: Modifier = Modifier
) {
    val animatedRadius = remember {
        Animatable(0f)
    }

    //Inicia a animação sempre que o targetColor mudar
    LaunchedEffect(key1 = baseColor) {
        animatedRadius.snapTo(0f) //Reinicia o raio
        animatedRadius.animateTo(1f, animationSpec = tween(1000, easing = LinearOutSlowInEasing)) //Anima até o raio total
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasSize = size
        val maxRadius = hypot(canvasSize.width, canvasSize.height) / 2
        val canvasCenter = Offset(canvasSize.width / 2, canvasSize.height / 2)
        val currentRadius = maxRadius * animatedRadius.value

        val waveAmplitude = 30f
        val waveFrequency = 10

        val path = Path().apply {
            val angleStep = 360f / waveFrequency
            moveTo(canvasCenter.x + currentRadius, canvasCenter.y)

            for (i in 0..waveFrequency){
                val angle = Math.toRadians((angleStep * i).toDouble())
                val offsetX = cos(angle).toFloat() * (currentRadius + waveAmplitude * sin(angle).toFloat())
                val offsetY = sin(angle).toFloat() * (currentRadius + waveAmplitude * sin(angle).toFloat())
                lineTo(canvasCenter.x + offsetX, canvasCenter.y + offsetY)
            }
            close()
        }
        drawPath(
            path = path,
            color = baseColor
        )

    }
}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    Column(modifier = Modifier.fillMaxSize()){
        OndasAnimadas(MaterialTheme.colorScheme.primary)
    }
}