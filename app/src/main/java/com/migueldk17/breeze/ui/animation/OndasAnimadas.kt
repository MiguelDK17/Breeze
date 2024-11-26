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
fun ColorTransitionFromCenter(
    targetColor: Color,
    modifier: Modifier = Modifier
) {
    //Estado inicial e final da cor
    val animatedRadius = remember {
        Animatable(0f)
    }

    //Inicia a animação sempre que o targetColor mudar
    LaunchedEffect(key1 = targetColor) {
        animatedRadius.snapTo(0f) //Reincia o raio
        animatedRadius.animateTo(1f, animationSpec = tween(1000, easing = LinearOutSlowInEasing)) //Anima até o raio total
    }

    //Desenhar com o Canvas para criar a transição radial
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasSize = size
        val canvasCenter = Offset(canvasSize.width / 2, canvasSize.height / 2)
        val maxRadius = hypot(canvasSize.width, canvasSize.height) / 2


        //Preencher gradualmente com a cor a partir do centro
        drawCircle(
            color = targetColor,
            radius = maxRadius * animatedRadius.value, //Usando o valor animado
            center = canvasCenter
        )

    }
}
@Composable
@Preview(showBackground = true)
private fun Preview(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        ColorTransitionFromCenter(Color.Blue)
    }
}