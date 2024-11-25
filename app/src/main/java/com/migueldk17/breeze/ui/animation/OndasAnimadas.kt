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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sin

@Composable
fun OndasAnimadas(
    baseColor: Color, //Cor base das ondas
    percent: Float,
    modifier: Modifier = Modifier,
    waveAmplitude: Float = 15f, //Altura das ondas
    waveFrequency: Int = 30, // número de ondas ao longo da circunferência

) {
    var waveOffset by remember {
        mutableFloatStateOf(0f)
    }
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val animatedWaveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), ""
    )

    waveOffset = animatedWaveOffset

    if (percent > 0f) {
        var canvasSize by remember {
            mutableStateOf(Size.Zero)
        }
        var waveHeight by remember {
            mutableFloatStateOf(0f)
        }
        var yoffset by remember {
            mutableFloatStateOf(0f)
        }

        Box(modifier = Modifier.fillMaxSize()) {

            Canvas(modifier = Modifier.fillMaxSize()) {
                canvasSize = size
                waveHeight = 0.01f * size.height
                val lowFudge = 0.01f
                val hightFudge = 0.18f
                val adjustedPercent = lowFudge + (hightFudge - lowFudge) * percent.pow(0.34f)
                yoffset = (1f - adjustedPercent) * size.height

                val wavePath = Path().apply {
                    moveTo(
                        0f,
                        yoffset + waveHeight * sin(toRadians(waveOffset.toDouble()).toFloat())
                    )
                    for (x in 0 until size.width.toInt()) {
                        val angle = (x / size.width * 360f + waveOffset) % 360f
                        val y = yoffset + waveHeight * sin(toRadians(angle.toDouble())).toFloat()
                        lineTo(x.toFloat(), y)
                    }
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(
                    path = wavePath,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xff014174), Color(0xff0196a5)),
                        startY = yoffset,
                        endY = size.height
                    ),
                    style = Fill
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        OndasAnimadas(MaterialTheme.colorScheme.primary,15f)
    }
}